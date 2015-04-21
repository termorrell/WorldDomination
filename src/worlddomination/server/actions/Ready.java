package worlddomination.server.actions;


public class Ready extends Action{
    int playerId;
    int ackId;

    /**
     * Stores the attributes necessary for sending and receiving an 'ready' command
     * @param playerId id of the player who sent the command
     * @param ackId id of the acknowledgement that needs to be received
     */
    public Ready(int playerId, int ackId) {
        this.playerId = playerId;
        this.ackId = ackId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAckId() {
        return ackId;
    }
}
