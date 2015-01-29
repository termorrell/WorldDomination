package model;

import java.util.LinkedList;

import factories.BoardFactory;

public class GameState {
	Board board;
	Player[] players;
	int numberOfCards;
	LinkedList<Card> cards;
	int cardsTradedIn;
	
	public GameState() {
		BoardFactory factory = new BoardFactory();
		this.board = factory.getBoard();
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	int numberOfPlayers;

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

	public int getNumberOfCards() {
		return numberOfCards;
	}

	public void setNumberOfCards(int numberOfCards) {
		this.numberOfCards = numberOfCards;
	}

	public int getCardsTradedIn() {
		return cardsTradedIn;
	}

	public void setCardsTradedIn(int cardsTradedIn) {
		this.cardsTradedIn = cardsTradedIn;
	}

}
