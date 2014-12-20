package model;

import exceptions.BoardException;

public class Board {
	Continent[] continents;
	Territory[] territories;
	int numberOfContinent;
	int numberOfTerritories;

	public Continent[] getContinents() {
		return continents;
	}

	public void setContinents(Continent[] continents) {
		this.continents = continents;
	}

	public Territory[] getTerritories() {
		return territories;
	}
	
	public Territory getTerritoriesById(int id) throws BoardException {
		for (Territory territory: territories) {
			if(territory.getId() == id) {
				return territory;
			}
		}
		throw new BoardException();
	}

	public void setTerritories(Territory[] territories) {
		this.territories = territories;
	}

	public int getNumberOfContinent() {
		return numberOfContinent;
	}

	public void setNumberOfContinent(int numberOfContinent) {
		this.numberOfContinent = numberOfContinent;
	}

	public int getNumberOfTerritories() {
		return numberOfTerritories;
	}

	public void setNumberOfTerritories(int numberOfTerritories) {
		this.numberOfTerritories = numberOfTerritories;
	}

}
