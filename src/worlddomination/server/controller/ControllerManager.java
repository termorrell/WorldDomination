package worlddomination.server.controller;

import worlddomination.server.view.ControllerApiInterface;

public class ControllerManager {
	
	private static ClientController instance = null;
	
	protected ControllerManager() {}
	
	public static ClientController sharedManager(ControllerApiInterface view) {
		
		if(instance == null) {
			instance = new ClientController(view);
	    }
		return instance;
	}
}
