package controller;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import model.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import view.IView;

import java.io.BufferedReader;

/**
 * Created by ${mm280} on 03/02/15.
 */
public class CardMethods {

	static Logger log = LogManager.getLogger(CardMethods.class.getName());	// Called at end of each turn if territory gained
	// TODO SHUFFLE CARDS?
	public static void collectCard(Player activePlayer, Model model) {
		for (int i = 0; i < model.getGameState().getCards().size(); i++) {
			if (!model.getGameState().getCards().get(i).isAssigned()) {
				activePlayer.getCards().add(model.getGameState().getCards().get(i));
				model.getGameState().getCards().get(i).setAssigned(true);
				int playerCards = activePlayer.getNoCards();
				playerCards++;
				activePlayer.setNoCards(playerCards);
				log.debug(activePlayer.getName().toString()+" collected a "+ model.getGameState().getCards().get(i).getType()+" card");
				System.out.println(activePlayer.getName()+ ", you have gained a "+ model.getGameState().getCards().get(i).getType()+" card");
				break;
			}
		}
	}

	// Called at beginning of turn
	public static void tradeInCards(Player activePlayer, IView view, Model model) throws BoardException, IllegalMoveException {
		// Force player to trade in cards
		if (activePlayer.getNoCards() >= 5) {
			System.out.println("You have to trade in some of your cards");
			activePlayer.printCards(activePlayer.getCards());
			cardTrader(activePlayer, view, model);

		} else {
			// Ask player to if they want to
			if(activePlayer.getNoCards()>3) {
				activePlayer.printCards(activePlayer.getCards());
				String input = view.getInput("Would you like to trade in your cards?(Y/N)");
				input.toLowerCase();
				if (input.equals("y")) {
					cardTrader(activePlayer, view, model);
				}
			}
		}
	}

	public static void cardTrader(Player activePlayer, IView view, Model model) throws BoardException, IllegalMoveException {
		Card[] selectedCards = new Card[3];
		int playerCards=activePlayer.getNoCards();
		// Gather selected cards
		for (int i = 0; i < 3; i++) {
			int response = view.getNumber("Please enter the number of a card you would like to trade:");
			selectedCards[i] = activePlayer.getCards().get(response);
			System.out.println(selectedCards[i].getId() + " " + selectedCards[i].getTerritory().getName()+": " + selectedCards[i].getType() + " selected for trade");
		}
		// Check you can trade in cards
		if (verifyCardTrade(selectedCards)) {
			awardArmies(model, activePlayer, selectedCards, view);
			for(int i =0;i<2;i++){
				activePlayer.getCards().remove(selectedCards[i]);
			}
			playerCards = playerCards-3;
			activePlayer.setNoCards(playerCards);
			log.debug(activePlayer.getName().toString()+" traded in a set of cards");
		} else {
			System.out.println("Those cards can't be traded");
			log.debug("Card trade failed, cards didn't match");
		}
	}

	public static boolean verifyCardTrade(Card[] selectedCards) {
		if (checkOneWildCard(selectedCards)) {
			return true;
		} else if (checkAllSameType(selectedCards)) {
			return true;
		} else if (checkAllDifferentType(selectedCards)) {
			return true;
		} else {
			System.out.println("ERROR: Cards aren't the same");
		}
		return false;

	}

	public static boolean checkOneWildCard(Card[] selectedCards) {
		int wildCardNo = 0;
		for (int i = 0; i < selectedCards.length; i++) {
			if (selectedCards[i].getType().equals("Wild")) {
				wildCardNo++;
			}
		}
		return wildCardNo == 1;
	}

	public static boolean checkAllSameType(Card[] selectedCards) {

		return selectedCards[0].getType().equals(selectedCards[1].getType()) && selectedCards[0].getType().equals(selectedCards[2].getType());
	}

	public static boolean checkAllDifferentType(Card[] selectedCards) {
		String cardType = selectedCards[0].getType();
		if (checkOneWildCard(selectedCards)) {
			return false;
		}
		if (!cardType.equals(selectedCards[1].getType()) && !cardType.equals(selectedCards[2].getType())) {
			if (!selectedCards[2].getType().equals(selectedCards[1].getType())) {
				return true;
			}
		}
		return false;
	}

	public static void awardArmies(Model model, Player activePlayer, Card[] selectedCards, IView view) throws BoardException, IllegalMoveException {
		int selectedArmies;
		int playerTerritorySelection;
		int armiesAwarded = calculateArmiesAwarded(model);
		System.out.println("You have been awarded: "+armiesAwarded+" armies");
		for (int i = 0; i < 3; i++) {
			if (activePlayer.getArmies().containsValue(selectedCards[i].getTerritory().getName())) {
				playerTerritorySelection = selectedCards[i].getTerritory().getId();
				Moves.reinforce(activePlayer, model.getGameState(), playerTerritorySelection, 2);
				break;
			}
		}
		while (armiesAwarded > 0) {
			System.out.println("Armies left to be traded: "+ armiesAwarded);
			model.getGameState().getBoard().printAvailableTerritories();
			playerTerritorySelection = view.getNumber("What territory would you like to put your armies in?");
			selectedArmies = view.getNumber("How many armies would you like to put on this territory? ");
			if (selectedArmies <= armiesAwarded) {
				armiesAwarded = armiesAwarded - selectedArmies;
				Moves.reinforce(activePlayer, model.getGameState(), playerTerritorySelection, selectedArmies);
			}
		}
		System.out.println("All armies added to board");
	}

	public static int calculateArmiesAwarded(Model model) {
		int armiesAwarded = 2;
		int cardsTraded = model.getGameState().getCardsTradedIn();
		for (int i = 0; i <= cardsTraded; i++) {
			if (i <= 4) {
				armiesAwarded += 2;
			} else if (i == 5) {
				armiesAwarded += 3;
			} else if (i > 5) {
				armiesAwarded += 5;
			}
		}
		cardsTraded++;
		model.getGameState().setCardsTradedIn(cardsTraded);
		return armiesAwarded;
	}
}
