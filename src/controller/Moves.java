package controller;

import java.util.Map.Entry;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import model.Army;
import model.GameState;
import model.Player;
import model.Territory;

public class Moves {

	/*
	 * REINFORCE
	 * 
	 * Reinforcing can be used both when the territories are initially claimed
	 * and in the reinforce stage of every move.
	 */

	public static void reinforce(Player player, GameState gameState,
			int territoryId, int numberOfArmies) throws BoardException,
			IllegalMoveException {
		// TODO: check whether it is this players turn

		Territory territory = gameState.getBoard().getTerritoriesById(
				territoryId);

		if (checkTerritoryCanBeReinforced(territory, player)) {
			for (int i = 0; i < numberOfArmies; i++) {
				Army army = new Army(player, territory);
				player.addArmies(army, territory);
				territory.addOccupyingArmy(army);
			}
		} else {
			throw new IllegalMoveException();
		}
	}

	private static boolean checkTerritoryCanBeReinforced(Territory territory,
			Player player) {
		boolean playerOwnsTerritory = false;

		// check if player owns territory
		for (Entry<Army, Territory> deployment : player.getArmies().entrySet()) {
			if (deployment.getValue().equals(territory)) {
				playerOwnsTerritory = true;
				break;
			}
		}

		// the player owns the territory and has at least one army in it
		if (playerOwnsTerritory && territory.getOwner().equals(player)) {
			if (territory.getOccupyingArmies().size() >= 1) {
				return true;
			}
		}

		// the player doesn't own the territory and there are no armies in it
		if (!playerOwnsTerritory && territory.getOwner() == null) {
			if (territory.getOccupyingArmies().size() == 0) {
				return true;
			}
		}

		return false;
	}

	/*
	 * FORTIFY
	 * 
	 * Allows to move armies from one territory to another in the fortify stage
	 * of a move.
	 */
	public static void fortify(Player player, GameState gameState,
			int originTerritoryId, int destinationTerritoryId,
			int numberOfArmies) throws BoardException, IllegalMoveException {
		
		Territory originTerritory = gameState.getBoard().getTerritoriesById(originTerritoryId);
		Territory destinationTerritory = gameState.getBoard().getTerritoriesById(destinationTerritoryId);

		if (checkFortifyIsLegal(player, originTerritory, destinationTerritory,
				numberOfArmies)) {
			for (int i = 0; i < numberOfArmies; i++) {
				Army movingArmy = originTerritory.getOccupyingArmies()
						.getLast();
				originTerritory.getOccupyingArmies().remove(movingArmy);
				try {
					destinationTerritory.addOccupyingArmy(movingArmy);
				} catch (IllegalMoveException e) {
					throw new BoardException();
				}
			}
		} else {
			throw new IllegalMoveException();
		}

	}

	private static boolean checkFortifyIsLegal(Player player,
			Territory originTerritory, Territory destinationTerritory,
			int numberOfArmies) {
		if (originTerritory.getOwner().equals(player)
				&& destinationTerritory.getOwner().equals(player)) {
			if (originTerritory.getOccupyingArmies().size() > numberOfArmies) {
				if (originTerritory
						.isNeighbouringTerritory(destinationTerritory)
						&& destinationTerritory
								.isNeighbouringTerritory(originTerritory)) {
					return true;
				}
			}
		}
		return false;
	}
}
