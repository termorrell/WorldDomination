package controller;

import java.util.Map.Entry;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import model.Army;
import model.GameState;
import model.Player;
import model.Territory;

public class Moves {
	
	public void reinforce(Player player, GameState gameState, int territoryId, int numberOfArmies) throws BoardException, IllegalMoveException {
		// TODO: check whether it is this players turn
	
		Territory territory = gameState.getBoard().getTerritoriesById(territoryId);
		
		if(checkTerritoryCanBeReinforced(territory, player)){
			for (int i = 0; i < numberOfArmies; i++) {
				Army army = new Army();
				// TODO: add armies to board
			}
		} else {
			throw new IllegalMoveException();
		}
	}
	
	private boolean checkTerritoryCanBeReinforced(Territory territory, Player player) {
		boolean playerOwnsTerritory = false;
		
		// check if player owns territory
		for (Entry<Army, Territory> deployment : player.getArmies().entrySet()) {
			if(deployment.getValue().equals(territory)) {
				playerOwnsTerritory = true;
				break;
			}
		}
		
		// the player owns the territory and has at least one army in it
		if(playerOwnsTerritory && territory.getOwner().equals(player)) {
			if(territory.getOccupyingArmies().size() > 1) {
				return true;
			}
		}
		
		// the player doesn't own the territory and there are no armies in it
		if(!playerOwnsTerritory && territory.getOwner() == null) {
			if(territory.getOccupyingArmies().size() ==  0) {
				return true;
			}
		}
		
		return false;
	}
}
