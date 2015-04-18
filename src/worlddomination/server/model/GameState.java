package worlddomination.server.model;

import java.util.ArrayList;
import java.util.LinkedList;

import worlddomination.server.factories.BoardFactory;

public class GameState {
	Board board;
	ArrayList<Player> players = new ArrayList<Player>();
	int numberOfCards;
	LinkedList<Card> cards;
	int cardsTradedIn;
	int numberOfPlayers;
	
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
