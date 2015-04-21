package worlddomination.server.actions;

public class Acknowledgement extends Action{
    int acknowledgementId;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'acknowledgement' command
     * @param acknowledgementId id of the acknowledgement
     * @param playerId id of the player
     */
    public Acknowledgement(int acknowledgementId, int playerId) {
        this.acknowledgementId = acknowledgementId;
        this.playerId = playerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
