package tests;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import view.ControllerApiInterface;
import view.INetworkView;
import view.MockControllerApiImpl;
import view.MockNetworkView;
import controller.ClientController;
import actions.*;

public class ClientControllerTest {
	static LinkedList<Action> networkActions = new LinkedList<>();

	public static void main(String[] args) {
		initNetworkActions();
		ControllerApiInterface view = new MockControllerApiImpl();
		ClientController controller = new ClientController(view);

		for (Action action : networkActions) {
			controller.handleAction(action);
		}

		controller.run();
	}

	// nonplaying host
	private static void initNetworkActions() {
		int hostId = -1;
		int firstId = 1;
		int localId = 2;
		int thirdId = 3;

		// accept join game
		networkActions.add(new AcceptJoinGame(localId, 1000, 10000));

		// player's joined
		Map<Integer, String[]> map = new HashMap<>();
		String[] p1 = { "Alice", "Alice's public key." };
		String[] p2 = { "Bob", "Bob's pulbic key." };
		map.put(firstId, p1);
		map.put(localId, p2);
		networkActions.add(new PlayersJoined(map));

		// player's joined
		Map<Integer, String[]> map1 = new HashMap<>();
		map1.put(firstId, p1);
		map1.put(localId, p2);
		String[] p3 = { "Charly", "Charly's public key." };
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

        networkActions.add(new Setup(thirdId, 32, 3));
        networkActions.add(new Acknowledgement(3, firstId));

        networkActions.add(new Setup(firstId, 0, 4));
        networkActions.add(new Acknowledgement(4, thirdId));

        networkActions.add(new Setup(thirdId, 15, 5));
        networkActions.add(new Acknowledgement(5, firstId));

        networkActions.add(new Setup(firstId, 8, 6));
        networkActions.add(new Acknowledgement(6, thirdId));

        networkActions.add(new Setup(thirdId, 5, 7));
        networkActions.add(new Acknowledgement(7, firstId));

        networkActions.add(new Setup(thirdId, 19, 8));
        networkActions.add(new Acknowledgement(8, firstId));

        networkActions.add(new Setup(firstId, 7, 9));
        networkActions.add(new Acknowledgement(9, thirdId));

        networkActions.add(new Setup(thirdId, 12, 10));
        networkActions.add(new Acknowledgement(10, firstId));

        networkActions.add(new Setup(firstId, 3, 11));
        networkActions.add(new Acknowledgement(11, thirdId));

        networkActions.add(new Setup(thirdId, 6, 12));
        networkActions.add(new Acknowledgement(12, firstId));

        networkActions.add(new Setup(firstId, 27, 13));
        networkActions.add(new Acknowledgement(13, thirdId));

        networkActions.add(new Setup(thirdId, 23, 14));
        networkActions.add(new Acknowledgement(14, firstId));

        networkActions.add(new Setup(thirdId, 22, 15));
        networkActions.add(new Acknowledgement(15, firstId));

        networkActions.add(new Setup(firstId, 33, 16));
        networkActions.add(new Acknowledgement(16, thirdId));

        networkActions.add(new Setup(thirdId, 18, 17));
        networkActions.add(new Acknowledgement(17, firstId));

        networkActions.add(new Setup(firstId, 4, 18));
        networkActions.add(new Acknowledgement(18, thirdId));

        networkActions.add(new Setup(thirdId, 20, 19));
        networkActions.add(new Acknowledgement(19, firstId));

        networkActions.add(new Setup(firstId, 24, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 25, 21));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(firstId, 14, 22));
        networkActions.add(new Acknowledgement(22, thirdId));

        networkActions.add(new Setup(thirdId, 16, 23));
        networkActions.add(new Acknowledgement(23, firstId));

        networkActions.add(new Setup(firstId, 31, 24));
        networkActions.add(new Acknowledgement(24, thirdId));

        networkActions.add(new Setup(thirdId, 30, 25));
        networkActions.add(new Acknowledgement(25, firstId));

        networkActions.add(new Setup(firstId, 28, 26));
        networkActions.add(new Acknowledgement(26, thirdId));

        networkActions.add(new Setup(thirdId, 1, 27));
        networkActions.add(new Acknowledgement(27, firstId));

        networkActions.add(new Setup(firstId, 37, 28));
        networkActions.add(new Acknowledgement(28, thirdId));

        // Ditributing remaining armies

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(firstId, 10, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(firstId, 10, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(firstId, 10, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));

        networkActions.add(new Setup(firstId, 10, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));


        networkActions.add(new Setup(firstId, 10, 20));
        networkActions.add(new Acknowledgement(20, thirdId));

        networkActions.add(new Setup(thirdId, 10, 21));
        networkActions.add(new Acknowledgement(21, firstId));


		// turn
		// TODO playerid, ack id
		networkActions.add(new PlayCards(0, new int[0], 0, firstId, 60));
	}
}
