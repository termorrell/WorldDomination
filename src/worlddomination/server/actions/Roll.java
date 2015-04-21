package worlddomination.server.actions;

public class Roll extends Action{
    int numberOfRolls;
    int nubmerOfFaces;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'roll' command
     * @param numberOfRolls
     * @param nubmerOfFaces
     * @param playerId
     */
    public Roll(int numberOfRolls, int nubmerOfFaces, int playerId) {
        this.numberOfRolls = numberOfRolls;
        this.nubmerOfFaces = nubmerOfFaces;
        this.playerId = playerId;
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }

    public int getNubmerOfFaces() {
        return nubmerOfFaces;
    }

    public int getPlayerId() {
        return playerId;
    }
}
