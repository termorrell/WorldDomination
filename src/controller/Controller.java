package controller;

import com.sun.xml.internal.bind.v2.TODO;
import factories.BoardFactory;
import factories.CardFactory;
import model.Player;
import model.Territory;
import view.IView;
import model.Card;
import model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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

			int playerNo = 0;
			//Checks positive amount of players is entered
			while(playerNo< 3 || playerNo>6) {
				model.getGameState().setNumberOfPlayers(view.getNumber("Please enter the number of players between 3 and 6: ", reader));
				playerNo = model.getGameState().getNumberOfPlayers();
			}
			Player[] allPlayers = new Player[playerNo];
			for(int i=0; i< playerNo; i++){
				Player player = new Player();
				// Set player name
				player.setName(view.getInput("Please enter your name:", reader));
				player.setColour(view.getInput("What colour would you like to be: ", reader));
				allPlayers[i]= player;

			}
			model.getGameState().setPlayers(allPlayers);
			claimTerritories(reader, allPlayers);
			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
	}
	public boolean claimTerritories(BufferedReader reader, Player[] allPlayers){
		boolean allTerritoriesClaimed = false;

		//ASSUMES PLAYER ARRAY IS ALTERED!
		while(!allTerritoriesClaimed) {
			for (int i = 0; i < allPlayers.length; i++) {
				String territory = view.getInput(allPlayers[i].getName() + " what territory would you like to claim: ", reader);
				territory.toLowerCase();
				//call reinforce method using player etc
				allTerritoriesClaimed = checkForUnclaimedTerritories();
			}
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
	//Called at end of each turn
	// TODO SHUFFLE CARDS?
	public void collectCard(Player activePlayer){
		for(int i =0; i<model.getGameState().getNumberOfCards();i++){
			if(!model.getGameState().getCards().get(i).isAssigned()){
				activePlayer.getCards().add(model.getGameState().getCards().get(i));
				model.getGameState().getCards().get(i).setAssigned(true);
				int playerCards = activePlayer.getNoCards();
				activePlayer.setNoCards(playerCards++);
			}
		}
	}

	//Called at beginning of turn
	public void tradeInCards(Player activePlayer, BufferedReader reader){
		//Force player to trade in cards
		if(activePlayer.getNoCards()>=5){

			System.out.println("You have to trade in some of your cards");
			activePlayer.printCards(activePlayer.getCards());
			cardTrader(activePlayer, reader);

		}else{
			//Ask player to if they want to
			activePlayer.printCards(activePlayer.getCards());
			String input = view.getInput("Would you like to trade in your cards?(Y/N)", reader);
			input.toLowerCase();
			if(input.equals("y")){
				cardTrader(activePlayer, reader);
			}
		}
	}

	public void cardTrader(Player activePlayer, BufferedReader reader) {
		Territory selectedTerritory;
		int selectedArmies;
		Card[] selectedCards = new Card[2];
		String playerSelection = null;
		// Gather selected cards
		for (int i = 0; i < 3; i++) {
			int response = view.getNumber("Please enter the number of a card you would like to trade", reader);
			selectedCards[i] = activePlayer.getCards().get(response);
		}

		// Checks all are same type
		if ((selectedCards[0].getTerritory().getName()).equals(selectedCards[1].getTerritory().getName())
				&& (selectedCards[0].getTerritory().getName().equals(selectedCards[2].getTerritory().getName()))) {
			int armiesAwarded = 0;
			int cardsTraded = model.getGameState().getCardsTradedIn();
			if (cardsTraded == 0) {
				armiesAwarded = 4;
			} else if (cardsTraded == 1) {
				armiesAwarded = 6;
			} else if (cardsTraded == 2) {
				armiesAwarded = 8;
			} else if (cardsTraded == 3) {
				armiesAwarded = 10;
			} else if (cardsTraded == 4) {
				armiesAwarded = 12;
			} else if (cardsTraded == 5) {
				armiesAwarded = 15;
			} else if (cardsTraded == 6) {
				armiesAwarded = 20;
			} else if (cardsTraded == 7) {
				armiesAwarded = 25;
			} else if (cardsTraded == 8) {
				armiesAwarded = 30;
			}
			cardsTraded++;
			model.getGameState().setCardsTradedIn(cardsTraded);
			for (int i = 0; i < 3; i++) {
				if (activePlayer.getArmies().containsValue(selectedCards[i].getTerritory().getName())) {
					armiesAwarded += 2;
					selectedTerritory = selectedCards[i].getTerritory();
					break;
				}
			}
			while(armiesAwarded>= 0) {
				playerSelection = view.getInput("What territory would you like to put your armies in?", reader);
				selectedArmies = view.getNumber("How many armies would you like to put on this territory? ", reader);
				if(armiesAwarded<=selectedArmies) {
					armiesAwarded = armiesAwarded - selectedArmies;
					//call reinforce method
				}
			}
		} else {
			System.out.println("Error: Cards aren't the same type");
			tradeInCards(activePlayer, reader);
		}
	}

}
