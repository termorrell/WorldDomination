package controller;

import java.util.ArrayList;

import model.Model;
import model.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.INetworkView;

public class NetworkController {
	Model model;
	INetworkView view;
	static Logger log = LogManager.getLogger(Controller.class.getName());

	public NetworkController(Model model, INetworkView view) {
		this.model = model;
		this.view = view;
	}

	public void run() {
		// Initialise controller

		init();
		// Players choose territories until all territories are occupied
		//claimTerritories();

		// Players reinforce their territories until all initial armies are on
		// the board
		//distributeInitialArmies();
		log.info("Finished Distributing Army Setup");
		// Begin game play
		//beginGamePlay();

	}
	
	/*
	 * assign, name of territory and player - return boolean
	 */
	private void init() {
		// Set board
		model.getGameState().setBoard(DataManager.getBoard());

		// Set cards based on board
		model.getGameState().setCards(DataManager.getCards(model.getGameState().getBoard()));
	}
	
	/*
	 * Adds the player that is resembled by the running instance of this program.
	 */
	public void addLocalPlayer(int id) {
		String name = view.getLocalPlayerName();
		this.addPlayer(id, name);
	}
	
	/*
	 * Adds the players that will act as opponents.
	 */
	public void addPlayer(int id, String name) {
		// TODO check for no more than 6 players
		Player me = new Player();
		me.setName(name);
		me.setId(id);
		model.getGameState().getPlayers().add(me);
		model.getGameState().setNumberOfPlayers(model.getGameState().getNumberOfPlayers() + 1);
	}
	
	/*
	 * Checks the game is set up correctly
	 */
	public boolean checkReady() {
		boolean ready = true;
		if(model.getGameState().getNumberOfPlayers() >= 3 && model.getGameState().getNumberOfPlayers() <= 6) {
			ready = false;
		}
		return ready;
	}

}
