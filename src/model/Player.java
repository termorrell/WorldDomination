package model;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Player {
	int id;
	InetAddress ipAddress;
	int port;
	String name;
	String publicKey;
	Map<Army, Territory> armies; // mapping armies to the territories they are occupying
	LinkedList<Card> cards;

	public Player() {
		this.armies = new HashMap<Army, Territory>();
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

}
