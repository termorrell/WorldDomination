package model;

import java.util.LinkedList;

import factories.BoardFactory;

public class GameState {
	Board board;
	Player[] players;
	//int numberOfCards;
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



	public int getCardsTradedIn() {
		return cardsTradedIn;
	}

	public void setCardsTradedIn(int cardsTradedIn) {
		this.cardsTradedIn = cardsTradedIn;
	}


	public static void printAllCards(LinkedList<Card> cards) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cards.size(); i++) {
			builder.append(cards.get(i).getId());
			builder.append(". ");
			if(cards.get(i).getTerritory() == null) {
				builder.append("Wild card");
			} else {
				builder.append((i+1)+ " " + cards.get(i).getTerritory().name +": " + cards.get(i).getType());
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}
}
