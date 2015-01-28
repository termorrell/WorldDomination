package model;

import java.util.LinkedList;

import exceptions.IllegalMoveException;

public class Territory {
	int id;
	String name;
	Player owner;
	Continent continent;
	Territory[] neighbours;
	LinkedList<Army> occupyingArmies; // TODO: would an integer suffice?

	public Territory(int id, String name, Continent continent) {
		this.id = id;
		this.name = name;
		this.continent = continent;
		this.occupyingArmies = new LinkedList<Army>();
	}

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

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Continent getContinent() {
		return continent;
	}

	public void setContinent(Continent continent) {
		this.continent = continent;
	}

	public Territory[] getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(Territory[] neighbours) {
		this.neighbours = neighbours;
	}

	public LinkedList<Army> getOccupyingArmies() {
		return occupyingArmies;
	}

	public void addOccupyingArmy(Army army) throws IllegalMoveException {
		if(this.owner == null) {
			this.owner = army.getPlayer();
		}
		if(this.owner.equals(army.getPlayer())) {
			this.occupyingArmies.add(army);
		} else {
			throw new IllegalMoveException();
		}
	}

}
