package controller;

import java.util.LinkedList;

import factories.BoardFactory;
import factories.CardFactory;
import model.Board;
import model.Card;

public class DataManager {
	
	public static Board getBoard() {
		BoardFactory boardFactory = new BoardFactory();
		return boardFactory.getBoard();
	}
	
	public static LinkedList<Card> getCards(Board board) {
		CardFactory cardFactory = new CardFactory(board);
		return cardFactory.getCards();
	}
}
