package worlddomination.server.actions;


public class AcceptJoinGame extends Action {
    int playerId;
    int acknowledgementTimeout;
    int moveTimeout;

    /**
     * Stores the attributes necessary for sending and receiving an 'accept_join_game' command
     * @param playerId id of the player who sent the message
     * @param acknowledgementTimeout time the client has to send an acknowledgement command
     * @param moveTimeout time the client has to send a move command
     */
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
