package model;

import exceptions.BoardException;

public class Board {
	Continent[] continents;
	Territory[] territories;
	int numberOfContinent;
	int numberOfTerritories;
	
	public Board(int numberOfContinents, int numberOfTerritories) {
		this.numberOfContinent = numberOfContinents;
		this.numberOfTerritories = numberOfTerritories;
		this.continents = new Continent[numberOfContinents];
		this.territories = new Territory[numberOfTerritories];
	}

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

	public void printBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < continents.length; i++) {
			builder.append(continents[i].getId());
			builder.append(". ");
			builder.append(continents[i].getName());
			builder.append(":\n");
			for (int j = 0; j < continents[i].getTerritories().length; j++) {
				builder.append("\t");
				builder.append(continents[i].getTerritories()[j].getId());
				builder.append(". ");
				builder.append(continents[i].getTerritories()[j].getName());
				builder.append(" - ");
				
				if(continents[i].getTerritories()[j].getNeighbours() != null) {
					for (int k = 0; k < continents[i].getTerritories()[j].getNeighbours().length; k++) {
						builder.append(continents[i].getTerritories()[j].getNeighbours()[k].getName());
						builder.append(", ");
					}
				}
				
				builder.append("\n");
			}
		}
		System.out.println(builder.toString());
	}

	public void printAvailableTerritories(){
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<numberOfTerritories;i++){
			if(territories[i].getOwner() != null) {
				builder.append(territories[i].getId());
				builder.append(": ");
			    builder.append(territories[i].getName() +": " + territories[i].getContinent()+": " + territories[i].getOwner());
				}
				builder.append("\n");
		}
		System.out.println(builder.toString());
		}
	}
	

