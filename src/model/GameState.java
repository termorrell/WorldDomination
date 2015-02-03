package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameState {
	Board board;
	ArrayList<Player> players = new ArrayList<Player>();
	int numberOfCards;
	LinkedList<Card> cards;

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

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> allPlayers) {
		this.players = allPlayers;
	}

	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}

}
