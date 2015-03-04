package controller;

import model.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.INetworkView;

public class NewController {
	
	INetworkView view;
	GameStateManager gameStateManager;
	static Logger log = LogManager.getLogger(NewController.class.getName());
	
	public NewController(INetworkView view) {
		this.view = view;
		this.gameStateManager = new GameStateManager();
	}

	/*
	 * Adds the player that is resembled by the running instance of this program.
	 */
	public void addLocalPlayer(int id) {
		String name = view.getLocalPlayerName();
		gameStateManager.addPlayer(id, name);
	}
	
	/*
	 * Adds the players that will act as opponents.
	 */
	public void addPlayer(int id, String name) {
		gameStateManager.addPlayer(id, name);
	}
}