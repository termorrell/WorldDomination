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
		for (Territory territory : territories) {
			if (territory.getId() == id) {
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

	public int getContinentBonus(Player player) {
		int bonus = 0;

		for (Continent continent : continents) {
			if (playerOwnsContinent(player, continent)) {
				bonus += continent.getReinforcementArmies();
			}
		}

		return bonus;
	}

	private boolean playerOwnsContinent(Player player, Continent continent) {
		for (Territory territory : continent.getTerritories()) {
			if (!territory.getOwner().equals(player)) {
				return false;
			}
		}
		return true;
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

				if (continents[i].getTerritories()[j].getNeighbours() != null) {
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

	public void printAvailableTerritories() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numberOfTerritories; i++) {
			builder.append(territories[i].getId());
			builder.append(": ");
			builder.append(territories[i].getName());
			builder.append(" (");
			builder.append(territories[i].getContinent().getName());
			builder.append(")");
			addBlankSpace(builder, 50, builder.toString().length());
			if (territories[i].getOwner() != null) {
				builder.append(territories[i].getOwner().getName());
				addBlankSpace(builder, 65, builder.toString().length());
				builder.append(territories[i].getOccupyingArmies().size());
			} else {
				builder.append("AVAILABLE");
			}
			addBlankSpace(builder, 70, builder.toString().length());
			printNeighbours(builder, territories[i].getNeighbours());
			System.out.println(builder.toString());
			builder = new StringBuilder();
		}
		System.out.println("");
	}

	private void addBlankSpace(StringBuilder builder, int lines, int size) {
		for (int j = lines; j > size; j--) {
			builder.append(" ");
		}
	}
	
	private void printNeighbours(StringBuilder builder, Territory[] neighbours) {
		for (int i = 0; i < neighbours.length; i++) {
			builder.append(neighbours[i].getName());
			if(i != neighbours.length - 1) {
				builder.append(", ");
			}
		}
	}

}
