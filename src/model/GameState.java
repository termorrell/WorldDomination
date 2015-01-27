package model;

import java.util.LinkedList;

import factories.BoardFactory;

public class GameState {
	Board board;
	Player[] players;
	int numberOfCards;
	LinkedList<Card> cards;
	
	public GameState() {
		BoardFactory factory = new BoardFactory();
		this.board = factory.getBoard();
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}

}
