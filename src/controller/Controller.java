package controller;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import factories.BoardFactory;
import factories.CardFactory;
import model.*;
import view.IView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Controller {

	Model model;
	IView view;

	public Controller(Model model, IView view) {
		this.model = model;
		this.view = view;
	}

	public void run() {
		// Initialise controller
		init();

		// Players choose territories until all territories are occupied
		claimTerritories();

		// Players reinforce their territories until all inital armies are on
		// the board
		distributeInitialArmies();

		// Begin game play
		beginGamePlay();

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
	 * All players claim one of the 42 territories in turn.
	 */
	public void claimTerritories() {
		boolean allTerritoriesClaimed = false;

		while (!allTerritoriesClaimed) {
			for (int i = 0; i < model.getGameState().getPlayers().size(); i++) {
				System.out.println("\n");
				model.getGameState().getBoard().printAvailableTerritories();
				int territory = view.getNumber(model.getGameState().getPlayers().get(i).getName() + " please enter the territory ID you would like to claim: ");
				try {
					Moves.reinforce(model.getGameState().getPlayers().get(i), model.getGameState(), territory, 1);
				} catch (IllegalMoveException e) {
					e.printStackTrace();
				} catch (BoardException e) {
					e.printStackTrace();
				}
				allTerritoriesClaimed = checkForUnclaimedTerritories();
			}
		}
	}

	public boolean checkForUnclaimedTerritories() {
		int noTerritory = model.getGameState().getBoard().getNumberOfTerritories();
		Territory[] territories = model.getGameState().getBoard().getTerritories();
		for (int i = 0; i < noTerritory; i++) {
			// Check if territory has an owner
			if (territories[i].getOwner() == null) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Once all territories are claimed, the remaining intial armies are
	 * distributed.
	 */
	private void distributeInitialArmies() {
		int numberOfInitialArmies = calclulateNumberOfArmies();

		Map<Player, Integer> remainingArmies = new HashMap<>();
		for (Player player : model.getGameState().getPlayers()) {
			remainingArmies.put(player, numberOfInitialArmies - player.getArmies().size());
		}

		boolean allPlaced = allArmiesPlaced(remainingArmies.values());
		while (!allPlaced) {
			for (Player player : model.getGameState().getPlayers()) {
				if (remainingArmies.get(player) > 0) {
					System.out.println("\n");
					model.getGameState().getBoard().printAvailableTerritories();
					int destinationTerritoryId = view.getNumber(player.getName() + " please enter the territory ID you would like to deploy an army to: ");
					try {
						Moves.reinforce(player, model.getGameState(), destinationTerritoryId, 1);
						remainingArmies.put(player, remainingArmies.get(player) - 1);
					} catch (BoardException e) {
						System.err.println("A problem with the board occured.");
					} catch (IllegalMoveException e) {
						System.err.println("An illegal move was attempted. Please try again.");
					}
				}
				allPlaced = allArmiesPlaced(remainingArmies.values());
			}
		}
	}

	private boolean allArmiesPlaced(Collection<Integer> numberOfArmies) {
		for (int i : numberOfArmies) {
			if (i > 0) {
				return false;
			}
		}
		return true;
	}

	private int calclulateNumberOfArmies() {
		switch (model.getGameState().getNumberOfPlayers()) {
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		case 6:
			return 20;
		default:
			System.out.println("Wrong number of players.");
			break;
		}
		return -1;
	}

	/*
	 * Game moves are executed in turn
	 */
	public void beginGamePlay() {

		ArrayList<Player> players = model.getGameState().getPlayers();
		Iterator<Player> playerIterator = players.iterator();
		// Check to see whether the game has finished
		while (!checkWinnerExists()) {

			// Loop through players giving each player in turn a go
			Player player = playerIterator.next();

			collectArmies(player);

			boolean capturedTerritory = false;
			boolean playersTurnIsValid = true;
			// Allow each player to select what move they would like to make
			while (playersTurnIsValid) {

				// Ask the player what move they would like to perform
				// TODO: Need to be able to pass in the available moves for a
				// point in the game flow, so that one of two options is
				// returned
				System.out.println("\n");
				model.getGameState().getBoard().printAvailableTerritories();
				Move chosenMove = view.getMove(player.getName() + ", what move would you like to perform? (Attack/Fortify)");
				if (chosenMove == Move.ATTACK) {
					capturedTerritory |= attackTerritory(player);
					// Check whether the player would like an additional turn
					playersTurnIsValid = view.getBoolean(player.getName() + ", would you like to continue your turn? (Yes/No)");
				} else if (chosenMove == Move.FORTIFY) {
					fortifyTerritory(player);
					// After fortifying the game is over
					playersTurnIsValid = false;
				}
			}

			if (capturedTerritory) {
				// TODO do card stuff here
			}

			// Check whether the player has lost the game
			if (player.getTerritories().size() == 0) {
				// Remove the player from the list of players
				model.getGameState().getPlayers().remove(player);

				// TODO: Need to maintain the position in the iterator,
				// implement players and active players array list
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

	private int calculateNumberOfArmies(Player player, GameState gameState) {
		int numberOfArmies = 0;

		if (player.getTerritories().size() < 9) {
			numberOfArmies += 3;
		} else {
			numberOfArmies += Math.floor(player.getTerritories().size() / 3);
		}

		numberOfArmies += gameState.getBoard().getContinentBonus(player);
		return numberOfArmies;
	}

	public boolean attackTerritory(Player player) {

		boolean capturedTerritory = false;

		int attackingTerritoryId = view.getNumber(player.getName() + " please enter the territory ID you would like to attack from: ");
		int defendingTerritoryId = view.getNumber(player.getName() + " please enter the territory ID you would like to attack: ");
		int numberOfAttackingArmies = view.getNumber(player.getName() + " please enter the number of armies you would like attack with: ");

		try {
			Moves.attack(player, model.getGameState(), attackingTerritoryId, defendingTerritoryId, numberOfAttackingArmies);
			int numberOfDefendingArmies = view.getNumber(model.getGameState().getBoard().getTerritoriesById(defendingTerritoryId).getOwner().getName() + " please enter the number of armies you would like defend with: ");
			capturedTerritory = Moves.defend(player, model.getGameState(), attackingTerritoryId, defendingTerritoryId, numberOfAttackingArmies, numberOfDefendingArmies);
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		} catch (BoardException e) {
			e.printStackTrace();
		}

		return capturedTerritory;
	}

	public boolean fortifyTerritory(Player player) {

		int originTerritoryId = view.getNumber(player.getName() + " please enter the territory ID you would like to move your armies from: ");
		int destinationTerritoryId = view.getNumber(player.getName() + " please enter the territory ID you would like to fortify: ");
		int numberOfArmies = view.getNumber(player.getName() + " please enter the number of armies you would like to move: ");

		try {
			Moves.fortify(player, model.getGameState(), originTerritoryId, destinationTerritoryId, numberOfArmies);
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		} catch (BoardException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean checkWinnerExists() {
		int numberOfPlayersRemaining = model.getGameState().getPlayers().size();
		if (numberOfPlayersRemaining < 2 && numberOfPlayersRemaining > 0) {
			System.out.println("The game has finished. We have a winner");
			return true;
		} else {
			return false;
		}
	}

	public void collectArmies(Player player) {
		int numberOfArmies = calculateNumberOfArmies(player, model.getGameState());
		while (numberOfArmies > 0) {
			System.out.println("\n");
			model.getGameState().getBoard().printAvailableTerritories();
			int destinationTerritoryId = view.getNumber(player.getName() + " , you have " + numberOfArmies + " armies. Please enter the territory ID you would like to deploy armies to: ");
			int numberOfArmiesToBeDeploied = view.getNumber(player.getName() + " please enter the number of armies you would like to deploy: ");
			if (numberOfArmiesToBeDeploied <= numberOfArmies) {
				try {
					Moves.reinforce(player, model.getGameState(), destinationTerritoryId, numberOfArmiesToBeDeploied);
				} catch (BoardException e) {
					System.err.println("A problem with the board occured.");
				} catch (IllegalMoveException e) {
					System.err.println("An illegal move was attempted. Please try again.");
				}
				numberOfArmies -= numberOfArmiesToBeDeploied;
			} else {
				System.out.println("Sorry, you don't have that many armies.");
			}
		}
	}

}
