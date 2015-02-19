package controller;

import java.util.ArrayList;

import model.Model;
import model.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.IView;

public class NetworkController {
	Model model;
	IView view;
	static Logger log = LogManager.getLogger(Controller.class.getName());

	public NetworkController(Model model, IView view) {
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

		// Number of players are entered
		int playerNo = 0;
		while (playerNo < 3 || playerNo > 6) {
			model.getGameState().setNumberOfPlayers(view.getNumber("Please enter the number of players between 3 and 6: "));
			playerNo = model.getGameState().getNumberOfPlayers();
		}

		// Player details are entered
		ArrayList<Player> allPlayers = new ArrayList<Player>();
		for (int i = 0; i < playerNo; i++) {
			Player player = new Player();
			// Set player name
			player.setName(view.getInput("Please enter your name:"));
			player.setColour(view.getInput("What colour would you like to be: "));
			allPlayers.add(player);
		}
		model.getGameState().setPlayers(allPlayers);
	}
	
	/*
	 * Adds the player that is resembled by the running instance of this program.
	 */
	public void addLocalPlayer(int id) {
		
	}
	
	public void addPlayer(int id, String name) {
		
	}

}
