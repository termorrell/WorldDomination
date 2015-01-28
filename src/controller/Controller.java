package controller;

import factories.BoardFactory;
import factories.CardFactory;
import model.Player;
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

			// Set port number and validates it
			//player.setPort(view.getPort("Please enter the port number: ", reader));

			// Set public Key
			//player.setPublicKey(view.getInput("Please enter your public key: ", reader));

			// Player numbers,
			model.getGameState().setNumberOfPlayers(view.getPort("Please enter the number of players: ", reader));
			Player[] allPlayers = new Player[model.getGameState().getNumberOfPlayers()];
			for(int i=0; i< model.getGameState().getNumberOfPlayers(); i++){
				Player player = new Player();
				// Set player name
				player.setName(view.getInput("Please enter your name:", reader));
				player.setColour(view.getInput("What colour would you like to be: ", reader));
				allPlayers[i]= player;

			}
			model.getGameState().setPlayers(allPlayers);
			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
	}
	
}
