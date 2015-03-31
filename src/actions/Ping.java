package actions;


public class Ping extends Action {
    int numberOfPlayers;

    public Ping(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
