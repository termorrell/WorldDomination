package worlddomination.server.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import worlddomination.server.exceptions.BoardException;
import worlddomination.server.exceptions.IllegalMoveException;
import worlddomination.server.model.Army;
import worlddomination.server.model.GameState;
import worlddomination.server.model.Player;
import worlddomination.server.model.Territory;
import org.apache.logging.log4j.Logger;

public class Moves {
    static Logger log = org.apache.logging.log4j.LogManager.getLogger(Controller.class.getName());

	/*
     * REINFORCE
	 * 
	 * Reinforcing can be used both when the territories are initially claimed
	 * and in the reinforce stage of every move.
	 */

    public static void reinforce(Player player, GameState gameState, int territoryId, int numberOfArmies) throws BoardException, IllegalMoveException {
        // TODO: check whether it is this players turn

        Territory territory = gameState.getBoard().getTerritoriesById(territoryId);

        if (checkTerritoryCanBeReinforced(territory, player)) {
            for (int i = 0; i < numberOfArmies; i++) {
                Army army = new Army(player, territory);
                player.addArmies(army, territory);
                territory.addOccupyingArmy(army);

            }
        } else {
            log.error("Illegal Move Detected in reinforce");
            throw new IllegalMoveException();
        }
    }

    private static boolean checkTerritoryCanBeReinforced(Territory territory, Player player) {
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
     * ATTACK
     *
     * Allows to attack a neighbouring country with up to 3 armies. No changes
     * to the game state are necessary as the defence needs to be chosen before
     * the move can be calculated.
     */
    public static void attack(Player player, GameState gameState, int attackingTerritoryId, int defendingTerritoryId, int numberOfArmies) throws BoardException, IllegalMoveException {

        Territory attackingTerritory = gameState.getBoard().getTerritoriesById(attackingTerritoryId);
        Territory defendingTerritory = gameState.getBoard().getTerritoriesById(defendingTerritoryId);
        Player defendingPlayer = defendingTerritory.getOwner();

        if (!checkAttackIsLegal(player, defendingPlayer, gameState, attackingTerritory, defendingTerritory, numberOfArmies)) {
            throw new IllegalMoveException();
        }

    }

    private static boolean checkAttackIsLegal(Player player, Player defendingPlayer, GameState gameState, Territory attackingTerritory, Territory defendingTerritory, int numberOfArmies) {

        boolean legal = true;

        if (player.equals(defendingPlayer)) {
            legal = false;
        }
        if (!attackingTerritory.isNeighbouringTerritory(defendingTerritory) || !defendingTerritory.isNeighbouringTerritory(attackingTerritory)) {
            legal = false;
        }
        if (attackingTerritory.getOccupyingArmies().size() < 2) {
            legal = false;
        }
        if (attackingTerritory.getOccupyingArmies().size() <= numberOfArmies) {
            legal = false;
        }
        if (numberOfArmies < 1 || numberOfArmies > 3) {
            legal = false;
        }

        return legal;
    }

	/*
     * DEFEND
	 * 
	 * Checks that a defence is legal and plays attack-defend scenario.
	 */

    public static boolean defend(Player attacker, GameState gameState, int attackingTerritoryId, int defendingTerritoryId, int numberOfAttackingArmies, int numberOfDefendingArmies) throws BoardException, IllegalMoveException {

        boolean captured = false;

        Territory attackingTerritory = gameState.getBoard().getTerritoriesById(attackingTerritoryId);
        Territory defendingTerritory = gameState.getBoard().getTerritoriesById(defendingTerritoryId);
        Player defendingPlayer = defendingTerritory.getOwner();

        if (checkDefendIsLegal(attacker, defendingPlayer, gameState, attackingTerritory, defendingTerritory, numberOfAttackingArmies, numberOfDefendingArmies)) {

            List<Integer> attackerDie = SimpleDieManager.diceRoll(6, numberOfAttackingArmies);
            Collections.sort(attackerDie, Collections.reverseOrder());
            List<Integer> defenderDie = SimpleDieManager.diceRoll(6, numberOfDefendingArmies);
            Collections.sort(defenderDie, Collections.reverseOrder());

            for (int i = 0; i < defenderDie.size() && i < attackerDie.size(); i++) {
                if (attackerDie.get(i) > defenderDie.get(i)) {
                    removeArmyFromTerritory(defendingTerritory);
                } else {
                    removeArmyFromTerritory(attackingTerritory);
                }
            }

            if (defendingTerritory.getOccupyingArmies().size() == 0) {
                captured = true;
                defendingTerritory.setOwner(attacker);
                for (int i = 0; i < numberOfAttackingArmies; i++) {
                    removeArmyFromTerritory(attackingTerritory);
                    Army army = new Army(attacker, defendingTerritory);
                    attacker.addArmies(army, defendingTerritory);
                    defendingTerritory.addOccupyingArmy(army);
                }
            }

        } else {
            throw new IllegalMoveException();
        }

        return captured;
    }



    public static boolean defendTerritory(Player attacker, GameState gameState, int attackingTerritoryId, int defendingTerritoryId, int numberOfAttackingArmies, int numberOfDefendingArmies, int[] dieRolls) throws BoardException, IllegalMoveException {

        boolean captured = false;

        Territory attackingTerritory = gameState.getBoard().getTerritoriesById(attackingTerritoryId);
        Territory defendingTerritory = gameState.getBoard().getTerritoriesById(defendingTerritoryId);
        Player defendingPlayer = defendingTerritory.getOwner();

        if (checkDefendIsLegal(attacker, defendingPlayer, gameState, attackingTerritory, defendingTerritory, numberOfAttackingArmies, numberOfDefendingArmies)) {

            int[] attackerDie = Arrays.copyOfRange(dieRolls,0, numberOfAttackingArmies);
            int[] defenderDie = Arrays.copyOfRange(dieRolls, numberOfAttackingArmies, dieRolls.length);

            for (int i = 0; i < defenderDie.length && i < attackerDie.length; i++) {
                if (attackerDie[i] > defenderDie[i]) {
                    removeArmyFromTerritory(defendingTerritory);
                } else {
                    removeArmyFromTerritory(attackingTerritory);
                }
            }

            if (defendingTerritory.getOccupyingArmies().size() == 0) {
                captured = true;

            }

        } else {
            throw new IllegalMoveException();
        }

        return captured;
    }


    public static void capture (Player attacker, GameState gameState, int attackingTerritoryId, int defendingTerritoryId, int numberOfMovingArmies) throws BoardException, IllegalMoveException {

        Territory attackingTerritory = gameState.getBoard().getTerritoriesById(attackingTerritoryId);
        Territory defendingTerritory = gameState.getBoard().getTerritoriesById(defendingTerritoryId);
        Player defendingPlayer = defendingTerritory.getOwner();

        defendingTerritory.setOwner(attacker);
        for (int i = 0; i < numberOfMovingArmies; i++) {
            removeArmyFromTerritory(attackingTerritory);
            Army army = new Army(attacker, defendingTerritory);
            attacker.addArmies(army, defendingTerritory);
            defendingTerritory.addOccupyingArmy(army);
        }
    }

    private static void removeArmyFromTerritory(Territory lostTerritory) {
        if (lostTerritory.getOccupyingArmies().size() > 0) {
            Army lostArmy = lostTerritory.getOccupyingArmies().getLast();
            lostTerritory.getOccupyingArmies().remove(lostArmy);
            lostTerritory.getOwner().getArmies().remove(lostArmy);
        }
    }

    private static boolean checkDefendIsLegal(Player attacker, Player defendingPlayer, GameState gameState, Territory attackingTerritory, Territory defendingTerritory, int numberOfAttackingArmies, int numberOfDefendingArmies) {

        boolean legal = true;

        if (numberOfDefendingArmies > 2) {
            legal = false;
        }
        if (numberOfDefendingArmies > defendingTerritory.getOccupyingArmies().size()) {
            legal = false;
        }

        return legal;
    }

    /*
     * FORTIFY
     *
     * Allows to move armies from one territory to another in the fortify stage
     * of a move.
     */
    public static void fortify(Player player, GameState gameState, int originTerritoryId, int destinationTerritoryId, int numberOfArmies) throws BoardException, IllegalMoveException {

        Territory originTerritory = gameState.getBoard().getTerritoriesById(originTerritoryId);
        Territory destinationTerritory = gameState.getBoard().getTerritoriesById(destinationTerritoryId);

        if (checkFortifyIsLegal(player, originTerritory, destinationTerritory, numberOfArmies)) {
            for (int i = 0; i < numberOfArmies; i++) {
                Army movingArmy = originTerritory.getOccupyingArmies().getLast();
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

    private static boolean checkFortifyIsLegal(Player player, Territory originTerritory, Territory destinationTerritory, int numberOfArmies) {
        if (originTerritory.getOwner().equals(player) && destinationTerritory.getOwner().equals(player)) {
            if (originTerritory.getOccupyingArmies().size() > numberOfArmies) {
                if (originTerritory.isNeighbouringTerritory(destinationTerritory) && destinationTerritory.isNeighbouringTerritory(originTerritory)) {
                    return true;
                }
            }
        }
        return false;
    }


}
