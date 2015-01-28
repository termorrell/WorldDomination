package factories;

import java.util.LinkedList;

import model.Board;
import model.Card;

public class CardFactory {
	LinkedList<Card> cards;
	Board board;
	int numOfWildCards = 2;
	int numOfCards; 
	
	public CardFactory(Board board) {
		this.board = board;
		numOfCards = numOfWildCards + board.getNumberOfTerritories();
		this.cards = new LinkedList<Card>();
	}
	
	private void initCards() {
		for (int i = 0; i < board.getNumberOfTerritories(); i++) {
			cards.add(new Card(board.getTerritories()[i].getId(), board.getTerritories()[i]));
		}
		
		for (int i = board.getNumberOfTerritories(); i < numOfCards; i++) {
			cards.add(new Card(i, null));
		}
	}
	
	public LinkedList<Card> getCards() {
		initCards();
		return cards;
	}
}
