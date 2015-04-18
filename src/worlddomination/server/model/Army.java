package model;

public class Army {
	
	static int nextID = 0;
	
	int id;
	Player player;
	Territory territory;
	
	public Army(Player player, Territory territory) {
		this.id = nextID;
		nextID++;
		this.player = player;
		this.territory = territory;
	}

	public int getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public Territory getTerritory() {
		return territory;
	}

	public void setTerritory(Territory territory) {
		this.territory = territory;
	}

}
