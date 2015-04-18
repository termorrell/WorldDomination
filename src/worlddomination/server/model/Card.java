package worlddomination.server.model;

import java.util.LinkedList;

public class Card {
	private int id;
	private Territory territory;
	private String type;
	private boolean assigned;

	
	public Card(int id, Territory territory, String type, boolean assigned) {
		this.id = id;
		this.territory = territory;
		this.type = type;
		this.assigned = assigned;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}



}
