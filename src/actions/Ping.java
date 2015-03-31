package actions;


public class Ping extends Action {
    int numberOfPlayers;
    int playerId;

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
