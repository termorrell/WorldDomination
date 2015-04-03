package controller;

import actions.*;
import model.Player;
import network.ClientResponseGenerator;
import network.RiskClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import program.Constants;
import view.INetworkView;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ClientController {

    INetworkView view;
    GameStateManager gameStateManager;
    RiskClient client;
    ClientResponseGenerator responseGenerator;
    static Logger log = LogManager.getLogger(ClientController.class.getName());

    Queue<Action> actions = new LinkedList<>();

    boolean won = false;

    AcknowledgementManager acknowledgementManager = null;
    NetworkDieManager networkDieManager = null;

    Action cachedAction; // Action will be saved in here while the die roll is completed
    State state = State.INITIALISE;
    int currentPlayer;
    int[] dieRolls;

    public ClientController(INetworkView view) {
        this.view = view;
        this.gameStateManager = new GameStateManager();
        this.client = new RiskClient();
        this.responseGenerator = new ClientResponseGenerator(client);
        this.acknowledgementManager = new AcknowledgementManager();
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
        responseGenerator.joinGameGenerator(Constants.getSupportedVersions(), Constants.getSupportedFeatures(), gameStateManager.model.getPlayerInfo().getUserName());
    }

    public void gameLoop() {

        while (!won) {
            executeActions();
        }
    }

    private void executeActions() {
        while (!actions.isEmpty()) {
            Action nextAction = actions.poll();
            executeAction(nextAction);
        }
    }

    private void executeAction(Action action) {
        if (action instanceof Acknowledgement) {
            acknowledgement((Acknowledgement) action);
        } else {
            if (action instanceof Ready) {
                ready((Ready) action);
            } else {
                // Check that there are no outstanding acknowledgements
                if (!acknowledgementManager.isAcknowledgedByAllPlayers(gameStateManager.model.getGameState().getNumberOfPlayers())) {
                    // TODO missing acknowledgement
                    shutDown();
                }
                if (action instanceof RejectJoinGame) {
                    rejectJoinGame((RejectJoinGame) action);
                } else if (action instanceof AcceptJoinGame) {
                    acceptJoinGame((AcceptJoinGame) action);
                } else if (action instanceof PlayersJoined) {
                    playersJoined((PlayersJoined) action);
                } else if (action instanceof Ping) {
                    ping((Ping) action);
                } else if (action instanceof InitialiseGame) {
                    initialiseGame((InitialiseGame) action);
                } else if (action instanceof Roll) {
                    roll((Roll) action);
                } else if (action instanceof RollHash) {
                    rollHash((RollHash) action);
                } else if (action instanceof RollNumber) {
                    rollNumber((RollNumber) action);
                }
            }
        }
    }

    private void setFirstPlayerId() {
        if(dieRolls.length > 0) {
            currentPlayer = dieRolls[0];
        }
        dieRolls =  null;
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
        for (Map.Entry<Integer, String[]> player : players.entrySet()) {
            if (!gameStateManager.checkPlayerExists(player.getKey())) {
                if (player.getValue().length == 2) {
                    gameStateManager.addPlayer(player.getKey(), player.getValue()[0], player.getValue()[1]);
                } else {
                    // TODO proper error handling
                    shutDown();
                }
            }
        }
    }

    private void ping(Ping ping) {
        if (ping.getPlayerId() == -1 || ping.getPlayerId() == 0) {
            hostPing(ping);
        } else {
            clientPing(ping);
        }
    }

    private void hostPing(Ping ping) {
        if (view.getPingReadyConfirmation()) {
            responseGenerator.pingGenerator(gameStateManager.model.getGameState().getNumberOfPlayers(), gameStateManager.getLocalPlayerId());
            if (ping.getPlayerId() == 0) {
                clientPing(ping);
            }
            clientPing(new Ping(0, gameStateManager.getLocalPlayerId()));
        } else {
            // TODO error handling
            shutDown();
        }
    }

    private void clientPing(Ping ping) {
        int returnCode = acknowledgementManager.addAcknowledgement(ping.getPlayerId(), -1);
        if (returnCode == -1) {
            // TODO error handling
            shutDown();
        }
    }

    private void ready(Ready ready) {
        if (!acknowledgementManager.isAcknowledgedByAllPlayers(gameStateManager.model.getGameState().getNumberOfPlayers())) {
            removeUnreadyPlayers();
        }
        acknowledgementManager.expectAcknowledgement();
        if (ready.getPlayerId() == 0) {
            acknowledgementManager.addAcknowledgement(0, acknowledgementManager.getAcknowledgementId());
        }
        sendAcknowledgement();
    }

    private void removeUnreadyPlayers() {
        Set<Integer> acknowledgedPlayers = acknowledgementManager.getPlayers();
        ArrayList<Player> players = gameStateManager.model.getGameState().getPlayers();
        Set<Integer> missedPlayers = new HashSet<>();
        for (Player player : players) {
            if (!acknowledgedPlayers.contains(player.getId())) {
                missedPlayers.add(player.getId());
            }
        }
        for (int playerId : missedPlayers) {
            gameStateManager.removePlayer(playerId);
        }
    }

    private void initialiseGame(InitialiseGame initialiseGame) {
        // TODO once i actually know what version i'm implementing....
    }

    private void acknowledgement(Acknowledgement acknowledgement) {
        int responseCode = -1;
        if (acknowledgementManager.getAcknowledgementId() == acknowledgement.getAcknowledgementId()) {
            responseCode = acknowledgementManager.addAcknowledgement(acknowledgement.getPlayerId(), acknowledgement.getAcknowledgementId());
        }
        if (responseCode != 0) {
            // TODO error handling
            shutDown();
        }
    }

    private void roll(Roll roll) {
        networkDieManager = new NetworkDieManager(roll.getPlayerId(), roll.getNumberOfRolls(), roll.getNubmerOfFaces());
        try {
            String hash = networkDieManager.generateLocalHash();
            responseGenerator.rollHashGenerator(hash, gameStateManager.getLocalPlayerId());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("A problem occured calculating the hash for the die roll.");
            shutDown();
        }
    }

    private void rollHash(RollHash rollHash) {
        networkDieManager.addHash(rollHash.getPlayerId(), rollHash.getHash());
    }

    private void rollNumber(RollNumber number) {
        int returnCode = networkDieManager.addNumber(number.getPlayerId(), number.getNumber());
        if (returnCode != 0) {
            System.out.println("Wrong hash for number");
            shutDown();
        }
        if(networkDieManager.isDieRollPossible(gameStateManager.model.getGameState().getNumberOfPlayers())) {
            dieRolls = networkDieManager.getDieRolls();
            setFirstPlayerId();
        }
    }

    private void sendAcknowledgement() {
        responseGenerator.ackGenerator(acknowledgementManager.getAcknowledgementId(), gameStateManager.getLocalPlayerId());
        acknowledgementManager.addAcknowledgement(gameStateManager.getLocalPlayerId(), acknowledgementManager.getAcknowledgementId());
    }

    /**
     * Will adapt the local game state according to the given action.
     * <p/>
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
