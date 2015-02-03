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

}
