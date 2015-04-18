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

        networkActions.add(new Setup(firstId, 18, 92));
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

		// turn
		// TODO playerid, ack id
		networkActions.add(new PlayCards(0, new int[0], 0, firstId, 60));
	}
}
