package worlddomination.server.controller;

import worlddomination.server.view.ControllerApiInterface;

public class ControllerManager {
	
	private static ClientController clientInstance = null;
	private static ServerController serverInstance = null;
	
	protected ControllerManager() {}
	
	public static ClientController sharedClientController(ControllerApiInterface view, String ipAddress, int port) {
		
		if(clientInstance == null) {
			clientInstance = new ClientController(view, ipAddress, port);
	    }
		return clientInstance;
	}

	public static ServerController sharedServerController(ControllerApiInterface view) {
		
		if(serverInstance == null) {
			serverInstance = new ServerController(view);
	    }
		return serverInstance;
	}
}
