package controller;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import model.GameState;
import model.Model;
import model.Player;

import model.Territory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameStateManager {
    Model model;
    static Logger log = LogManager.getLogger(ClientController.class.getName());


    public GameStateManager() {
        this.model = new Model();
        init();
    }

    /*
     * assign, name of territory and player - return boolean
     */
    private void init() {
        // Set board
        model.getGameState().setBoard(DataManager.getBoard());

        // Set cards based on board
        model.getGameState().setCards(DataManager.getCards(model.getGameState().getBoard()));
    }

    /*
     * Adds the players that will act as opponents.
     */
    public void addPlayer(int id, String name, String publicKey) {
        // TODO check for no more than 6 players
        Player player = new Player();
        player.setName(name);
        player.setId(id);
        if (publicKey != null && publicKey.length() > 0) {
            player.setPublicKey(publicKey);
        }
        model.getGameState().getPlayers().add(player);
        model.getGameState().setNumberOfPlayers(model.getGameState().getNumberOfPlayers() + 1);
    }

    /**
     * Checks if the player with this id is already known to the game state
     *
     * @param id - player id
     * @return true if the player is known to the game state
     */
    public boolean checkPlayerExists(int id) {
        for (Player player : model.getGameState().getPlayers()) {
            if (player.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /*
     * Checks the game is set up correctly
     */
    public boolean checkReady() {
        boolean ready = true;
        if (model.getGameState().getNumberOfPlayers() >= 3 && model.getGameState().getNumberOfPlayers() <= 6) {
            ready = false;
        }
        return ready;
    }

    /*
     * Claims territory for player.
     * TODO error checking
     */
    public void setup(int playerId, int territoryId) throws BoardException, IllegalMoveException {

        Moves.reinforce(model.getGameState().getPlayerById(playerId), model.getGameState(), territoryId, 1);

    }

    public void reinforce(int playerId, int territoryId, int numberOfArmies) throws BoardException, IllegalMoveException {
        Moves.reinforce(model.getGameState().getPlayerById(playerId), model.getGameState(), territoryId, numberOfArmies);
    }


    public void addLocalPlayerInfo(String name) {
        model.getPlayerInfo().setUserName(name);
    }

    public void setLocalPlayerId(int id) {
        model.getPlayerInfo().setId(id);
    }

    public int getLocalPlayerId() {
        return model.getPlayerInfo().getId();
    }

    public void removePlayer(int id) {
        Player player = model.getGameState().getPlayerById(id);
        model.getGameState().getPlayers().remove(player);
        model.getGameState().setNumberOfPlayers(model.getGameState().getNumberOfPlayers() - 1);
    }

    public String serializeMap() {
        // TODO Maria's excellent JSON skills to serialize the map :)
        model.getGameState().getBoard().printAvailableTerritories();
        return null;
    }

    public boolean allTerritoriesClaimed() {
        int noTerritory = model.getGameState().getBoard().getNumberOfTerritories();
        Territory[] territories = model.getGameState().getBoard().getTerritories();
        for (int i = 0; i < noTerritory; i++) {
            // Check if territory has an owner
            if (territories[i].getOwner() == null) {
                return false;
            }
        }
        return true;
    }

    public int calculateNumberOfArmies(int playerId) {
        int numberOfArmies = 0;
        Player player = model.getGameState().getPlayerById(playerId);

        if (player.getTerritories().size() < 9) {
            numberOfArmies += 3;
        } else {
            numberOfArmies += Math.floor(player.getTerritories().size() / 3);
        }

        numberOfArmies += model.getGameState().getBoard().getContinentBonus(player);
        return numberOfArmies;
    }
}

