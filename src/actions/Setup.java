package actions;


public class Setup extends Action {
    int territoryId;
    int acknowledgementId;
    int playerId;

    public Setup(int playerId, int territoryId, int acknowledgementId) {
        this.playerId = playerId;
        this.territoryId = territoryId;
        this.acknowledgementId = acknowledgementId;
    }

    public int getTerritoryId() {
        return territoryId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }

    public int getPlayerId() {
        return playerId;
    }
}
