package controller;

import factories.BoardFactory;
import factories.CardFactory;
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

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try{
			// Set player name
			model.getPlayerInfo().setUserName(view.getInput("Please enter your name:", reader));
		
			// Set board
			BoardFactory boardFactory = new BoardFactory();
			model.getGameState().setBoard(boardFactory.getBoard());
		
			// Set cards based on board
			//CardFactory cardFactory = new CardFactory(model.getGameState().getBoard());
			//model.getGameState().setCards(cardFactory.getCards());

			// Set port number and validates it
			model.getPlayerInfo().setPort(view.getPort("Please enter the port number: ", reader));

			// Set public Key
			//model.getPlayerInfo().setKey
			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
	}
	
}
