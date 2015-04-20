package worlddomination.server.controller;

import worlddomination.server.exceptions.BoardException;
import worlddomination.server.exceptions.IllegalMoveException;
import worlddomination.server.model.Card;
import worlddomination.server.model.Model;
import worlddomination.server.model.Player;
import worlddomination.server.model.Territory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

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
    	// TODO take out one day
    	// model.getGameState().getBoard().printAvailableTerritories();
    	
    	
    	JSONObject object = new JSONObject();
		JSONArray territories = new JSONArray();
		Territory[] territoriesArray = model.getGameState().getBoard().getTerritories();
		for (int i = 0; i < territoriesArray.length; i++) {
			JSONObject details = new JSONObject();
			Territory territory = territoriesArray[i];
			details.put("territoryID", territoriesArray[i].getId());
			if (territory.getOwner() == null) {

				details.put("playerId", JSONObject.NULL);
			} else {

				details.put("playerId", territoriesArray[i].getOwner().getId());
			}
			details.put("armies", territoriesArray[i].getOccupyingArmies().size());
			territories.put(details);
		}
		object.put("Territories", territories);
        return object.toString();
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

    public int tradeInCards(int playerId, int[] cards, int numberOfArmies) throws BoardException, IllegalMoveException {
        Card[] realCards = new Card[cards.length];
        for (int i = 0; i < cards.length; i ++){
            realCards[i] = model.getGameState().getCardsById(cards[i]);
        }
        ClientCardMethod.tradeInCards(model.getGameState().getPlayerById(playerId), realCards, model);
        return playerId;
    }

    public void attack(int attackingPlayer, int sourceTerritory, int destinationTerritory, int numberOfArmies) throws BoardException, IllegalMoveException {
    	Moves.attack(model.getGameState().getPlayerById(attackingPlayer), model.getGameState(), sourceTerritory, destinationTerritory, numberOfArmies);
    }
    
    public boolean defend(int attackingPlayer, int attackingTerritory, int defendingTerritory, int numberOfAttackingArmies, int numberOfDefendingArmies, int[] dieRolls) throws BoardException, IllegalMoveException {

            return Moves.defendTerritory(model.getGameState().getPlayerById(attackingPlayer),model.getGameState(), attackingTerritory,defendingTerritory,numberOfAttackingArmies, numberOfDefendingArmies, dieRolls );

    }

    public void attackCapture(int attackingPlayer, int attackingTerritory, int defendingTerritory, int numberOfArmies) throws BoardException, IllegalMoveException {
            Moves.capture(model.getGameState().getPlayerById(attackingPlayer),model.getGameState(),attackingTerritory,defendingTerritory, numberOfArmies);
    }

    public void fortify(int playerId, int sourceTerritory, int destinationTerritory, int numberOfArmies) throws BoardException, IllegalMoveException {
         Moves.fortify(model.getGameState().getPlayerById(playerId),model.getGameState(),sourceTerritory, destinationTerritory, numberOfArmies);
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
    
    public Model getModel() {
    	return model;
    }
}

