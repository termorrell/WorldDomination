package worlddomination.shared.updates;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Lobby extends Update {
	
	private ArrayList<Player> listOfPlayers;

	public Lobby() {
		super();
		listOfPlayers = new ArrayList<Player> ();
	}

	public ArrayList<Player> getListOfPlayers() {
		return listOfPlayers;
	}
	
	public ArrayList<Player> addPlayerToListOfPlayers(Player player) {
		listOfPlayers.add(player);
		return listOfPlayers;
	}
}
