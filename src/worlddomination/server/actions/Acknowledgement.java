package worlddomination.server.actions;

public class Acknowledgement extends Action{
    int acknowledgementId;
    int playerId;

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
