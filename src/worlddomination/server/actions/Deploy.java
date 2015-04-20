package worlddomination.server.actions;

public class Deploy extends Action{
    int[][] armies; // array of pairs: [0] - territory id and [1] - number of armies
    int playerId;
    int acknowledgementId;

    public Deploy(int[][] armies, int playerId, int acknowledgementId) {
        this.armies = armies;
        this.playerId = playerId;
        this.acknowledgementId = acknowledgementId;
    }

    public int[][] getArmies() {
        return armies;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }
}
