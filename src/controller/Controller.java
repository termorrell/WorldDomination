package controller;

import factories.BoardFactory;
import view.IView;
import model.Model;


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
	
		// Set player name
		model.getPlayerInfo().setUserName(view.getInput("Please enter you name:"));
		
		// Set board
		BoardFactory boardFactory = new BoardFactory();
		model.getGameState().setBoard(boardFactory.getBoard());
		
		model.getGameState().getBoard().printBoard();
	}
	
}
