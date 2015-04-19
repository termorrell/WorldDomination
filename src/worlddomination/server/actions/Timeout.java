package worlddomination.server.actions;

public class Timeout extends Action {
    int lostPlayerId;
    int acknowledgementId;

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
