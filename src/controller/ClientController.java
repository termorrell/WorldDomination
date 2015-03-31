package controller;

import actions.*;
import network.ClientResponseGenerator;
import network.RiskClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Constants;
import view.INetworkView;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ClientController {

    INetworkView view;
    GameStateManager gameStateManager;
    RiskClient client;
    ClientResponseGenerator responseGenerator;
    static Logger log = LogManager.getLogger(ClientController.class.getName());

    Queue<Action> actions = new LinkedList<>();

    boolean won = false;

    public ClientController(INetworkView view) {
        this.view = view;
        this.gameStateManager = new GameStateManager();
        this.client = new RiskClient();
        this.responseGenerator = new ClientResponseGenerator(client);
    }

    public void run() {
        addLocalPlayerInfo();
        join();
        gameLoop();
    }

    /*
    * Adds the player information for the local player.
    */
    public void addLocalPlayerInfo() {
        String name = view.getLocalPlayerName();
        gameStateManager.addLocalPlayerInfo(name);
    }

    private void join() {
        responseGenerator.joinGameGenerator(Constants.getSupportedVersions(), Constants.getSupportedFeatures(), view.getLocalPlayerName());
    }

    public void gameLoop() {

        while(!won) {
            executeActions();
        }
    }

    private void executeActions() {
        while(!actions.isEmpty()) {
            Action nextAction = actions.poll();
            executeAction(nextAction);
        }
    }

    private void executeAction(Action action) {
        if(action instanceof RejectJoinGame) {
            rejectJoinGame((RejectJoinGame) action);
        } else if(action instanceof AcceptJoinGame) {
            acceptJoinGame((AcceptJoinGame) action);
        }
    }

    private void rejectJoinGame(RejectJoinGame action) {
        view.displayRejection(action.getErrorMessage());
        shutDown();
    }

    private void acceptJoinGame(AcceptJoinGame action) {
        // TODO generate proper public key
        String publicKey = "My magically generated public key.";
        gameStateManager.addPlayer(action.getPlayerId(), gameStateManager.model.getPlayerInfo().getUserName(), publicKey);
    }

    private void playersJoined(PlayersJoined action) {
        Map<Integer, String[]> players = action.getPlayers();
        view.displayJoinedPlayers(players);
        for(Map.Entry<Integer, String[]> player : players.entrySet()) {
            if(!gameStateManager.checkPlayerExists(player.getKey())) {
                if(player.getValue().length == 2) {
                    gameStateManager.addPlayer(player.getKey(), player.getValue()[0], player.getValue()[1]);
                } else {
                    // TODO proper error handling
                    shutDown();
                }
            }
        }
    }

    /**
     * Will adapt the local game state according to the given action.
     *
     * For use by networking, ui and ai to propagate information to the controller.
     *
     * @param action - influences that affect the game
     */
    public synchronized void handleAction(Action action) {
        actions.add(action);
    }

    private void shutDown() {
        // TODO close resources etc
        System.exit(0);
    }
}
