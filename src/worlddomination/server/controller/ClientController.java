package worlddomination.server.controller;

import worlddomination.server.actions.*;
import worlddomination.server.exceptions.BoardException;
import worlddomination.server.exceptions.IllegalMoveException;
import worlddomination.server.model.Card;
import worlddomination.server.model.GameState;
import worlddomination.server.model.Move;
import worlddomination.server.model.Player;
import worlddomination.server.network.ClientResponseGenerator;
import worlddomination.server.network.RiskClient;

import org.apache.jasper.tagplugins.jstl.ForEach;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import worlddomination.server.program.Constants;
import worlddomination.shared.updates.*;
import worlddomination.server.view.ControllerApiInterface;
import worlddomination.server.view.IView;
import worlddomination.server.view.Input;

import java.io.BufferedReader;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

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

	Action cachedAction; // Action will be saved in here while the die roll is
							// completed
	State state = State.INITIALISE;

	int currentPlayer;
	int[] dieRolls;

	boolean capturedTerritory = false;

	int numberOfArmiesForTradedCards = 4;

	public ClientController(ControllerApiInterface view, String ipAddress, int port) {
		this.view = view;
		this.gameStateManager = new GameStateManager();
		// this.client = new RiskClient();
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
		LocalPlayerName response = (LocalPlayerName) view
				.addUpdateAndWaitForResponse(new LocalPlayerName());
		String name = response.getName();
		gameStateManager.addLocalPlayerInfo(name);
	}

	private void join() {
		responseGenerator.joinGameGenerator(Constants.getSupportedVersions(),
				Constants.getSupportedFeatures(), gameStateManager.model
						.getPlayerInfo().getUserName());
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
		boolean checked = false;
		while (!actions.isEmpty()) {
			Action nextAction = actions.peek();
			if (nextAction instanceof Acknowledgement) {
				if (((Acknowledgement) nextAction).getAcknowledgementId() == acknowledgementManager
						.getAcknowledgementId()) {
					checked = true;
					nextAction = actions.poll();
					executeAction(nextAction);
				} else {
					break;
				}
			} else {
				break;
			}
		}
		if (checked) {
			checkAcknowledgement();
		}
	}

	private void executeAction(Action action) {
		if (action instanceof Acknowledgement) {
			acknowledgement((Acknowledgement) action);
		} else if (action instanceof Ping) {
			ping((Ping) action);
			collectingAcknowledgements = true;
		} else {
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
			} else if (action instanceof PlayCards) {
				playCards((PlayCards) action);
			} else if (action instanceof Deploy) {
				deploy((Deploy) action);
			} else if (action instanceof Attack) {
				attack((Attack) action);
			} else if (action instanceof Defend) {
				defend((Defend) action);
			} else if (action instanceof AttackCapture) {
				attackCapture((AttackCapture) action);
			} else if (action instanceof Fortify) {
				fortify((Fortify) action);
			} else {
				System.out
						.println("Action not implemented" + action.getClass());
			}
		}
	}

	private void checkAcknowledgement() {
		boolean complete;
		if (state == State.INITIALISE) {
			// In the initialse state the messages are sent by the server, so
			// all clients have to acknoledge
			complete = acknowledgementManager
					.isAcknowledgedByAllPlayers(gameStateManager.model
							.getGameState().getNumberOfPlayers() + 1);
		} else {
			complete = acknowledgementManager
					.isAcknowledgedByAllPlayers(gameStateManager.model
							.getGameState().getNumberOfPlayers());
		}
		if (complete) {
			collectingAcknowledgements = false;
		} else {
			System.out.print("Missing acknowledgement.");
			shutDown();
		}
	}

	private void processDieRolls() {
		boolean dieRollStarted = false;
		while (true) {
			if (!actions.isEmpty()) {
				Action nextAction = actions.peek();
				if (!dieRollStarted) {
					if (nextAction instanceof RollHash) {
						dieRollStarted = true;
						nextAction = actions.poll();
						executeAction(nextAction);
					}
				} else {
					if ((nextAction instanceof RollHash)
							|| (nextAction instanceof RollNumber)) {
						nextAction = actions.poll();
						executeAction(nextAction);
					} else {
						break;
					}
				}

			}
		}
	}

	private void setFirstPlayerId() {

		Collections.sort(gameStateManager.model.getGameState().getPlayers(),
				Player.PlayerComparator);

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
		int numOfCards = gameStateManager.model.getGameState().getCards()
				.size();
		roll(new Roll(numOfCards, numOfCards,
				gameStateManager.getLocalPlayerId()));
	}

	private void shuffle() {
		int numberOfCards = gameStateManager.model.getGameState().getCards()
				.size();
		if (dieRolls.length == numberOfCards) {
			for (int i = 0; i < numberOfCards; i++) {
				Collections.swap(gameStateManager.model.getGameState()
						.getCards(), i, dieRolls[i]);
			}
			state = State.CLAIM;
		} else {
			// TODO error handling
			shutDown();
		}
		play();
	}

	private void play() {

		Map<Integer, Integer> numberOfArmies = new HashMap<>();
		for (Player player : gameStateManager.model.getGameState().getPlayers()) {
			numberOfArmies
					.put(player.getId(), calculateNumberOfInitialArmies());
		}
		claimTerritory(numberOfArmies);
		distributeTerritory(numberOfArmies);
		makeTurns();
	}

	private void claimTerritory(Map<Integer, Integer> numberOfArmies) {
		boolean allTerritoriesClaimed = false;

		while (!allTerritoriesClaimed) {
			view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
			numberOfArmies.put(currentPlayer,
					numberOfArmies.get(currentPlayer) - 1);
			if (currentPlayer == gameStateManager.getLocalPlayerId()) {
				executeAllCurrentAcknowledgements();
				ClaimTerritory claimTerritory = (ClaimTerritory) view
						.addUpdateAndWaitForResponse(new ClaimTerritory("Please claim a territory"));
				localSetupTurn(claimTerritory);
			} else {
				executeActionsUntilIncludingType(new Setup(0, 0, 0));
			}
			allTerritoriesClaimed = gameStateManager.allTerritoriesClaimed();
		}

		state = State.DISTRIBUTE;
	}

	private void distributeTerritory(Map<Integer, Integer> numberOfArmies) {
		while (numberOfArmies.get(currentPlayer) > 0) {
			view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
			numberOfArmies.put(currentPlayer,
					numberOfArmies.get(currentPlayer) - 1);
			if (currentPlayer == gameStateManager.getLocalPlayerId()) {
				executeAllCurrentAcknowledgements();
				DistributeArmy distributeArmy = (DistributeArmy) view
						.addUpdateAndWaitForResponse(new DistributeArmy("Please select a territory"));
				localSetupTurn(distributeArmy);
			} else {
				executeActionsUntilIncludingType(new Setup(0, 0, 0));
			}
		}
		System.out.print("start with the real stuff");
	}

	private void makeTurns() {
		while (!won) {
			if (currentPlayer == gameStateManager.getLocalPlayerId()) {
				localMakeTurn();
				executeAllCurrentAcknowledgements();
			} else {
				executeActionsUntilIncludingType(new Fortify(0, 0, 0, 0, 0));
			}
			setNextPlayer();
		}
	}

	private void localMakeTurn() {
    	// trade in cards
        boolean canTradeInCards = ClientCardMethod.canTradeInCards(gameStateManager.getModel().getGameState().getPlayerById(currentPlayer), gameStateManager.model);
        String logMessage = "It's your turn.";
        CurrentPlayer currentPlayerUpdate = new CurrentPlayer(logMessage, currentPlayer, true);
        view.addUpdate(currentPlayerUpdate);
        
        int numberOfArmies = 0;
        
        int index =0;
        while(canTradeInCards && index < 2) {
        	int returnValue = tradeInCards();
            canTradeInCards = ClientCardMethod.canTradeInCards(gameStateManager.getModel().getGameState().getPlayerById(currentPlayer), gameStateManager.model);
            index++;
            if(returnValue == -1) {
            	break;
            } else {
            	numberOfArmies += returnValue;
            }
        }
        
        // calculate armies
        numberOfArmies += calculateNumberOfArmies(gameStateManager.model.getGameState().getPlayerById(currentPlayer), gameStateManager.model.getGameState());
        
        // reinforce
        Reinforce reinforce = new Reinforce("Please select territories to reinforce.", numberOfArmies);
        reinforce = (Reinforce) view.addUpdateAndWaitForResponse(reinforce);
        int[] territories = reinforce.getAllocationOfArmies();
        reinforceTerritories(territories);
        view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
        
        // attack or fortify loop
        boolean wantToTurn = true;
        
        while(wantToTurn) {
        	MakeTurn turn = new MakeTurn("Please make a turn", "", false);
        	turn = (MakeTurn) view.addUpdateAndWaitForResponse(turn);
        	
        	makeLocalTurn(turn);
        		
        	view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
        	if(turn.getType().equalsIgnoreCase("quit") || turn.getType().equalsIgnoreCase("fortify")) {
        		wantToTurn = false;
        		if(turn.getType().equalsIgnoreCase("quit")) {
        			acknowledgementManager.expectAcknowledgement();
        			collectingAcknowledgements = true;
        			responseGenerator.fortifyGenerator(new int[0],currentPlayer, acknowledgementManager.id );
        			executeAllCurrentAcknowledgements();
        		}
        	}
        }
        
        
            drawCards();
        
    }
	
	private void makeLocalTurn(MakeTurn turn) {
		if(turn.getType().equalsIgnoreCase("Attack")) {
			localAttack(turn.getSourceTerritory(), turn.getDestinationTerritory(), turn.getNumberOfArmies());
		} else if(turn.getType().equalsIgnoreCase("Fortify")) {
			localFortify(turn.getSourceTerritory(), turn.getDestinationTerritory(), turn.getNumberOfArmies());
		}
	}
	
	private void localAttack(int sourceTerritory, int destinationTerritory, int numberOfArmies) {
		try {
			gameStateManager.attack(currentPlayer, sourceTerritory, destinationTerritory, numberOfArmies);
		} catch (BoardException | IllegalMoveException e) {
			e.printStackTrace();
			shutDown();
		}
		
		acknowledgementManager.expectAcknowledgement();
		int[] attackInfo = {sourceTerritory, destinationTerritory, numberOfArmies};
		responseGenerator.attackGenerator(attackInfo, currentPlayer, acknowledgementManager.id);
		executeAllCurrentAcknowledgements();
		checkAcknowledgement();
		
		cachedAction = new Attack(sourceTerritory, destinationTerritory, numberOfArmies, currentPlayer, 0);
		
		executeActionsUntilIncludingType(new Defend(0, 0, 0));
	}
	
	private void localFortify(int sourceTerritory, int destinationTerritory, int numberOfArmies) {
		try {
			gameStateManager.fortify(currentPlayer, sourceTerritory, destinationTerritory, numberOfArmies);
		} catch (BoardException | IllegalMoveException e) {
			e.printStackTrace();
			shutDown();
		}
		acknowledgementManager.expectAcknowledgement();
		int[] fortifyInfo = {sourceTerritory, destinationTerritory, numberOfArmies};
		responseGenerator.fortifyGenerator(fortifyInfo, currentPlayer, acknowledgementManager.id);
		executeAllCurrentAcknowledgements();
		checkAcknowledgement();
	}
	
	
	private void reinforceTerritories(int[] territories) {
		Map<Integer, Integer> armiesPerTerritory = new HashMap<> ();
		for (int i : territories) {
			if(armiesPerTerritory.containsKey(i)) {
				armiesPerTerritory.put(i, armiesPerTerritory.get(i));
			} else {
				armiesPerTerritory.put(i, 1);
			}
		}
		int[][] reinforcements =  new int[armiesPerTerritory.size()][2];
		int i = 0;
		for (Entry<Integer, Integer> entry : armiesPerTerritory.entrySet()) {
			reinforcements[i][0] = entry.getKey();
			reinforcements[i][1] = entry.getValue();
			i++;
		}		
		acknowledgementManager.expectAcknowledgement();
		responseGenerator.deployGenerator(reinforcements, currentPlayer, acknowledgementManager.id );
		executeAllCurrentAcknowledgements();
		checkAcknowledgement();
	}

	private int tradeInCards() {
		MakeTurn tradeIn = new MakeTurn("Would you like to trade in cards?", "", true);
		tradeIn = (MakeTurn) view.addUpdateAndWaitForResponse(tradeIn);

		if (tradeIn.getType().equalsIgnoreCase("tradein")) {

			// TODO actual: int[] cardIds = tradeIn1.getCards();
			int[] cardIds = { 1, 2, 3 };
			Card[] cards = new Card[cardIds.length];
			for (int i = 0; i < cards.length; i++) {
				cards[i] = gameStateManager.model.getGameState().getCardsById(
						cardIds[i]);
			}
			try {
				return ClientCardMethod.tradeInCards(
						gameStateManager.getModel().getGameState()
								.getPlayerById(currentPlayer), cards,
						gameStateManager.model);
			} catch (Exception e) {
				e.printStackTrace();
				shutDown();
			}
		}
		return -1;

	}

	private void setNextPlayer() {
		ArrayList<Player> players = gameStateManager.model.getGameState()
				.getPlayers();
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
		String message;
		CurrentPlayer player = null;
		if(currentPlayer == gameStateManager.getLocalPlayerId()) {
			message = "It's your turn.";
			player = new CurrentPlayer(message, currentPlayer, true);
		} else {
			message = "It's " + gameStateManager.getModel().getGameState().getPlayerById(currentPlayer).getName() + "'s turn";
			player = new CurrentPlayer(message, currentPlayer, false);
		}
		view.addUpdate(player);
	}

	private void rejectJoinGame(RejectJoinGame action) {
		view.addUpdate(new Rejection(action.getErrorMessage()));
		shutDown();
	}

	private void acceptJoinGame(AcceptJoinGame action) {
		// TODO generate proper public key
		String publicKey = "My magically generated public key.";
		gameStateManager.addPlayer(action.getPlayerId(), gameStateManager.model
				.getPlayerInfo().getUserName(), publicKey);
		gameStateManager.model.getPlayerInfo().setId(action.getPlayerId());
	}

	private void playersJoined(PlayersJoined action) {
		Map<Integer, String[]> players = action.getPlayers();
		Lobby lobby = new Lobby();
		// TODO: Needs to send an array list of players
		for (Map.Entry<Integer, String[]> player : players.entrySet()) {
			lobby.addPlayerToListOfPlayers(new LobbyPlayer(player.getKey(),
					player.getValue()[0], player.getValue()[1]));
			if (!gameStateManager.checkPlayerExists(player.getKey())) {
				if (player.getValue().length == 2) {
					gameStateManager.addPlayer(player.getKey(),
							player.getValue()[0], player.getValue()[1]);
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
		PingReady ready = (PingReady) view
				.addUpdateAndWaitForResponse(new PingReady());
		if (ready.getIsReady()) {
			// respond to ping
			responseGenerator.pingGenerator(gameStateManager.model
					.getGameState().getNumberOfPlayers(), gameStateManager
					.getLocalPlayerId());
			acknowledgementManager.addAcknowledgement(
					gameStateManager.getLocalPlayerId(), -1);

			// if the host is playing, his ping must also be treated as a client
			// ping
			if (ping.getPlayerId() == 0) {
				clientPing(ping);
			}
		} else {
			// TODO error handling
			shutDown();
		}
	}

	private void clientPing(Ping ping) {
		int returnCode = acknowledgementManager.addAcknowledgement(
				ping.getPlayerId(), -1);
		if (returnCode == -1) {
			// TODO error handling
			shutDown();
		}
	}

	private void ready(Ready ready) {
		if (!acknowledgementManager
				.isAcknowledgedByAllPlayers(gameStateManager.model
						.getGameState().getNumberOfPlayers())) {
			removeUnreadyPlayers();
		}

		// prepare for receiving acknowledgements
		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;

		// if the host is a player, his message counts towards the
		// acknowledgements
		if (ready.getPlayerId() == 0) {
			acknowledgementManager.addAcknowledgement(0,
					acknowledgementManager.getAcknowledgementId());
		}
		sendAcknowledgement();
	}

	private void removeUnreadyPlayers() {
		Set<Integer> acknowledgedPlayers = acknowledgementManager.getPlayers();
		ArrayList<Player> players = gameStateManager.model.getGameState()
				.getPlayers();
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
		roll(new Roll(1, gameStateManager.model.getGameState()
				.getNumberOfPlayers(), gameStateManager.getLocalPlayerId()));
	}

	private void acknowledgement(Acknowledgement acknowledgement) {
		int responseCode = acknowledgementManager.addAcknowledgement(
				acknowledgement.getPlayerId(),
				acknowledgement.getAcknowledgementId());
		if (responseCode != 0) {
			// TODO error handling
			shutDown();
		}
	}

	private void roll(Roll roll) {
		networkDieManager = new NetworkDieManager(roll.getPlayerId(),
				roll.getNumberOfRolls(), roll.getNubmerOfFaces());
		try {
			String hash = networkDieManager.generateLocalHash();
			responseGenerator.rollHashGenerator(hash,
					gameStateManager.getLocalPlayerId());
		} catch (NoSuchAlgorithmException e) {
			System.out
					.println("A problem occurred calculating the hash for the die roll.");
			shutDown();
		}
	}

	private void rollHash(RollHash rollHash) {
		if (networkDieManager.addHash(rollHash.getPlayerId(), rollHash
				.getHash(), gameStateManager.model.getGameState()
				.getNumberOfPlayers())) {
			responseGenerator.rollNumberGenerator("This is my number",
					gameStateManager.getLocalPlayerId());
		}
	}

	private void rollNumber(RollNumber number) {
		int returnCode = networkDieManager.addNumber(number.getPlayerId(),
				number.getNumber());
		if (returnCode != 0) {
			System.out.println("Wrong hash for number");
			shutDown();
		}
		if (networkDieManager.isDieRollPossible(gameStateManager.model
				.getGameState().getNumberOfPlayers())) {
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
		if (update instanceof ClaimTerritory) {
			localClaimTurn((ClaimTerritory) update);
		} else if (update instanceof DistributeArmy) {
			localDistributeTurn((DistributeArmy) update);
		}

		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;
	}

	private void localClaimTurn(ClaimTerritory claimTerritory) {
		responseGenerator.setupGenerator(gameStateManager.getLocalPlayerId(),
				claimTerritory.getTerritoryID(), 1);
		claim(gameStateManager.getLocalPlayerId(),
				claimTerritory.getTerritoryID());
	}

	private void localDistributeTurn(DistributeArmy distributeArmy) {
		responseGenerator.setupGenerator(gameStateManager.getLocalPlayerId(),
				distributeArmy.getTerritoryID(), 1);
		distribute(gameStateManager.getLocalPlayerId(),
				distributeArmy.getTerritoryID());
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

	private void playCards(PlayCards playCards) {
		if (playCards.getPlayerId() != currentPlayer) {
			// TODO error handling
			shutDown();
		} else {
			// TODO check if player actually has cards?
			for (int[] cards : playCards.getSetsTradedIn()) {
				try {
					gameStateManager.tradeInCards(playCards.getPlayerId(),
							cards, playCards.getNumberOfArmiesForCards());
				} catch (Exception e) {
					e.printStackTrace();
					shutDown();
				}
			}
		}
		// prepare for receiving acknowledgements
		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;
		sendAcknowledgement();
		state = State.REINFORCE;
	}

	private void deploy(Deploy deploy) {
		if (currentPlayer == deploy.getPlayerId()) {
			for (int[] inforce : deploy.getArmies()) {
				if (inforce.length < 2) {
					shutDown();
				}
				try {
					gameStateManager.reinforce(currentPlayer, inforce[0],
							inforce[1]);
				} catch (Exception e) {
					shutDown();
				}
			}
		} else {
			// TODO error handling
			shutDown();
		}

		view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
		// prepare for receiving acknowledgements
		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;
		sendAcknowledgement();
		state = State.ATTACK;
	}

	private void attack(Attack attack) {
		if (currentPlayer == attack.getPlayerId()) {
			try {
				Moves.attack(gameStateManager.model.getGameState()
						.getPlayerById(attack.getPlayerId()),
						gameStateManager.model.getGameState(), attack
								.getOriginTerritory(), attack
								.getDestinationTerritory(), attack
								.getNumberOfArmies());
			} catch (Exception e) {
				e.printStackTrace();
				shutDown();
			}
			cachedAction = attack;
			acknowledgementManager.expectAcknowledgement();
			collectingAcknowledgements = true;
			sendAcknowledgement();
			executeAllCurrentAcknowledgements();
			state = state.DEFEND;
			try {
				if (gameStateManager.model.getGameState().getBoard()
						.getTerritoriesById(attack.getDestinationTerritory())
						.getOwner().getId() == gameStateManager
						.getLocalPlayerId()) {
					localDefend(attack);
				}
			} catch (BoardException e) {
				shutDown();
			}
		} else {
			shutDown();
		}

	}

	private void localDefend(Attack attack) {
		String message = "You are under attack.";
		// make defence
		int maxArmies = 2;
		try {
			if (gameStateManager.model.getGameState().getBoard()
					.getTerritoriesById(attack.getDestinationTerritory())
					.getOccupyingArmies().size() < 2) {
				maxArmies = gameStateManager.model.getGameState().getBoard()
						.getTerritoriesById(attack.getDestinationTerritory())
						.getOccupyingArmies().size();
			}
		} catch (BoardException e) {
			shutDown();
		}

		DefendTerritory defendTerritory = (DefendTerritory) view
				.addUpdateAndWaitForResponse(new DefendTerritory(message,
						attack.getOriginTerritory(), attack
								.getDestinationTerritory(), maxArmies));
		responseGenerator.defendGenerator(defendTerritory.getArmiesUsed(),
				gameStateManager.getLocalPlayerId(),
				acknowledgementManager.getAcknowledgementId());

		// acknowledge defence
		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;
		executeAllCurrentAcknowledgements();

		roll(new Roll(attack.getNumberOfArmies()
				+ defendTerritory.getArmiesUsed(), 6,
				gameStateManager.getLocalPlayerId()));
		processDieRolls();
		int[] dieRolls = networkDieManager.getDieRolls();
		boolean captured = false;
		try {
			captured = gameStateManager.defend(attack.getPlayerId(),
					attack.getOriginTerritory(),
					attack.getDestinationTerritory(),
					attack.getNumberOfArmies(),
					defendTerritory.getArmiesUsed(), dieRolls);
		} catch (Exception e) {
			shutDown();
		}
		if (captured) {
			captured = true;
			executeActionsUntilIncludingType(new AttackCapture(0, 0, 0, 0, 0));
		}
	}

	private void defend(Defend defend) {
		if (cachedAction instanceof Attack) {
			Attack attack = (Attack) cachedAction;
			try {
				if (defend.getPlayerId() == gameStateManager.model
						.getGameState().getBoard()
						.getTerritoriesById(attack.getDestinationTerritory())
						.getOwner().getId()) {
					try {
						// acknowledge defend
						acknowledgementManager.expectAcknowledgement();
						collectingAcknowledgements = true;
						sendAcknowledgement();
						executeAllCurrentAcknowledgements();

						roll(new Roll(attack.getNumberOfArmies()
								+ defend.getNumberOfArmies(), 6,
								gameStateManager.getLocalPlayerId()));
						processDieRolls();
						int[] dieRolls = networkDieManager.getDieRolls();
						boolean captured = gameStateManager.defend(
								attack.getPlayerId(),
								attack.getOriginTerritory(),
								attack.getDestinationTerritory(),
								attack.getNumberOfArmies(),
								defend.getNumberOfArmies(), dieRolls);
						if (captured) {
							captured = true;
							if (attack.getPlayerId() == gameStateManager
									.getLocalPlayerId()) {
								localAllocateArmies(attack);
							} else {
								executeActionsUntilIncludingType(new AttackCapture(
										0, 0, 0, 0, 0));
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						shutDown();
					}
				}
			} catch (BoardException e) {
				e.printStackTrace();
			}
		} else {
			// problem
			shutDown();
		}
		view.addUpdate(new MapUpdate("", gameStateManager.serializeMap()));
		state = state.DEFEND;
	}

	private void localAllocateArmies(Attack attack) {
		String message = "Allocate armies";
		try {
			AllocateArmies allocateArmies = (AllocateArmies) view
					.addUpdateAndWaitForResponse(new AllocateArmies(message,
							attack.getOriginTerritory(), attack
									.getDestinationTerritory(),
							gameStateManager.model
									.getGameState()
									.getBoard()
									.getTerritoriesById(
											attack.getOriginTerritory())
									.getOccupyingArmies().size()));
			int[] captureArray = { attack.getOriginTerritory(),
					attack.getDestinationTerritory(),
					allocateArmies.getArmiesMoved() };
			acknowledgementManager.expectAcknowledgement();
			collectingAcknowledgements = true;
			responseGenerator.attackCaptureGenerator(captureArray,
					gameStateManager.getLocalPlayerId(),
					acknowledgementManager.getAcknowledgementId());
			executeAllCurrentAcknowledgements();
		} catch (BoardException e) {
			e.printStackTrace();
			shutDown();
		}
	}

	private void attackCapture(AttackCapture attackCapture) {
		if (cachedAction instanceof Attack) {
			Attack attack = (Attack) cachedAction;
			if (attack.getPlayerId() == attackCapture.getPlayerId()) {
				if (attackCapture.getNumberOfArmies() >= attack
						.getNumberOfArmies()) {
					try {
						gameStateManager.attackCapture(
								attackCapture.getPlayerId(),
								attack.getOriginTerritory(),
								attack.getDestinationTerritory(),
								attackCapture.getNumberOfArmies());
					} catch (Exception e) {
						e.printStackTrace();
						shutDown();
					}
					acknowledgementManager.expectAcknowledgement();
					collectingAcknowledgements = true;
					sendAcknowledgement();
					executeAllCurrentAcknowledgements();
				}
			}
		}
	}

	private void fortify(Fortify fortify) {
		if (fortify.getNumberOfArmies() != -1) {
			try {
				gameStateManager.fortify(fortify.getPlayerId(),
						fortify.getOriginTerritory(),
						fortify.getDestinationTerritory(),
						fortify.getNumberOfArmies());
			} catch (Exception e) {
				shutDown();
			}
		}

		acknowledgementManager.expectAcknowledgement();
		collectingAcknowledgements = true;
		sendAcknowledgement();
		executeAllCurrentAcknowledgements();

		drawCards();
		state = State.CARDS;
	}

	private void drawCards() {
		if (capturedTerritory == true) {
			CardMethods.collectCard(gameStateManager.model.getGameState()
					.getPlayerById(currentPlayer), gameStateManager.model);
			capturedTerritory = false;
		}
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

	public int calculateNumberOfArmies(Player player, GameState gameState) {
		int numberOfArmies = 0;

		if (player.getTerritories().size() < 9) {
			numberOfArmies += 3;
		} else {
			numberOfArmies += Math.floor(player.getTerritories().size() / 3);
		}

		numberOfArmies += gameState.getBoard().getContinentBonus(player);
		return numberOfArmies;
	}

	private void sendAcknowledgement() {
		responseGenerator.ackGenerator(
				acknowledgementManager.getAcknowledgementId(),
				gameStateManager.getLocalPlayerId());
		acknowledgementManager.addAcknowledgement(
				gameStateManager.getLocalPlayerId(),
				acknowledgementManager.getAcknowledgementId());
	}

	/**
	 * Will adapt the local game state according to the given action.
	 * <p/>
	 * For use by networking, ui and ai to propagate information to the
	 * controller.
	 *
	 * @param action
	 *            - influences that affect the game
	 */
	public synchronized void handleAction(Action action) {
		actions.add(action);
	}

	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	private void shutDown() {
		// TODO close resources etc
		System.exit(0);
	}
}
