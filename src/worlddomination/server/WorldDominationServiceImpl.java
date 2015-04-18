package worlddomination.server;

import java.util.ArrayList;

import worlddomination.client.*;
import worlddomination.shared.updates.AllocateArmies;
import worlddomination.shared.updates.AllocateCard;
import worlddomination.shared.updates.ClaimTerritory;
import worlddomination.shared.updates.CurrentPlayer;
import worlddomination.shared.updates.DistributeArmy;
import worlddomination.shared.updates.Lobby;
import worlddomination.shared.updates.LocalPlayerName;
import worlddomination.shared.updates.LogUpdate;
import worlddomination.shared.updates.MakeTurn;
import worlddomination.shared.updates.MapUpdate;
import worlddomination.shared.updates.Player;
import worlddomination.shared.updates.Ready;
import worlddomination.shared.updates.Reinforce;
import worlddomination.shared.updates.Update;
import worlddomination.shared.updates.Winner;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WorldDominationServiceImpl extends RemoteServiceServlet implements
		WorldDominationService {
	
	private String[] names = {"Thomas", "Caroline", "Maria", "Jon", "Zhaniel"};
	private ArrayList<Player> players = new ArrayList<Player> ();
	private static int numberOfHits = 0;

	public void initialiseController(String ipAddress, int port) {
		
		System.out.println(ipAddress + " - " + port);
	}

	public void initialiseControllerAsHost(boolean shouldJoin) {

		System.out.println(shouldJoin);
	}

	/**
	 * 
	 */
	public Update getNextUpdate() {
		
		numberOfHits ++;
		System.out.println(numberOfHits);
		
		if (numberOfHits == 1) { 
			LocalPlayerName localPlayerName = new LocalPlayerName();
			return localPlayerName;
		} else if (numberOfHits < 20) {

			int numberOfPlayers = (int)(Math.random() * (6 - 1) + 1);
			Lobby lobby = new Lobby();
			players = new ArrayList<Player> ();
			for (int i = 0; i < numberOfPlayers; i++) {

				Player newPlayer = new Player(i, names[i], "abcdefghijklmnop");
				lobby.addPlayerToListOfPlayers(newPlayer);
				players.add(newPlayer);
			}
			return lobby;
		} else if (numberOfHits < 21) {
			Ready ready = new Ready();
			return ready;
		} else if (numberOfHits < 22) {
			CurrentPlayer player = new CurrentPlayer("Thomas to play", 0, true, false);
			return player;
		} else if (numberOfHits < 23) {
			CurrentPlayer player = new CurrentPlayer("Caroline to play", 1, false, false);
			return player;
		} else if (numberOfHits < 24) {
			ClaimTerritory claimTerritory = new ClaimTerritory("Claim a territory");
			return claimTerritory;
		} else if (numberOfHits < 25) {
			DistributeArmy distributeArmy = new DistributeArmy("Distribute an army");
			return distributeArmy;
		} else if (numberOfHits == 30) {
			MapUpdate mapUpdate = new MapUpdate(new String(), "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":null,\"armies\":null},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":null,\"armies\":null},{\"territoryID\":21,\"playerID\":null,\"armies\":null},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":null,\"armies\":null},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":null,\"armies\":null},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
			return mapUpdate;
		} else if (numberOfHits == 34) {
			LogUpdate logUpdate = new LogUpdate("Test log update");
			return logUpdate;
		} else if (numberOfHits == 35) {
			MapUpdate mapUpdate = new MapUpdate(new String(), "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":null,\"armies\":null},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":null,\"armies\":null},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":null,\"armies\":null},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
			return mapUpdate;
		} else if (numberOfHits == 40) {
			MapUpdate mapUpdate = new MapUpdate(new String(), "{\"Territories\":[{\"territoryID\":0,\"playerID\":null,\"armies\":null},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":null,\"armies\":null},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":null,\"armies\":null},{\"territoryID\":8,\"playerID\":null,\"armies\":null},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":null,\"armies\":null},{\"territoryID\":12,\"playerID\":null,\"armies\":null},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":null,\"armies\":null},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":null,\"armies\":null},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":1},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":1,\"armies\":2},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":1,\"armies\":3},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":null,\"armies\":null},{\"territoryID\":37,\"playerID\":null,\"armies\":null},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
			return mapUpdate;
		} else if (numberOfHits == 44) {
			LogUpdate logUpdate = new LogUpdate("Another Test log update");
			return logUpdate;
		} else if (numberOfHits == 45) {
			MapUpdate mapUpdate = new MapUpdate(new String(), "{\"Territories\":[{\"territoryID\":0,\"playerID\":0,\"armies\":1},{\"territoryID\":1,\"playerID\":0,\"armies\":5},{\"territoryID\":2,\"playerID\":0,\"armies\":8},{\"territoryID\":3,\"playerID\":2,\"armies\":3},{\"territoryID\":4,\"playerID\":0,\"armies\":1},{\"territoryID\":5,\"playerID\":0,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":2,\"armies\":5},{\"territoryID\":8,\"playerID\":0,\"armies\":1},{\"territoryID\":9,\"playerID\":0,\"armies\":2},{\"territoryID\":10,\"playerID\":0,\"armies\":1},{\"territoryID\":11,\"playerID\":0,\"armies\":1},{\"territoryID\":12,\"playerID\":2,\"armies\":3},{\"territoryID\":13,\"playerID\":0,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":2,\"armies\":4},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":2,\"armies\":7},{\"territoryID\":18,\"playerID\":null,\"armies\":null},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":1},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":null,\"armies\":null},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":null,\"armies\":null},{\"territoryID\":28,\"playerID\":null,\"armies\":null},{\"territoryID\":29,\"playerID\":null,\"armies\":null},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":1,\"armies\":2},{\"territoryID\":33,\"playerID\":null,\"armies\":null},{\"territoryID\":34,\"playerID\":1,\"armies\":3},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":0,\"armies\":2},{\"territoryID\":37,\"playerID\":1,\"armies\":3},{\"territoryID\":38,\"playerID\":null,\"armies\":null},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
			return mapUpdate;
		} else if (numberOfHits == 50) {
			MapUpdate mapUpdate = new MapUpdate(new String(), "{\"Territories\":[{\"territoryID\":0,\"playerID\":1,\"armies\":2},{\"territoryID\":1,\"playerID\":2,\"armies\":5},{\"territoryID\":2,\"playerID\":1,\"armies\":8},{\"territoryID\":3,\"playerID\":1,\"armies\":1},{\"territoryID\":4,\"playerID\":2,\"armies\":1},{\"territoryID\":5,\"playerID\":2,\"armies\":1},{\"territoryID\":6,\"playerID\":0,\"armies\":1},{\"territoryID\":7,\"playerID\":1,\"armies\":1},{\"territoryID\":8,\"playerID\":2,\"armies\":1},{\"territoryID\":9,\"playerID\":0,\"armies\":1},{\"territoryID\":10,\"playerID\":2,\"armies\":1},{\"territoryID\":11,\"playerID\":2,\"armies\":5},{\"territoryID\":12,\"playerID\":0,\"armies\":8},{\"territoryID\":13,\"playerID\":2,\"armies\":1},{\"territoryID\":14,\"playerID\":0,\"armies\":1},{\"territoryID\":15,\"playerID\":2,\"armies\":1},{\"territoryID\":16,\"playerID\":0,\"armies\":1},{\"territoryID\":17,\"playerID\":0,\"armies\":1},{\"territoryID\":18,\"playerID\":2,\"armies\":1},{\"territoryID\":19,\"playerID\":0,\"armies\":1},{\"territoryID\":20,\"playerID\":0,\"armies\":1},{\"territoryID\":21,\"playerID\":2,\"armies\":5},{\"territoryID\":22,\"playerID\":0,\"armies\":8},{\"territoryID\":23,\"playerID\":2,\"armies\":1},{\"territoryID\":24,\"playerID\":0,\"armies\":1},{\"territoryID\":25,\"playerID\":0,\"armies\":1},{\"territoryID\":26,\"playerID\":0,\"armies\":1},{\"territoryID\":27,\"playerID\":0,\"armies\":1},{\"territoryID\":28,\"playerID\":2,\"armies\":1},{\"territoryID\":29,\"playerID\":2,\"armies\":1},{\"territoryID\":30,\"playerID\":0,\"armies\":1},{\"territoryID\":31,\"playerID\":0,\"armies\":5},{\"territoryID\":32,\"playerID\":2,\"armies\":8},{\"territoryID\":33,\"playerID\":0,\"armies\":1},{\"territoryID\":34,\"playerID\":0,\"armies\":1},{\"territoryID\":35,\"playerID\":0,\"armies\":1},{\"territoryID\":36,\"playerID\":0,\"armies\":1},{\"territoryID\":37,\"playerID\":0,\"armies\":1},{\"territoryID\":38,\"playerID\":0,\"armies\":1},{\"territoryID\":39,\"playerID\":0,\"armies\":1},{\"territoryID\":40,\"playerID\":0,\"armies\":1},{\"territoryID\":41,\"playerID\":0,\"armies\":1}]}");
			return mapUpdate;
		} else if (numberOfHits == 51) {
			Reinforce reinforce = new Reinforce("Select territories to reinforce", 3);
			return reinforce;
		} else if (numberOfHits == 52) {
			MakeTurn makeTurn = new MakeTurn("Make a turn");
		    makeTurn.setTimeOut("");
			return makeTurn;
		} else if (numberOfHits == 53) {
			AllocateCard allocateCard = new AllocateCard("You collected a card", 11, "Infantry");
			return allocateCard;
		} else if (numberOfHits == 54) {
			MakeTurn makeTurn = new MakeTurn("Make a turn");
		    makeTurn.setTimeOut("");
			return makeTurn;
		} else if (numberOfHits == 55) {
			AllocateCard allocateCard = new AllocateCard("You collected a card", 30, "Cavalry");
			return allocateCard;
		} else if (numberOfHits == 56) {
			MakeTurn makeTurn = new MakeTurn("Make a turn");
		    makeTurn.setTimeOut("");
			return makeTurn;
		} else if (numberOfHits == 57) {
			AllocateCard allocateCard = new AllocateCard("You collected a card", 23, "Artillery");
			return allocateCard;
		} else if (numberOfHits == 58) {
			AllocateArmies allocateArmies = new AllocateArmies("Allocate armies", 23, 34, 3);
			return allocateArmies;
		} else if (numberOfHits == 59) {
			Player[] playerArray = new Player[players.size()];
			players.toArray(playerArray);
			Winner winner = new Winner("GAME OVER!", true, playerArray);
			return winner;
		} else {
			return null;
		}
	}

	/**
	 * 
	 */
	public void sendUpdateResponse(Update update) {
		
		System.out.println("Called");
	}

	/**
	 * 
	 */
	public void leaveGame()  {
		
		numberOfHits = 0;
	}
}
