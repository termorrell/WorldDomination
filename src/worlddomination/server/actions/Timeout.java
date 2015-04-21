package worlddomination.server.actions;

public class Timeout extends Action{
    int lostPlayerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'timeout' command
     * @param acknowledgementId id of the acknowledgement that needs to be sent
     * @param lostPlayerId id of the player who has left
     */
    public Timeout(int acknowledgementId, int lostPlayerId) {
        this.acknowledgementId = acknowledgementId;
        this.lostPlayerId = lostPlayerId;
    }

    public int getLostPlayerId() {
        return lostPlayerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }
}
