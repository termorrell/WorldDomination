package actions;


import java.util.Map;

public class PlayersJoined extends Action{
    /**
     * Integer - player id
     *
     * String[2]
     *  0 - player name
     *  1 - player public key or empty string
     */
    Map<Integer, String[]> players;

    public PlayersJoined(Map<Integer, String[]> players) {
        this.players = players;
    }

    public Map<Integer, String[]> getPlayers() {
        return players;
    }
}
