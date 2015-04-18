package worlddomination.server.view;

import java.util.Map;

public interface INetworkView {
	public String getLocalPlayerName();
    public void displayRejection(String message);
    public void displayJoinedPlayers( Map<Integer, String[]> players);
    public boolean getPingReadyConfirmation();
}
