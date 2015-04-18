package worlddomination.server.controller;

import worlddomination.server.actions.*;
import worlddomination.server.model.Player;
import worlddomination.server.network.ClientResponseGenerator;
import worlddomination.server.network.RiskClient;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import worlddomination.server.program.Constants;
import worlddomination.shared.updates.*;
import worlddomination.server.view.ControllerApiInterface;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map;

public class ClientController implements Runnable {

    ControllerApiInterface view;
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

    public ClientController(ControllerApiInterface view) {
        this.view = view;
        this.gameStateManager = new GameStateManager();
        this.client = new RiskClient();
        this.responseGenerator = new ClientResponseGenerator(client);
        this.acknowledgementManager = new AcknowledgementManager();
    }

    boolean collectingAcknowledgements = false;

    public void run() {
        addLocalPlayerInfo();
        join();
        gameLoop();
    }

    /*
    * Adds the player information for the local player.
    */
    public void addLocalPlayerInfo() {
        LocalPlayerName response = (LocalPlayerName) view.addUpdateAndWaitForResponse(new LocalPlayerName());
        String name = response.getName();
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

    private void executeActionsUntilIncludingType(Action type) {
        while (!actions.isEmpty()) {
            Action nextAction = actions.poll();
            executeAction(nextAction);
            if (type.getClass().equals(nextAction.getClass())) {
                break;
            }
        }
    }

    private void executeAllCurrentAcknowledgements() {
        while (!actions.isEmpty()) {
            Action nextAction = actions.peek();
            if (nextAction instanceof Acknowledgement) {
              if(((Acknowledgement) nextAction).getAcknowledgementId() == acknowledgementManager.getAcknowledgementId()) {
                  nextAction = actions.poll();
                  executeAction(nextAction);
              }
            }
            break;
        }
        checkAcknowledgement();
    }

    private void executeAction(Action action) {
        if (action instanceof Acknowledgement) {
            acknowledgement((Acknowledgement) action);
        } else   if (action instanceof Ping) {
            ping((Ping) action);
            collectingAcknowledgements = true;
        } else{
            if (collectingAcknowledgements) {
                checkAcknowledgement();
            }

            if (action instanceof Ready) {
                ready((Ready) action);
            } else if (action instanceof RejectJoinGame) {
                rejectJoinGame((RejectJoinGame) action);
            } else if (action instanceof AcceptJoinGame) {
                acceptJoinGame((AcceptJoinGame) action);
            } else if (action instanceof PlayersJoined) {
                playersJoined((PlayersJoined) action);
            } else if (action instanceof InitialiseGame) {
                initialiseGame((InitialiseGame) action);
            } else if (action instanceof RollHash) {
                rollHash((RollHash) action);
            } else if (action instanceof RollNumber) {
                rollNumber((RollNumber) action);
            } else if (action instanceof Setup) {
                setup((Setup) action);
            }
        }

    }

    private void checkAcknowledgement() {
        boolean complete;
        if(state == State.INITIALISE) {
            // In the initialse state the messages are sent by the server, so all clients have to acknoledge
            complete = acknowledgementManager.isAcknowledgedByAllPlayers(gameStateManager.model.getGameState().getNumberOfPlayers() +1);
        } else {
            complete = acknowledgementManager.isAcknowledgedByAllPlayers(gameStateManager.model.getGameState().getNumberOfPlayers());
        }
        if(complete) {
            collectingAcknowledgements = false;
        } else {
            System.out.print("Missing acknowledgement.");
            shutDown();
        }
    }

    private void setFirstPlayerId() {

        Collections.sort(gameStateManager.model.getGameState().getPlayers(), Player.PlayerComparator);

        if (dieRolls.length == 1) {
            currentPlayer = dieRolls[0];
            // TODO take out
            currentPlayer = 3;
        } else {
            // TODO errorhandling
            shutDown();
        }
        dieRolls = null;
        state = State.CARDS;
        int numOfCards = gameStateManager.model.getGameState().getCards().size();
        roll(new Roll(numOfCards, numOfCards, gameStateManager.getLocalPlayerId()));
    }

    private void shuffle() {
        int numberOfCards = gameStateManager.model.getGameState().getCards().size();
        if (dieRolls.length == numberOfCards) {
            for (int i = 0; i < numberOfCards; i++) {
                Collections.swap(gameStateManager.model.getGameState().getCards(), i, dieRolls[i]);
            }
            state = State.CLAIM;
        } else {
            // TODO error handling
            shutDown();
        }
        setupBoard();
    }


    private void setupBoard() {

        Map<Integer, Integer> numberOfArmies = new HashMap<>();
        for(Player player: gameStateManager.model.getGameState().getPlayers()) {
            numberOfArmies.put(player.getId(), calculateNumberOfInitialArmies());
        }
        claimTerritory(numberOfArmies);
        distributeTerritory(numberOfArmies);
    }

    private void claimTerritory( Map<Integer, Integer> numberOfArmies) {
        boolean allTerritoriesClaimTerritoryed = false;

        while (!allTerritoriesClaimTerritoryed) {
            view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
            numberOfArmies.put(currentPlayer, numberOfArmies.get(currentPlayer) - 1);
            if (currentPlayer == gameStateManager.getLocalPlayerId()) {
                executeAllCurrentAcknowledgements();
                ClaimTerritory claim = (ClaimTerritory) view.addUpdateAndWaitForResponse(new ClaimTerritory("Please claim a territory"));
                localSetupTurn(claim);
            } else {
                executeActionsUntilIncludingType(new Setup(0, 0, 0));
            }
            allTerritoriesClaimTerritoryed = gameStateManager.allTerritoriesClaimed();
        }

        state = State.DISTRIBUTE;
    }

    private void distributeTerritory( Map<Integer, Integer> numberOfArmies) {
        while(numberOfArmies.get(currentPlayer) > 0) {
            view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
            numberOfArmies.put(currentPlayer, numberOfArmies.get(currentPlayer) - 1);
            if (currentPlayer == gameStateManager.getLocalPlayerId()) {
                executeAllCurrentAcknowledgements();
                DistributeArmy distribute = (DistributeArmy) view.addUpdateAndWaitForResponse(new DistributeArmy("Please select a territory"));
                localSetupTurn(distribute);
            } else {
                executeActionsUntilIncludingType(new Setup(0, 0, 0));
            }
        }
    }

    private void setNextPlayer() {
        ArrayList<Player> players = gameStateManager.model.getGameState().getPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == currentPlayer) {
                if (i < players.size() - 1) {
                    currentPlayer = players.get(i + 1).getId();
                } else {
                    currentPlayer = players.get(0).getId();
                }
                break;
            }

        }
    }

    private void rejectJoinGame(RejectJoinGame action) {
        view.addUpdate(new Rejection(action.getErrorMessage()));
        shutDown();
    }

    private void acceptJoinGame(AcceptJoinGame action) {
        // TODO generate proper public key
        String publicKey = "My magically generated public key.";
        gameStateManager.addPlayer(action.getPlayerId(), gameStateManager.model.getPlayerInfo().getUserName(), publicKey);
        gameStateManager.model.getPlayerInfo().setId(action.getPlayerId());
    }

    private void playersJoined(PlayersJoined action) {
        Map<Integer, String[]> players = action.getPlayers();
        Lobby lobby = new Lobby();
        //TODO: Needs to send an array list of players
        for (Map.Entry<Integer, String[]> player : players.entrySet()) {
        	lobby.addPlayerToListOfPlayers(new LobbyPlayer(player.getKey(), player.getValue()[0], player.getValue()[1]));
            if (!gameStateManager.checkPlayerExists(player.getKey())) {
                if (player.getValue().length == 2) {
                    gameStateManager.addPlayer(player.getKey(), player.getValue()[0], player.getValue()[1]);
                } else {
                    // TODO proper error handling
                    shutDown();
                }
            }
        }
        
        view.addUpdate(lobby);
    }

    private void ping(Ping ping) {
        if (ping.getPlayerId() == -1 || ping.getPlayerId() == 0) {
            hostPing(ping);
        } else {
            clientPing(ping);
        }
    }

    private void hostPing(Ping ping) {
        PingReady ready = (PingReady) view.addUpdateAndWaitForResponse(new PingReady());
        if (ready.getIsReady()) {
            // respond to ping
            responseGenerator.pingGenerator(gameStateManager.model.getGameState().getNumberOfPlayers(), gameStateManager.getLocalPlayerId());
            acknowledgementManager.addAcknowledgement(gameStateManager.getLocalPlayerId(), -1);

            // if the host is playing, his ping must also be treated as a client ping
            if (ping.getPlayerId() == 0) {
                clientPing(ping);
            }
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

        // prepare for receiving acknowledgements
        acknowledgementManager.expectAcknowledgement();
        collectingAcknowledgements = true;

        // if the host is a player, his message counts towards the acknowledgements
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
        roll(new Roll(1, gameStateManager.model.getGameState().getNumberOfPlayers(), gameStateManager.getLocalPlayerId()));
    }

    private void acknowledgement(Acknowledgement acknowledgement) {
        int responseCode = -1;
        responseCode = acknowledgementManager.addAcknowledgement(acknowledgement.getPlayerId(), acknowledgement.getAcknowledgementId());
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
        if(networkDieManager.addHash(rollHash.getPlayerId(), rollHash.getHash(), gameStateManager.model.getGameState().getNumberOfPlayers())) {
            responseGenerator.rollNumberGenerator("This is my number", gameStateManager.getLocalPlayerId());
        }
    }

    private void rollNumber(RollNumber number) {
        int returnCode = networkDieManager.addNumber(number.getPlayerId(), number.getNumber());
        if (returnCode != 0) {
            System.out.println("Wrong hash for number");
            shutDown();
        }
        if (networkDieManager.isDieRollPossible(gameStateManager.model.getGameState().getNumberOfPlayers())) {
            dieRolls = networkDieManager.getDieRolls();
            if (state == State.INITIALISE) {
                setFirstPlayerId();
                state = State.SHUFFLE;
            } else if (state == State.SHUFFLE) {
                shuffle();
                state = State.CLAIM;

            }
        }
    }

    private void setup(Setup setup) {
        if (state == State.CLAIM) {
            claim(setup.getPlayerId(), setup.getTerritoryId());
        } else if (state == State.DISTRIBUTE) {
            distribute(setup.getPlayerId(), setup.getTerritoryId());
        }

        // prepare for receiving acknowledgements
        acknowledgementManager.expectAcknowledgement();
        collectingAcknowledgements = true;

        sendAcknowledgement();
    }

    private void localSetupTurn(Update update) {
        if(update instanceof ClaimTerritory) {
            localClaimTerritoryTurn((ClaimTerritory) update);
        } else if(update instanceof DistributeArmy) {
            localDistributeArmyTurn((DistributeArmy) update);
        }

        acknowledgementManager.expectAcknowledgement();
        collectingAcknowledgements = true;
    }

    private void localClaimTerritoryTurn(ClaimTerritory claim) {
        responseGenerator.setupGenerator(gameStateManager.getLocalPlayerId(), claim.getTerritoryID(), 1);
        claim(gameStateManager.getLocalPlayerId(), claim.getTerritoryID());
    }

    private void localDistributeArmyTurn(DistributeArmy distribute) {
        responseGenerator.setupGenerator(gameStateManager.getLocalPlayerId(), distribute.getTerritoryID(), 1);
        distribute(gameStateManager.getLocalPlayerId(), distribute.getTerritoryID());
    }

    private void distribute(int playerId, int territoryId) {
        // TODO error checking
        try {
            gameStateManager.setup(playerId, territoryId);
        } catch (Exception e) {
            System.err.print("An invalid move occurred claiming a territory.");
            shutDown();
        }
        setNextPlayer();
    }

    private void claim(int playerId, int territoryId) {
        // TODO error checking
        try {
            gameStateManager.setup(playerId, territoryId);
        } catch (Exception e) {
            System.err.print("An invalid move occurred claiming a territory.");
            shutDown();
        }
        setNextPlayer();
    }

    public int calculateNumberOfInitialArmies() {
        switch (gameStateManager.model.getGameState().getNumberOfPlayers()) {
            case 3:
                return 35;
            case 4:
                return 30;
            case 5:
                return 25;
            case 6:
                return 20;
            default:
                System.out.println("Wrong number of players.");
                break;
        }
        return -1;
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
