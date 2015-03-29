package controller;

import network.ClientResponseGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import program.Constants;
import view.INetworkView;

public class NewController {

    INetworkView view;
    GameStateManager gameStateManager;
    static Logger log = LogManager.getLogger(NewController.class.getName());

    public NewController(INetworkView view) {
        this.view = view;
        this.gameStateManager = new GameStateManager();
    }

    /*
     * Adds the player that is resembled by the running instance of this program.
     */
    public void addLocalPlayer(int id) {
        String name = view.getLocalPlayerName();
        gameStateManager.addPlayer(id, name);
    }

    /*
     * Adds the players that will act as opponents.
     */
    public void addPlayer(int id, String name) {
        gameStateManager.addPlayer(id, name);
    }

    public void run() {
        join(gameStateManager.model.getPlayerInfo().getUserName());
    }

<<<<<<< HEAD

    private void join(String name) {
        //TODO must be sent somewhere
        ClientResponseGenerator.joinGameGenerator(Constants.getSupportedVersions(), Constants.getSupportedFeatures(), name);
=======
    private void join() {
        ClientResponseGenerator.joinGameGenerator(Constants.getSupportedVersions(), Constants.getSupportedFeatures(),view.getLocalPlayerName());
>>>>>>> 227f480472e47c200f5513484464b7086b84d7db
    }
}
