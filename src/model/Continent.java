package model;

public class Continent {
	int id;
	String name;
	Territory[] territories;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Territory[] getTerritories() {
		return territories;
	}

	public void setTerritories(Territory[] territories) {
		this.territories = territories;
	}

}
