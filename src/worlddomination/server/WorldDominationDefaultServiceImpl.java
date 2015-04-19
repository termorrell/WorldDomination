package worlddomination.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import worlddomination.client.WorldDominationDefaultService;
import worlddomination.server.actions.AcceptJoinGame;
import worlddomination.server.actions.Acknowledgement;
import worlddomination.server.actions.Action;
import worlddomination.server.actions.Attack;
import worlddomination.server.actions.AttackCapture;
import worlddomination.server.actions.Defend;
import worlddomination.server.actions.Deploy;
import worlddomination.server.actions.Fortify;
import worlddomination.server.actions.InitialiseGame;
import worlddomination.server.actions.Ping;
import worlddomination.server.actions.PlayCards;
import worlddomination.server.actions.PlayersJoined;
import worlddomination.server.actions.Ready;
import worlddomination.server.actions.RollHash;
import worlddomination.server.actions.RollNumber;
import worlddomination.server.actions.Setup;
import worlddomination.server.controller.ClientController;
import worlddomination.server.controller.ControllerManager;
import worlddomination.server.view.ControllerApiInterface;
import worlddomination.shared.updates.Update;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WorldDominationDefaultServiceImpl extends RemoteServiceServlet implements
		WorldDominationDefaultService, ControllerApiInterface {

	private static LinkedList<Action> networkActions = new LinkedList<>();
	private Queue<Update> updates = new LinkedList<>();
	private Update response = null;
	private boolean shouldLeaveGame;

	public void initialiseController(String ipAddress, int port) {

		System.out.println(ipAddress + " - " + port);

		initNetworkActions();
		ClientController controller = ControllerManager.sharedManager(this);

		Thread thread = new Thread(controller);
		
		for (Action action : networkActions) {
			controller.handleAction(action);
		}

		thread.start();
	}

	public void initialiseControllerAsHost(boolean shouldJoin) {

		System.out.println(shouldJoin);

		initNetworkActions();
		ClientController controller = ControllerManager.sharedManager(this);

		Thread thread = new Thread(controller);
		
		for (Action action : networkActions) {
			controller.handleAction(action);
		}

		thread.start();
	}

	private synchronized boolean ready() {
		if (response != null) {
			return true;
		}
		return false;
	}

	public void addUpdate(Update update) {
		updates.add(update);
	}

	public Update addUpdateAndWaitForResponse(Update update) {
		updates.add(update);
		while (!ready()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO
				e.printStackTrace();
			}
		}
		Update currentResponse = response;
		response = null;
		return currentResponse;
	}

	@Override
	public synchronized Update getNextUpdate() {
		if (updates.isEmpty()) {
			return null;
		}
		return updates.poll();
	}

	//
	//
	// /**
	// *
	// */
	// public Update getNextUpdate() {
	//
	// numberOfHits ++;
	// System.out.println(numberOfHits);
	//
	// if (numberOfHits == 1) {
	// LocalPlayerName localPlayerName = new LocalPlayerName();
	// return localPlayerName;
	// } else if (numberOfHits < 20) {
	//
	// int numberOfPlayers = (int)(Math.random() * (6 - 1) + 1);
	// Lobby lobby = new Lobby();
	// players = new ArrayList<Player> ();
	// for (int i = 0; i < numberOfPlayers; i++) {
	//
	// Player newPlayer = new Player(i, names[i], "abcdefghijklmnop");
	// lobby.addPlayerToListOfPlayers(newPlayer);
	// players.add(newPlayer);
	// }
	// return lobby;
	// } else if (numberOfHits < 21) {
	// PingReady ready = new PingReady();
	// return ready;
	// } else if (numberOfHits < 22) {
	// CurrentPlayer player = new CurrentPlayer("Thomas to play", 0, true,
	// false);
	// return player;
	// } else if (numberOfHits < 23) {
	// CurrentPlayer player = new CurrentPlayer("Caroline to play", 1, false,
	// false);
	// return player;
	// } else if (numberOfHits < 24) {
	// ClaimTerritory claimTerritory = new ClaimTerritory("Claim a territory");
	// return claimTerritory;
	// } else if (numberOfHits < 25) {
	// DistributeArmy distributeArmy = new DistributeArmy("Distribute an army");
	// return distributeArmy;
	// } else if (numberOfHits == 30) {
	// MapUpdate mapUpdate = new MapUpdate(new String(),
	// "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":null,\"armies\":null},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":null,\"armies\":null},{\"territoryID\":21,\"playerID\":null,\"armies\":null},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":null,\"armies\":null},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":null,\"armies\":null},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
	// return mapUpdate;
	// } else if (numberOfHits == 34) {
	// LogUpdate logUpdate = new LogUpdate("Test log update");
	// return logUpdate;
	// } else if (numberOfHits == 35) {
	// MapUpdate mapUpdate = new MapUpdate(new String(),
	// "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":null,\"armies\":null},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":null,\"armies\":null},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":null,\"armies\":null},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
	// return mapUpdate;
	// } else if (numberOfHits == 40) {
	// MapUpdate mapUpdate = new MapUpdate(new String(),
	// "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":1},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":1,\"armies\":2},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":1,\"armies\":3},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
	// return mapUpdate;
	// } else if (numberOfHits == 44) {
	// LogUpdate logUpdate = new LogUpdate("Another Test log update");
	// return logUpdate;
	// } else if (numberOfHits == 45) {
	// MapUpdate mapUpdate = new MapUpdate(new String(),
	// "{\"Territories\":[{\"territoryID\":0,\"playerID\":0,\"armies\":1},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":2,\"armies\":3},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":2,\"armies\":5},{\"territoryID\":8,\"playerID\":0,\"armies\":1},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":0,\"armies\":1},{\"territoryID\":12,\"playerID\":2,\"armies\":3},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":2,\"armies\":4},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":2,\"armies\":7},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":1},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":1,\"armies\":2},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":1,\"armies\":3},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":0,\"armies\":2},{\"territoryID\":37,\"playerID\":1,\"armies\":3},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
	// return mapUpdate;
	// } else if (numberOfHits == 50) {
	// MapUpdate mapUpdate = new MapUpdate(new String(),
	// "{\"Territories\":[{\"territoryID\":0,\"playerID\":1,\"armies\":2},{\"territoryID\":1,\"playerID\":2,\"armies\":5},{\"territoryID\":2,\"playerID\":1,\"armies\":8},{\"territoryID\":3,\"playerID\":1,\"armies\":1},{\"territoryID\":4,\"playerID\":2,\"armies\":1},{\"territoryID\":5,\"playerID\":2,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":1,\"armies\":1},{\"territoryID\":8,\"playerID\":2,\"armies\":1},{\"territoryID\":9,\"playerID\":0,\"armies\":1},{\"territoryID\":10,\"playerID\":2,\"armies\":1},{\"territoryID\":11,\"playerID\":2,\"armies\":5},{\"territoryID\":12,\"playerID\":0,\"armies\":8},{\"territoryID\":13,\"playerID\":2,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":2,\"armies\":1},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":0,\"armies\":1},{\"territoryID\":18,\"playerID\":2,\"armies\":1},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":5},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":2,\"armies\":1},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":0,\"armies\":1},{\"territoryID\":28,\"playerID\":2,\"armies\":1},{\"territoryID\":29,\"playerID\":2,\"armies\":1},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":2,\"armies\":8},{\"territoryID\":33,\"playerID\":0,\"armies\":1},{\"territoryID\":34,\"playerID\":0,\"armies\":1},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":0,\"armies\":1},{\"territoryID\":37,\"playerID\":0,\"armies\":1},{\"territoryID\":38,\"playerID\":0,\"armies\":1},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
	// return mapUpdate;
	// } else if (numberOfHits == 51) {
	// Reinforce reinforce = new Reinforce("Select territories to reinforce",
	// 3);
	// return reinforce;
	// } else if (numberOfHits == 52) {
	// MakeTurn makeTurn = new MakeTurn("Make a turn");
	// makeTurn.setTimeOut("");
	// return makeTurn;
	// } else if (numberOfHits == 53) {
	// AllocateCard allocateCard = new AllocateCard("You collected a card", 11,
	// "Infantry");
	// return allocateCard;
	// } else if (numberOfHits == 54) {
	// MakeTurn makeTurn = new MakeTurn("Make a turn");
	// makeTurn.setTimeOut("");
	// return makeTurn;
	// } else if (numberOfHits == 55) {
	// AllocateCard allocateCard = new AllocateCard("You collected a card", 30,
	// "Cavalry");
	// return allocateCard;
	// } else if (numberOfHits == 56) {
	// MakeTurn makeTurn = new MakeTurn("Make a turn");
	// makeTurn.setTimeOut("");
	// return makeTurn;
	// } else if (numberOfHits == 57) {
	// AllocateCard allocateCard = new AllocateCard("You collected a card", 23,
	// "Artillery");
	// return allocateCard;
	// } else if (numberOfHits == 58) {
	// AllocateArmies allocateArmies = new AllocateArmies("Allocate armies", 23,
	// 34, 3);
	// return allocateArmies;
	// } else if (numberOfHits == 59) {
	// Player[] playerArray = new Player[players.size()];
	// players.toArray(playerArray);
	// Winner winner = new Winner("GAME OVER!", true, playerArray);
	// return winner;
	// } else {
	// return null;
	// }
	// }

	/**
	 * 
	 */
	public void sendUpdateResponse(Update response) {
		if (response != null) {
			this.response = response;
		}
	}

	/**
	 * 
	 */
	public void leaveGame() {

//		Update leaveGame = new Update();
//		leaveGame.setToTerminate();
		
	}

	private static void initNetworkActions() {
        int hostId = -1;
        int firstId = 1;
        int localId = 2;
        int thirdId = 3;

        // accept join game
        networkActions.add(new AcceptJoinGame(localId, 1000, 10000));

        // player's joined
        Map<Integer, String[]> map = new HashMap<>();
        String[] p1 = {"Alice", "Alice's public key."};
        String[] p2 = {"Bob", "Bob's pulbic key."};
        map.put(firstId, p1);
        map.put(localId, p2);
        networkActions.add(new PlayersJoined(map));

        // player's joined
        Map<Integer, String[]> map1 = new HashMap<>();
        map1.put(firstId, p1);
        map1.put(localId, p2);
        String[] p3 = {"Charly", "Charly's public key."};
        map1.put(thirdId, p3);
        networkActions.add(new PlayersJoined(map1));

        // server ping
        networkActions.add(new Ping(3, hostId));

        // client pings
        networkActions.add(new Ping(3, firstId));
        networkActions.add(new Ping(3, thirdId));

        // ready
        networkActions.add(new Ready(-1, 0));

        // acknowledge ready
        networkActions.add(new Acknowledgement(0, firstId));
        networkActions.add(new Acknowledgement(0, thirdId));

        // initialise game
        networkActions.add(new InitialiseGame(1, new String[0]));

        // find first player
        // TODO hashes
        networkActions.add(new RollHash(firstId, "hash1"));
        networkActions.add(new RollHash(thirdId, "hash3"));

        // numbers
        networkActions.add(new RollNumber(firstId, "number1"));
        networkActions.add(new RollNumber(thirdId, "number3"));

        // card shuffling

        // TODO hashes
        networkActions.add(new RollHash(firstId, "hashshuffle1"));
        networkActions.add(new RollHash(thirdId, "hashshuffle3"));

        // numbers
        networkActions.add(new RollNumber(firstId, "numbershuffle1"));
        networkActions.add(new RollNumber(thirdId, "numbershuffle3"));

        // TODO locally calculate the number of cards

        // setup
        // TODO check parameters and fill out till completeness
        networkActions.add(new Setup(thirdId, 10, 1));
        networkActions.add(new Acknowledgement(1, firstId));

        networkActions.add(new Setup(firstId, 2, 2));
        networkActions.add(new Acknowledgement(2, thirdId));

        networkActions.add(new Acknowledgement(3, thirdId));
        networkActions.add(new Acknowledgement(3, firstId));

        networkActions.add(new Setup(thirdId, 32, 4));
        networkActions.add(new Acknowledgement(4, firstId));

        networkActions.add(new Setup(firstId, 0, 5));
        networkActions.add(new Acknowledgement(5, thirdId));

        networkActions.add(new Acknowledgement(6, thirdId));
        networkActions.add(new Acknowledgement(6, firstId));

        networkActions.add(new Setup(thirdId, 15, 7));
        networkActions.add(new Acknowledgement(7, firstId));

        networkActions.add(new Setup(firstId, 8, 8));
        networkActions.add(new Acknowledgement(8, thirdId));

        networkActions.add(new Acknowledgement(9, thirdId));
        networkActions.add(new Acknowledgement(9, firstId));

        networkActions.add(new Setup(thirdId, 5, 10));
        networkActions.add(new Acknowledgement(10, firstId));

        networkActions.add(new Setup(firstId, 19, 11));
        networkActions.add(new Acknowledgement(11, firstId));

        networkActions.add(new Acknowledgement(12, thirdId));
        networkActions.add(new Acknowledgement(12, firstId));

        networkActions.add(new Setup(thirdId, 7, 13));
        networkActions.add(new Acknowledgement(13, thirdId));

        networkActions.add(new Setup(firstId, 12, 10));
        networkActions.add(new Acknowledgement(14, firstId));

        networkActions.add(new Acknowledgement(15, thirdId));
        networkActions.add(new Acknowledgement(15, firstId));

        networkActions.add(new Setup(thirdId, 3, 16));
        networkActions.add(new Acknowledgement(16, thirdId));

        networkActions.add(new Setup(firstId, 6, 17));
        networkActions.add(new Acknowledgement(17, firstId));

        networkActions.add(new Acknowledgement(18, thirdId));
        networkActions.add(new Acknowledgement(18, firstId));

        networkActions.add(new Setup(thirdId, 27, 19));
        networkActions.add(new Acknowledgement(19, thirdId));

        networkActions.add(new Setup(firstId, 23, 20));
        networkActions.add(new Acknowledgement(20, firstId));

        networkActions.add(new Acknowledgement(21, thirdId));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(thirdId, 22, 22));
        networkActions.add(new Acknowledgement(22, firstId));

        networkActions.add(new Setup(firstId, 33, 23));
        networkActions.add(new Acknowledgement(23, thirdId));

        networkActions.add(new Acknowledgement(24, thirdId));
        networkActions.add(new Acknowledgement(24, firstId));

        networkActions.add(new Setup(thirdId, 18, 25));
        networkActions.add(new Acknowledgement(25, firstId));

        networkActions.add(new Setup(firstId, 4, 26));
        networkActions.add(new Acknowledgement(26, thirdId));

        networkActions.add(new Acknowledgement(27, thirdId));
        networkActions.add(new Acknowledgement(27, firstId));

        networkActions.add(new Setup(thirdId, 20, 28));
        networkActions.add(new Acknowledgement(28, firstId));

        networkActions.add(new Setup(firstId, 24, 29));
        networkActions.add(new Acknowledgement(29, thirdId));

        networkActions.add(new Acknowledgement(30, thirdId));
        networkActions.add(new Acknowledgement(30, firstId));

        networkActions.add(new Setup(thirdId, 25, 31));
        networkActions.add(new Acknowledgement(31, firstId));

        networkActions.add(new Setup(firstId, 14, 32));
        networkActions.add(new Acknowledgement(32, thirdId));

        networkActions.add(new Acknowledgement(33, thirdId));
        networkActions.add(new Acknowledgement(33, firstId));

        networkActions.add(new Setup(thirdId, 16, 34));
        networkActions.add(new Acknowledgement(34, firstId));

        networkActions.add(new Setup(firstId, 31, 35));
        networkActions.add(new Acknowledgement(35, thirdId));

        networkActions.add(new Acknowledgement(36, thirdId));
        networkActions.add(new Acknowledgement(36, firstId));

        networkActions.add(new Setup(thirdId, 30, 37));
        networkActions.add(new Acknowledgement(37, firstId));

        networkActions.add(new Setup(firstId, 28, 38));
        networkActions.add(new Acknowledgement(38, thirdId));

        networkActions.add(new Acknowledgement(39, thirdId));
        networkActions.add(new Acknowledgement(39, firstId));

        networkActions.add(new Setup(thirdId, 1, 40));
        networkActions.add(new Acknowledgement(40, firstId));

        networkActions.add(new Setup(firstId, 37, 41));
        networkActions.add(new Acknowledgement(41, thirdId));

        networkActions.add(new Acknowledgement(42, thirdId));
        networkActions.add(new Acknowledgement(42, firstId));

        // Ditributing remaining armies

        networkActions.add(new Setup(thirdId, 32, 43));
        networkActions.add(new Acknowledgement(43, firstId));

        networkActions.add(new Setup(firstId, 23, 44));
        networkActions.add(new Acknowledgement(44, thirdId));

        networkActions.add(new Acknowledgement(45, thirdId));
        networkActions.add(new Acknowledgement(45, firstId));

        networkActions.add(new Setup(thirdId, 16, 46));
        networkActions.add(new Acknowledgement(46, firstId));

        networkActions.add(new Setup(firstId, 31, 47));
        networkActions.add(new Acknowledgement(47, thirdId));

        networkActions.add(new Acknowledgement(48, thirdId));
        networkActions.add(new Acknowledgement(48, firstId));

        networkActions.add(new Setup(thirdId, 27, 49));
        networkActions.add(new Acknowledgement(49, firstId));

        networkActions.add(new Setup(firstId, 14, 50));
        networkActions.add(new Acknowledgement(50, thirdId));

        networkActions.add(new Acknowledgement(51, thirdId));
        networkActions.add(new Acknowledgement(51, firstId));

        networkActions.add(new Setup(thirdId, 32, 52));
        networkActions.add(new Acknowledgement(52, firstId));

        networkActions.add(new Setup(firstId, 31, 53));
        networkActions.add(new Acknowledgement(53, thirdId));

        networkActions.add(new Acknowledgement(54, thirdId));
        networkActions.add(new Acknowledgement(54, firstId));

        networkActions.add(new Setup(thirdId, 5, 55));
        networkActions.add(new Acknowledgement(55, firstId));

        networkActions.add(new Setup(firstId, 0, 56));
        networkActions.add(new Acknowledgement(56, thirdId));

        networkActions.add(new Acknowledgement(57, thirdId));
        networkActions.add(new Acknowledgement(57, firstId));

        networkActions.add(new Setup(thirdId, 10, 58));
        networkActions.add(new Acknowledgement(58, firstId));

        networkActions.add(new Setup(firstId, 4, 59));
        networkActions.add(new Acknowledgement(59, thirdId));

        networkActions.add(new Acknowledgement(60, thirdId));
        networkActions.add(new Acknowledgement(60, firstId));

        networkActions.add(new Setup(thirdId, 15, 61));
        networkActions.add(new Acknowledgement(61, firstId));

        networkActions.add(new Setup(firstId, 19, 62));
        networkActions.add(new Acknowledgement(62, thirdId));

        networkActions.add(new Acknowledgement(63, thirdId));
        networkActions.add(new Acknowledgement(63, firstId));

        networkActions.add(new Setup(thirdId, 10, 64));
        networkActions.add(new Acknowledgement(64, firstId));

        networkActions.add(new Setup(firstId, 0, 65));
        networkActions.add(new Acknowledgement(65, thirdId));

        networkActions.add(new Acknowledgement(66, thirdId));
        networkActions.add(new Acknowledgement(66, firstId));

        networkActions.add(new Setup(thirdId, 30, 67));
        networkActions.add(new Acknowledgement(67, firstId));

        networkActions.add(new Setup(firstId, 23, 68));
        networkActions.add(new Acknowledgement(68, thirdId));

        networkActions.add(new Acknowledgement(69, thirdId));
        networkActions.add(new Acknowledgement(69, firstId));

        networkActions.add(new Setup(thirdId, 25, 70));
        networkActions.add(new Acknowledgement(70, firstId));

        networkActions.add(new Setup(firstId, 28, 71));
        networkActions.add(new Acknowledgement(71, thirdId));

        networkActions.add(new Acknowledgement(72, thirdId));
        networkActions.add(new Acknowledgement(72, firstId));

        networkActions.add(new Setup(thirdId, 16, 73));
        networkActions.add(new Acknowledgement(73, firstId));

        networkActions.add(new Setup(firstId, 12, 74));
        networkActions.add(new Acknowledgement(74, thirdId));

        networkActions.add(new Acknowledgement(75, thirdId));
        networkActions.add(new Acknowledgement(75, firstId));

        networkActions.add(new Setup(thirdId, 22, 76));
        networkActions.add(new Acknowledgement(76, firstId));

        networkActions.add(new Setup(firstId, 12, 77));
        networkActions.add(new Acknowledgement(77, thirdId));

        networkActions.add(new Acknowledgement(78, thirdId));
        networkActions.add(new Acknowledgement(78, firstId));

        networkActions.add(new Setup(thirdId, 32, 79));
        networkActions.add(new Acknowledgement(79, firstId));

        networkActions.add(new Setup(firstId, 19, 80));
        networkActions.add(new Acknowledgement(80, thirdId));

        networkActions.add(new Acknowledgement(81, thirdId));
        networkActions.add(new Acknowledgement(81, firstId));

        networkActions.add(new Setup(thirdId, 7, 82));
        networkActions.add(new Acknowledgement(82, firstId));

        networkActions.add(new Setup(firstId, 12, 83));
        networkActions.add(new Acknowledgement(83, thirdId));

        networkActions.add(new Acknowledgement(84, thirdId));
        networkActions.add(new Acknowledgement(84, firstId));

        networkActions.add(new Setup(thirdId, 25, 85));
        networkActions.add(new Acknowledgement(85, firstId));

        networkActions.add(new Setup(firstId, 31, 86));
        networkActions.add(new Acknowledgement(86, thirdId));

        networkActions.add(new Acknowledgement(87, thirdId));
        networkActions.add(new Acknowledgement(87, firstId));

        networkActions.add(new Setup(thirdId, 25, 88));
        networkActions.add(new Acknowledgement(88, firstId));

        networkActions.add(new Setup(firstId, 14, 89));
        networkActions.add(new Acknowledgement(89, thirdId));

        networkActions.add(new Acknowledgement(90, thirdId));
        networkActions.add(new Acknowledgement(90, firstId));

        networkActions.add(new Setup(thirdId, 27, 91));
        networkActions.add(new Acknowledgement(91, firstId));

        networkActions.add(new Setup(firstId, 8, 92));
        networkActions.add(new Acknowledgement(92, thirdId));

        networkActions.add(new Acknowledgement(93, thirdId));
        networkActions.add(new Acknowledgement(93, firstId));

        networkActions.add(new Setup(thirdId, 20, 94));
        networkActions.add(new Acknowledgement(94, firstId));

        networkActions.add(new Setup(firstId, 12, 95));
        networkActions.add(new Acknowledgement(95, thirdId));

        networkActions.add(new Acknowledgement(96, thirdId));
        networkActions.add(new Acknowledgement(96, firstId));

        networkActions.add(new Setup(thirdId, 27, 97));
        networkActions.add(new Acknowledgement(97, firstId));

        networkActions.add(new Setup(firstId, 31, 98));
        networkActions.add(new Acknowledgement(98, thirdId));

        networkActions.add(new Acknowledgement(99, thirdId));
        networkActions.add(new Acknowledgement(99, firstId));

        networkActions.add(new Setup(thirdId, 3, 100));
        networkActions.add(new Acknowledgement(100, firstId));

        networkActions.add(new Setup(firstId, 14, 101));
        networkActions.add(new Acknowledgement(101, thirdId));

        networkActions.add(new Acknowledgement(102, thirdId));
        networkActions.add(new Acknowledgement(102, firstId));

        networkActions.add(new Setup(thirdId, 30, 103));
        networkActions.add(new Acknowledgement(103, firstId));

        networkActions.add(new Setup(firstId, 8, 104));
        networkActions.add(new Acknowledgement(104, thirdId));

        networkActions.add(new Acknowledgement(105, thirdId));
        networkActions.add(new Acknowledgement(105, firstId));

        // Turns

        // third player
        networkActions.add(new PlayCards(0, new int[0][0], 0, thirdId, 106));
        networkActions.add(new Acknowledgement(106, firstId));

        int[][] deploy1 = {{16, 2},{25,1}};
        networkActions.add(new Deploy(deploy1, thirdId, 107));
        networkActions.add(new Acknowledgement(107, firstId));

        networkActions.add(new Attack(25,23,1,thirdId,108));
        networkActions.add(new Acknowledgement(108, firstId));

        networkActions.add(new Defend(2, firstId, 109));
        networkActions.add(new Acknowledgement(109, thirdId));

        networkActions.add(new RollHash(firstId, "hashshuffle1"));
        networkActions.add(new RollHash(thirdId, "hashshuffle3"));

        networkActions.add(new RollNumber(firstId, "numbershuffle1"));
        networkActions.add(new RollNumber(thirdId, "numbershuffle3"));

        networkActions.add(new Attack(16,13,3,thirdId,110));
        networkActions.add(new Acknowledgement(110, firstId));

        networkActions.add(new Acknowledgement(111, firstId));
        networkActions.add(new Acknowledgement(111, thirdId));

        networkActions.add(new RollHash(firstId, "hashshuffle1"));
        networkActions.add(new RollHash(thirdId, "hashshuffle3"));

        networkActions.add(new RollNumber(firstId, "numbershuffle1"));
        networkActions.add(new RollNumber(thirdId, "numbershuffle3"));

        networkActions.add(new AttackCapture(16,13,3,thirdId, 112 ));
        networkActions.add(new Acknowledgement(112, firstId));

        networkActions.add(new Fortify(30, 27,1,thirdId, 113));
        networkActions.add(new Acknowledgement(113, firstId));

        // first player
        networkActions.add(new PlayCards(0, new int[0][0], 0, firstId, 114));
        networkActions.add(new Acknowledgement(114, thirdId));

        int[][] deploy2 = {{12, 3}};
        networkActions.add(new Deploy(deploy2, firstId, 115));
        networkActions.add(new Acknowledgement(115, thirdId));

        networkActions.add(new Fortify(-1, -1, -1,firstId, 116));
        networkActions.add(new Acknowledgement(116, thirdId));

        // local Player
        networkActions.add(new Acknowledgement(117, thirdId));
        networkActions.add(new Acknowledgement(117, firstId));

        networkActions.add(new Acknowledgement(118, thirdId));
        networkActions.add(new Acknowledgement(118, firstId));

        networkActions.add(new Acknowledgement(119, thirdId));
        networkActions.add(new Acknowledgement(119, firstId));

        networkActions.add(new Acknowledgement(105, thirdId));
        networkActions.add(new Acknowledgement(105, firstId));
    }

}
