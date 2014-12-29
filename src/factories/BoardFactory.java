package factories;

import model.Board;
import model.Continent;
import model.Territory;

public class BoardFactory {

	private Board defaultBoard = null;

	public BoardFactory() {
		initDefaultBoard();
	}

	public Board getBoard() {
		return defaultBoard;
	}

	private void initDefaultBoard() {
		defaultBoard = new Board(6, 42);

		String[] northAmericanTerritoryNames = { "Alaska",
				"Northwest territory", "Greenland", "Alberta", "Ontario",
				"Quebec", "Western United States", "Eastern United States",
				"Central America" };
		initContinent(0, "North America", northAmericanTerritoryNames, 0,
				defaultBoard);

		String[] southAmericanTerritoryNames = { "Venezuela", "Peru", "Brazil",
				"Argentina" };
		initContinent(1, "South America", southAmericanTerritoryNames, 9,
				defaultBoard);

		String[] europeanTerritoryNames = { "Iceland", "Scandinavia",
				"Ukraine", "Great Britain", "Northern Europe",
				"Western Europe", "Southern Europe" };
		initContinent(2, "Europe", europeanTerritoryNames, 13, defaultBoard);

		String[] africanTerritoryNames = { "North Africa", "Egypt", "Congo",
				"East Africa", "South Africa", "Madagaskar" };
		initContinent(3, "Africa", africanTerritoryNames, 20, defaultBoard);

		String[] asianTerritoryNames = { "Ural", "Siberia", "Yakutsk",
				"Kamichatka", "Irkutsk", "Mongolia", "Japan", "Afghanistan",
				"China", "Middle East", "India", "Siam" };
		initContinent(4, "Asia", asianTerritoryNames, 26, defaultBoard);

		String[] australianTerritoryNames = { "Indonesia", "New Guinea",
				"Western Australia", "Eastern Australia" };
		initContinent(5, "Australia", australianTerritoryNames, 38,
				defaultBoard);

		setNeighbouringTerritories();
	}

	private void initContinent(int continentId, String name,
			String[] territories, int firstTerritoryId, Board board) {
		
		int numberOfTerritories = territories.length;
		board.getContinents()[continentId] = new Continent(continentId, name,
				new Territory[numberOfTerritories]);

		for (int i = 0; i < territories.length; i++) {
			board.getTerritories()[i + firstTerritoryId] = new Territory(i
					+ firstTerritoryId, territories[i], board.getContinents()[continentId]);
			board.getContinents()[continentId].getTerritories()[i] = defaultBoard
					.getTerritories()[i + firstTerritoryId];
		}
	}

	private void setNeighbouringTerritories() {
		Territory[] neighbours = {defaultBoard.getTerritories()[1], defaultBoard.getTerritories()[3]};
		defaultBoard.getTerritories()[0].setNeighbours(neighbours);
		
		//TODO this needs to be filled out for all other territories
	}
}
