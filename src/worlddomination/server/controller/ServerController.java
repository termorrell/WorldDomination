package worlddomination.server.controller;

import worlddomination.server.actions.*;
import worlddomination.server.network.RiskServer;
import worlddomination.server.network.ServerConnection;
import worlddomination.server.network.ServerResponseGenerator;

import org.json.JSONObject;

import worlddomination.server.program.Constants;
import worlddomination.shared.updates.Lobby;
import worlddomination.shared.updates.LobbyPlayer;
import worlddomination.shared.updates.LogUpdate;
import worlddomination.shared.updates.PingReady;
import worlddomination.server.view.ControllerApiInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ServerController implements Runnable{

	ServerResponseGenerator responseGenerator;
	AcknowledgementManager acknowledgementManager = null;
	ControllerApiInterface view;
	boolean collectingAcknowledgements;
	ArrayList<Integer> pingsRecevied = new ArrayList<>();
	ArrayList<ServerConnection> connections;
	Queue<Action> actions = new LinkedList<>();
	public RiskServer server;
	boolean beginGame, gameStarted;
	String host;
	int port, acknowCount;
	/**
	 * Creates the server controller and initializes all the variables, also runs a server on a new thread
	 * @param view view for updating the GUI
	 */
	public ServerController(ControllerApiInterface view){
		port = 6666;
		this.responseGenerator = new ServerResponseGenerator();
		this.acknowledgementManager = new AcknowledgementManager();
		this.view = view;
		gameStarted=false;
		acknowCount =0;
		server = new RiskServer(port,2,3,this);
		Thread t = new Thread(server);
		t.start();
		beginGame =false;
		collectingAcknowledgements =false;
		connections = server.getConnections();
	}

	/**
	 * Tells GUI if the port changed, runs a loop to continuously check for actions.
	 */
	public void run() {
		if(server.port != port){
			LogUpdate portChange = new LogUpdate("Changed port to: " + server.port);
			port = server.port;
			view.addUpdate(portChange);
		}

		while (true) {
			executeActions();
		}
	}

	class PingThread implements Runnable {

		PingReady ready;

		public PingThread() {
			this.ready =  (PingReady)view.addUpdateAndWaitForResponse(new PingReady());
		}

		public void run() {
			beginGame = this.ready.getIsReady();
		}
	}

	/**
	 * Continuously executes actions sent to controller
	 */
	private void executeActions() {
		while (!isActionEmpty()) {
			Action nextAction = pollAction();
			executeAction(nextAction);
		}
	}

	/**
	 * Sends the action off to the correct method to handle it
	 * @param action action to be sent
	 */
	private void executeAction(Action action) {

		if (action instanceof JoinGame) {
			joinGame((JoinGame)action);
		} else if (action instanceof Ping) {
			ping((Ping) action);
		}else if(action instanceof LeaveGame){
			leaveGame((LeaveGame) action);
		}else if(action instanceof Acknowledgement){
			if(!gameStarted) {
				acknowledgement((Acknowledgement) action);
			}else{
				JSONObject resp = responseGenerator.ackGenerator(((Acknowledgement) action).getAcknowledgementId(),((Acknowledgement) action).getPlayerId());
				server.sendMessageToAllExceptSender(((Acknowledgement) action).getPlayerId(), resp);
			}
		}

	}

	/**
	 * Update the GUI with all the players joined
	 * @param players players joined
	 */
	private void sendPlayersToGui(HashMap<Integer,String> players) {
		Lobby lobby = new Lobby();
		for (Map.Entry<Integer, String> player : players.entrySet()) {
			lobby.addPlayerToListOfPlayers(new LobbyPlayer(player.getKey(),
					player.getValue(), ""));
		}
		view.addUpdate(lobby);
	}

	/**
	 * Called when an acknowledgement is received, logs acknowledgement and checks if all have been received.
	 * @param acknowledgement the action to be analysed
	 */
	private void acknowledgement(Acknowledgement acknowledgement) {
		boolean complete = false;
		if(collectingAcknowledgements) {
			int responseCode = acknowledgementManager.addAcknowledgement(acknowledgement.getPlayerId(), acknowledgement.getAcknowledgementId());
			complete = acknowledgementManager.isAcknowledgedByAllPlayers(connections.size()+1);
			JSONObject resp = responseGenerator.ackGenerator(acknowledgement.getAcknowledgementId(),acknowledgement.getPlayerId());
			server.sendMessageToAllExceptSender(acknowledgement.getPlayerId(), resp);
		}
		if(complete){
			JSONObject response = responseGenerator.initGameGenerated(1.0,Constants.getSupportedFeatures());
			server.sendMessageToAll(response);
			collectingAcknowledgements =false;
			gameStarted = true;

		}
	}

	/**
	 * Called when a leave game action is received, removes player from connections and forwards message to 
	 * other players
	 * @param leave action received
	 */
	private void leaveGame(LeaveGame leave){
		connections.remove(leave.getPlayerId());
		JSONObject message = new JSONObject();
		message = responseGenerator.leaveGameGenerator(leave.getResponseCode(), leave.getReceiveUpdates(), leave.getPlayerId(), leave.getMessage());
		server.sendMessageToAllExceptSender(leave.getPlayerId(), message);
	}

	/**
	 * Called when a join game action is received, gets common versions and sends accept or reject message
	 * @param join action sent
	 */
	private synchronized void joinGame(JoinGame join){
		int player_id = connections.get(connections.size()-1).getPlayer_id();
		connections.get(connections.size()-1).setName(join.getPlayer_name());
		JSONObject response;
		HashMap<Integer,String> playersJoined = new HashMap<>();
		if(connections.size()<=6){
			response= responseGenerator.acceptJoinGameGenerator(connections.size(), 20, 30);
			server.sendToOne(player_id,response);
			for(int i =0;i<connections.size();i++){
					playersJoined.put(connections.get(i).getPlayer_id(),connections.get(i).getName());
				
			}
			response = responseGenerator.playersJoined(playersJoined);
			sendPlayersToGui(playersJoined);
			server.sendMessageToAll(response);
		}else{
			response = responseGenerator.rejectJoinGameGenerator("Too many players already joined");
			server.sendToOne(player_id,response);
			beginGame=true;
		}

		if(connections.size()==3){
			PingThread a = new PingThread();
			a.run();
		}
		if(beginGame || connections.size()==6) {
			response = responseGenerator.pingGenerator(playersJoined.size(), -1);
			server.sendMessageToAll(response);
		}
	}

	/**
	 * Called when a ping action is received, forwards on message and sends ready message is all pings are received
	 * @param ping action sent
	 */
	private void ping(Ping ping){
		pingsRecevied.add(ping.getPlayerId());
		JSONObject pings = responseGenerator.pingGenerator(ping.getNumberOfPlayers(),ping.getPlayerId());
		server.sendMessageToAllExceptSender(ping.getPlayerId(),pings);
		if((pingsRecevied.size()==connections.size()&&connections.size()>=3) ||connections.size()==6) {
			JSONObject response = responseGenerator.readyGenerator(-1, acknowCount);
			server.sendMessageToAll(response);
			acknowCount++;
			collectingAcknowledgements = true;
			acknowledgementManager.incrementAcknowledgementIdAndExpectAcknowledgment();
		}
	}

	/**
	 * Adds a new action to the queue of actions to be analyzed
	 * @param action action received.
	 */
	public synchronized void handleAction(Action action) {
		actions.add(action);
	}

	private synchronized Action pollAction() {
		return actions.poll();
	}

	private synchronized boolean isActionEmpty() {
		return actions.isEmpty();

	}

}
