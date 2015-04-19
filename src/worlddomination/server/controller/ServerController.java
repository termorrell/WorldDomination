package worlddomination.server.controller;

import worlddomination.server.actions.*;
import worlddomination.server.network.RiskServer;
import worlddomination.server.network.ServerConnection;
import worlddomination.server.network.ServerResponseGenerator;
import org.json.JSONObject;
import worlddomination.server.program.Constants;
import worlddomination.shared.updates.PingReady;
import worlddomination.server.view.ControllerApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ServerController implements Runnable{

    ServerResponseGenerator responseGenerator;
    AcknowledgementManager acknowledgementManager = null;
    ControllerApiInterface view;
    boolean collectingAcknowledgements;
    Float[] versions;
    String[]features;
    ArrayList<Float[]> allVersions = new ArrayList<>();
    ArrayList<String[]> allFeatures = new ArrayList<>();
    ArrayList<Integer> pingsRecevied = new ArrayList<>();
    ArrayList<ServerConnection> connections;
    Queue<Action> actions = new LinkedList<>();
    public RiskServer server;
    boolean beginGame;

    public ServerController( ){
        this.responseGenerator = new ServerResponseGenerator();
        this.acknowledgementManager = new AcknowledgementManager();
        this.view = view;
        //TODO GET INFO FROM USER INPUT??
        server = new RiskServer(6666,2,3,this);
        Thread t = new Thread(server);
        t.start();
        beginGame =false;
        collectingAcknowledgements =false;
        connections = server.getConnections();
    }
    public void run() {
//        PingReady ready =  (PingReady)view.addUpdateAndWaitForResponse(new PingReady());
  //      PingThread a = new PingThread(ready,view);
    //    a.run();
        while (true) {
            executeActions();
        }
    }
    class PingThread implements Runnable {

        PingReady c;

        public PingThread(PingReady c, ControllerApiInterface view) {
            this.c = c;
        }

        public void run() {
            beginGame = this.c.getIsReady();
        }
    }
    private void executeActions() {
        while (!actions.isEmpty()) {
            Action nextAction = actions.poll();
            executeAction(nextAction);
        }
    }


    private void executeAction(Action action) {

        if (action instanceof JoinGame) {
            joinGame((JoinGame)action);
        } else if (action instanceof Ping) {
            ping((Ping) action);
        }else if(action instanceof LeaveGame){
            leaveGame((LeaveGame) action);
        }else if(action instanceof Acknowledgement){
            acknowledgement((Acknowledgement) action);
        }

    }

    private void acknowledgement(Acknowledgement acknowledgement) {
        boolean complete = false;
        if(collectingAcknowledgements) {
            int responseCode = acknowledgementManager.addAcknowledgement(acknowledgement.getPlayerId(), acknowledgement.getAcknowledgementId());
            complete = acknowledgementManager.isAcknowledgedByAllPlayers(connections.size()-1);
        }
        if(complete){
            JSONObject response = responseGenerator.initGameGenerated(1.0,Constants.getSupportedFeatures());
            server.sendMessageToAll(response);
        }
    }
    private void leaveGame(LeaveGame leave){
        connections.remove(leave.getPlayerId());
    }
    private void joinGame(JoinGame join){
        int player_id = connections.get(connections.size()-1).getPlayer_id();
        versions = new Float[join.getSupported_versions().length];
        for(int i=0;i<versions.length;i++){
            versions[i] =join.getSupported_versions()[i];
        }
        allVersions.add(versions);
        features = new String[join.getSupported_features().length];
        for(int i=0;i<features.length;i++){
            features[i] =join.getSupported_features()[i];
        }
        allFeatures.add(features);
        connections.get(connections.size()-1).setName(join.getPlayer_name());
        JSONObject response;
        HashMap<Integer,String> playersJoined = new HashMap<>();
        if(connections.size()<=6){
            response= responseGenerator.acceptJoinGameGenerator(connections.size() - 1, 20, 30);
            server.sendToOne(player_id,response);
            for(int i =0;i<connections.size();i++){
                playersJoined.put(connections.get(i).getPlayer_id(),connections.get(i).getName());
            }
            response = responseGenerator.playersJoined(playersJoined);
            server.sendMessageToAll(response);
        }else{
            response = responseGenerator.rejectJoinGameGenerator("Too many players already joined");
            server.sendToOne(player_id,response);
            beginGame=true;
        }
        //TODO GET INPUT FROM GUI TO CHANGE BOOLEAN
        if(connections.size()==3){
            beginGame = true;
        }
        if(beginGame==true) {
            response = responseGenerator.pingGenerator(playersJoined.size(), -1);
            server.sendMessageToAll(response);
        }
    }
    private void ping(Ping ping){
        pingsRecevied.add(ping.getPlayerId());
        JSONObject pings = responseGenerator.pingGenerator(ping.getNumberOfPlayers(),ping.getPlayerId());
        server.sendMessageToAllExceptSender(ping.getPlayerId(),pings);
        if((pingsRecevied.size()==connections.size()&&connections.size()>=3) ||connections.size()==6) {
            JSONObject response = responseGenerator.readyGenerator(-1, 1);
            server.sendMessageToAll(response);
            collectingAcknowledgements = true;
        }
    }



    public synchronized void handleAction(Action action) {
        actions.add(action);
    }
}
