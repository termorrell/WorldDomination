package model;

import java.util.LinkedList;

public class Card {
	private int id;
	private Territory territory;
	private String type;

	
	public Card(int id, Territory territory, String type) {
		this.id = id;
		this.territory = territory;
		this.type = type;
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

	public static void printCards(LinkedList<Card> cards) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < cards.size(); i++) {
			builder.append(cards.get(i).getId());
			builder.append(". ");
			if(cards.get(i).getTerritory() == null) {
				builder.append("Wild card");
			} else {
				builder.append(cards.get(i).getTerritory().name);
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}

}
