package controller;

import java.util.ArrayList;

import exceptions.BoardException;
import exceptions.IllegalMoveException;
import model.Model;
import model.Player;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import view.INetworkView;

public class GameStateManager {
	Model model;
	static Logger log = LogManager.getLogger(NewController.class.getName());
	
	
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
	public void addPlayer(int id, String name) {
		// TODO check for no more than 6 players
		Player me = new Player();
		me.setName(name);
		me.setId(id);
		model.getGameState().getPlayers().add(me);
		model.getGameState().setNumberOfPlayers(model.getGameState().getNumberOfPlayers() + 1);
	}
	
	/*
	 * Checks the game is set up correctly
	 */
	public boolean checkReady() {
		boolean ready = true;
		if(model.getGameState().getNumberOfPlayers() >= 3 && model.getGameState().getNumberOfPlayers() <= 6) {
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

   
}
