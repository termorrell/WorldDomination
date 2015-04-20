package worlddomination.server.network;

import worlddomination.server.controller.ServerController;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class RiskServer implements Runnable{

	private static ArrayList<ServerConnection> connections = new ArrayList<>();
	public int port;
	private ServerSocket ssocket;
	private int ackTimeout;
	private int moveTimeout;
	private ServerController controller;
	private boolean portLocated;

	/**
	 * Constructor for server
	 * @param port port to start server on
	 * @param ackTimeout timeout for acknowledgments
	 * @param moveTimeout timeout for moves
	 * @param controller controller to handle actions
	 */
	public RiskServer(int port, int ackTimeout, int moveTimeout, ServerController controller){
		this.port = port;
		this.ackTimeout = ackTimeout;
		this.moveTimeout = moveTimeout;
		this.controller = controller;
	}
	
	/**
	 * Called to begin the server loop, will continuously check for connections
	 */
	public void run(){

		try {
			locateAvailablePort();
			int i=1;
			ssocket = new ServerSocket(port);
			System.out.println("listening");
			while(true){
				Socket socket = ssocket.accept();
				ServerClientThread client = new ServerClientThread(i,socket, ackTimeout,moveTimeout, controller);
				ServerConnection connection = new ServerConnection(i,client);
				connections.add(connection);
				Thread t = new Thread(client);
				t.start();
				System.out.println("connected");
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Called at end of program to close sockets
	 */
	public void closeServer(){
		try {
			ssocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Keeps incrementing ports until an available one is found.
	 */
	public void locateAvailablePort(){
		do{
			try{
				ServerSocket s = new ServerSocket(port);
				portLocated = true;
				
				s.close();
			}catch(IOException e){
				port++;
			}
		}while(!portLocated);
	}
	
	/**
	 * Send a message to all of the client connections 
	 * @param message message to be sent
	 */
	public void sendMessageToAll(JSONObject message){
		for (ServerConnection client : connections) {
			client.getConnection().sendServerMessage(message);
		}
	}
	
	/**
	 * Sends messages to all the clients except one
	 * @param playerId client to not send the message too
	 * @param message message to be sent
	 */
	public void sendMessageToAllExceptSender(int playerId, JSONObject message){
		for (ServerConnection client : connections) {
			if (client.getPlayer_id() != playerId) {
				client.getConnection().sendServerMessage(message);
			}
		}
	}
	
	/**
	 * Sends message to one connection
	 * @param playerId connection for message to be sent
	 * @param message message to be sent
	 */
	public void sendToOne(int playerId, JSONObject message){
		for (ServerConnection client : connections) {
			if (client.getPlayer_id() == playerId) {
				client.getConnection().sendServerMessage(message);
			}
		}
	}
	
	/**
	 * Get all the connections
	 * @return arraylist of connections
	 */
	public ArrayList<ServerConnection> getConnections() {
		return connections;
	}


}
