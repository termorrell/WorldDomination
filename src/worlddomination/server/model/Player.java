package worlddomination.server.model;

import java.net.InetAddress;
import java.util.*;

public class Player {
	static int nextId = 0;
	
	int id;
	InetAddress ipAddress;
	int port;
	String name;
	String publicKey;
	Map<Army, Territory> armies; // mapping armies to the territories they are occupying
	LinkedList<Card> cards;
	int noCards;
	String colour;

	public Player() {
		this.armies = new HashMap<Army, Territory>();
		this.id = this.nextId;
		this.nextId++;
		this.noCards = 0;
		this.cards = new LinkedList<Card>();
	}

	public static void printCards(LinkedList<Card> cards) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cards.size(); i++) {
			builder.append(cards.get(i).getId());
			builder.append(". ");
			if(cards.get(i).getTerritory() == null) {
				builder.append("Wild card");
			} else {
				builder.append(" " + cards.get(i).getTerritory().name +": " + cards.get(i).getType());
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InetAddress getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(InetAddress ipAddress) {
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Map<Army, Territory> getArmies() {
		return armies;
	}

	public void addArmies(Army army, Territory territory) {
		this.armies.put(army, territory);
	}
	
	public Territory getArmiesTerritory(Army army) {
		return this.armies.get(army);
	}

	public ArrayList<Territory> getTerritories() {
		Collection<Territory> territories = armies.values();
		HashSet<Territory> confinedTerritories = new HashSet<Territory>(territories);
		return new ArrayList<Territory>(confinedTerritories);
	}
	
	public boolean hasArmyInTerritory(Territory territory) {
		return armies.containsValue(territory);
	}

	public LinkedList<Card> getCards() {
		return cards;
	}

	public void setCards(LinkedList<Card> cards) {
		this.cards = cards;
	}

	public void setArmies(Map<Army, Territory> armies) {
		this.armies = armies;
	}

	public int getNoCards() {
		return noCards;
	}

	public void setNoCards(int noCards) {
		this.noCards = noCards;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

}
