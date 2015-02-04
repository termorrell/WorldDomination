package controller;

import com.sun.xml.internal.bind.v2.TODO;
import exceptions.BoardException;
import exceptions.IllegalMoveException;
import factories.BoardFactory;
import factories.CardFactory;
import model.Player;
import model.Territory;
import view.IView;
import model.Card;
import model.GameState;
import model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


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
			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
	}
	public void checkForWinner() {
		int i;
		for (i = 0; i < model.getGameState().getPlayers().size(); i++) {
			if (i < 2 && i > 0){
				System.out.println("The game has finished. We have a winner");
			}
		}
	}
	
	public boolean claimTerritories(BufferedReader reader, ArrayList<Player> allPlayers)throws BoardException, IllegalMoveException {
		boolean allTerritoriesClaimed = false;

		//ASSUMES PLAYER ARRAY IS ALTERED!
		while(!allTerritoriesClaimed) {
			for (int i = 0; i < allPlayers.size(); i++) {
				model.getGameState().getBoard().printAvailableTerritories();
				int territory = view.getNumber(allPlayers.get(i).getName() + " please enter the territory ID you would like to claim: ", reader);
				Moves.reinforce(allPlayers.get(i), model.getGameState(), territory, 1);
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
