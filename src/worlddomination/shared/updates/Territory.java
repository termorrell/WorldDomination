package worlddomination.shared.updates;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Territory implements Serializable {

	private int territoryID;
	private int playerID;
	private int numberOfArmies;
	
	public int getTerritoryID() {
		return territoryID;
	}
	public void setTerritoryID(int territoryID) {
		this.territoryID = territoryID;
	}
	public int getPlayerID() {
		return playerID;
	}
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public int getNumberOfArmies() {
		return numberOfArmies;
	}
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}
}
