package controller;

import com.sun.xml.internal.bind.v2.TODO;
import exceptions.BoardException;
import exceptions.IllegalMoveException;
import factories.BoardFactory;
import factories.CardFactory;
import model.*;
import view.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;


public class Controller {

	Model model;
	IView view;

	public Controller(Model model, IView view) {
		this.model = model;
		this.view = view;
	}

	public void run() {
		init();
	}

	private void init() {
	//assign, name of territory and player - return boolean
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{

			// Set board
			BoardFactory boardFactory = new BoardFactory();
			model.getGameState().setBoard(boardFactory.getBoard());

			// Set cards based on board
			CardFactory cardFactory = new CardFactory(model.getGameState().getBoard());
			model.getGameState().setCards(cardFactory.getCards());

			//int portNo=0;
			// Set port number
			//while(portNo<=0) {
				//player.setNumber(view.getNumber("Please enter the port number: ", reader));
			//}
			// Set public Key
			//player.setPublicKey(view.getInput("Please enter your public key: ", reader));
			// Player numbers,
			int playerNo = 0;
			//Checks positive amount of players is entered
			while(playerNo< 3 || playerNo>6) {
				model.getGameState().setNumberOfPlayers(view.getNumber("Please enter the number of players between 3 and 6: ", reader));
				playerNo = model.getGameState().getNumberOfPlayers();
			}
			ArrayList<Player> allPlayers = new ArrayList<Player>(model.getGameState().getPlayers());
			for(int i=0; i< playerNo; i++){
				Player player = new Player();
				// Set player name
				player.setName(view.getInput("Please enter your name:", reader));
				player.setColour(view.getInput("What colour would you like to be: ", reader));
				allPlayers.add(player);

			}
			model.getGameState().setPlayers(allPlayers);

			// Allow each player to assign themself territories
			//claimTerritories(reader, allPlayers);

			// Begin game play
			beginGamePlay(reader);

			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
	}
	public boolean checkWinnerExists() {
		int numberOfPlayersRemaining = model.getGameState().getPlayers().size();
		if (numberOfPlayersRemaining < 2 && numberOfPlayersRemaining > 0){
			System.out.println("The game has finished. We have a winner");
			return true;
		} else {
			return false;
		}
	}
	
	public boolean claimTerritories(BufferedReader reader, ArrayList<Player> allPlayers) {
		boolean allTerritoriesClaimed = false;

		//ASSUMES PLAYER ARRAY IS ALTERED!
		while(!allTerritoriesClaimed) {
			for (int i = 0; i < allPlayers.size(); i++) {
				model.getGameState().getBoard().printAvailableTerritories();
				int territory = view.getNumber(allPlayers.get(i).getName() + " please enter the territory ID you would like to claim: ", reader);
				try {
					Moves.reinforce(allPlayers.get(i), model.getGameState(), territory, 1);
				} catch (IllegalMoveException e) {
					e.printStackTrace();
				} catch (BoardException e) {
					e.printStackTrace();
				}
				allTerritoriesClaimed = checkForUnclaimedTerritories();
			}
		}
		return true;
	}

	public boolean attackTerritory(Player player, BufferedReader reader) {

		int territory = view.getNumber(player.getName() + " please enter the territory ID you would like to attack: ", reader);
		try {
			//Moves.attack(player, model.getGameState(), territory);
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		} catch (BoardException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean fortifyTerritory(Player player, BufferedReader reader) {

		int territory = view.getNumber(player.getName() + " please enter the territory ID you would like to fortify: ", reader);
		try {
			//Moves.attack(player, model.getGameState(), territory);
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		} catch (BoardException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean checkForUnclaimedTerritories(){
		int noTerritory = model.getGameState().getBoard().getNumberOfTerritories();
		Territory[] territories = model.getGameState().getBoard().getTerritories();
		for(int i =0; i< noTerritory;i++){
			//Check if territory has an owner
			if (territories[i].getOwner()==null){
				return false;
			}
		}
		return true;
	}


	public void beginGamePlay(BufferedReader reader) {

		ArrayList<Player> players = model.getGameState().getPlayers();
		Iterator playerIterator = players.iterator();
		// Check to see whether the game has finished
		while(!checkWinnerExists()) {

			// Loop through players giving each player in turn a go
			Player player = (Player)playerIterator.next();

			collectArmies(player, reader);

			boolean playersTurnIsValid = true;
			// Allow each player to select what move they would like to make
			while (playersTurnIsValid) {

				// Ask the player what move they would like to perform
				// TODO: Need to be able to pass in the available moves for a point in the game flow, so that one of two options is returned
				Move chosenMove = view.getMove(player.getName() + ", what move would you like to perform? (Attack/Fortify)", reader);
				if (chosenMove == Move.ATTACK) {
					attackTerritory(player, reader);
				} else if (chosenMove == Move.FORTIFY) {
					fortifyTerritory(player, reader);
				}

				// Check whether the player would like an additional turn
				playersTurnIsValid = view.getBoolean(player.getName() + ", would you like to continue your turn? (Yes/No)", reader);
			}

			// Check whether the player has lost the game
			if (player.getTerritories().size() == 0) {
				// Remove the player from the list of players
				model.getGameState().getPlayers().remove(player);


				// TODO: Need to maintain the position in the iterator, implement players and active players array list
				// Reset the iterator
				playerIterator = players.iterator();
			}

			// Check whether end of player list has been reached
			if (!playerIterator.hasNext()) {

				// Reset the iterator
				playerIterator = players.iterator();
			}
		}
	}


	public void collectArmies(Player player, BufferedReader reader) {

		// This needs to be implemented
	}
}
