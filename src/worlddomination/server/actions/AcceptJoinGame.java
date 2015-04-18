package actions;


public class AcceptJoinGame extends Action {
    int playerId;
    int acknowledgementTimeout;
    int moveTimeout;

    public AcceptJoinGame(int playerId, int acknowledgementTimeout, int moveTimeout) {
        this.playerId = playerId;
        this.acknowledgementTimeout = acknowledgementTimeout;
        this.moveTimeout = moveTimeout;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAcknowledgementTimeout() {
        return acknowledgementTimeout;
    }

    public int getMoveTimeout() {
        return moveTimeout;
    }
}
