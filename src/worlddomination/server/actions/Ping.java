package worlddomination.server.actions;


public class Ping extends Action {
    int numberOfPlayers;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'ping' command
     * @param numberOfPlayers number of players in the game
     * @param playerId player who sent the acknowledgement
     */
    public Ping(int numberOfPlayers, int playerId) {
        this.numberOfPlayers = numberOfPlayers;
        this.playerId = playerId;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public int getPlayerId() {
        return playerId;
    }
}
