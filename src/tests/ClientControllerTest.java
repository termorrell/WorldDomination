package tests;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import view.INetworkView;
import view.MockNetworkView;
import controller.ClientController;
import actions.*;

public class ClientControllerTest {
	static LinkedList<Action> networkActions = new LinkedList<> ();
	
	public static void main(String[] args) {
		initNetworkActions();
		INetworkView view = new MockNetworkView();
		ClientController controller = new ClientController(view);
		controller.run();
	}
	
	//  nonplaying host
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
		String[] p3 = {"Charly", "Charly's public key."};
		map.put(thirdId, p3);
		networkActions.add(new PlayersJoined(map));
		
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
		
		// die roll to determine first player
		networkActions.add(new Roll(1, 3, hostId));
		
		// TODO hashes
		networkActions.add(new RollHash(firstId, "hash1"));
		networkActions.add(new RollHash(thirdId, "hash3"));
		
		// numbers
		networkActions.add(new RollNumber(firstId, "number1"));
		networkActions.add(new RollNumber(thirdId, "number3"));
		
		// card shuffling
		
		// TODO locally calculate the number of cards
		
		// setup
		// TODO check parameters and fill out till completeness
		networkActions.add(new Setup(firstId, 1, 1));
		networkActions.add(new Acknowledgement(1, 3));
		
		// turn
		// TODO playerid, ack id
		networkActions.add(new PlayCards(0, new int[0], 0, firstId, 60));
	}
}
