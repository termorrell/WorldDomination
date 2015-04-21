package worlddomination.server.network;

import java.net.Socket;
import java.util.ArrayList;

import worlddomination.server.controller.ServerController;

public class RiskServerThread implements Runnable {
	
	private int numberOfPlayers;
	private Socket socket;
	private int ackTimeout;
	private int moveTimeout;
	private ServerController controller;
	private ArrayList<ServerConnection> connections;
	
	public RiskServerThread(int numberOfPlayers, Socket socket, int ackTimeout, int moveTimeout, ServerController controller, ArrayList<ServerConnection> connections) {
		this.numberOfPlayers = numberOfPlayers;
		this.socket = socket;
		this.ackTimeout = ackTimeout;
		this.moveTimeout = moveTimeout;
		this.controller = controller;
		this.connections = connections;
	}

	public void run() {

		ServerClientThread client = new ServerClientThread(numberOfPlayers ,socket, ackTimeout,moveTimeout, controller);
		ServerConnection connection = new ServerConnection(numberOfPlayers ,client);
		synchronized (this) {
			connections.add(connection);
		}
		Thread t = new Thread(client);
		t.start();
		System.out.println("connected");
		synchronized (this) {
			numberOfPlayers++;
		}
	}
}
