package worlddomination.shared.updates;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class Lobby extends Update {
	
	private ArrayList<LobbyPlayer> listOfPlayers;

	public Lobby() {
		super();
		listOfPlayers = new ArrayList<LobbyPlayer> ();
	}

	public ArrayList<LobbyPlayer> getListOfPlayers() {
		return listOfPlayers;
	}
	
	public ArrayList<LobbyPlayer> addPlayerToListOfPlayers(LobbyPlayer player) {
		listOfPlayers.add(player);
		return listOfPlayers;
	}
}
