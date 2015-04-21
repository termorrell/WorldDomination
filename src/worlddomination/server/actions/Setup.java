package worlddomination.server.actions;


public class Setup extends Action {
    int territoryId;
    int acknowledgementId;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'setup' command
     * @param playerId id of the player who has sent the command
     * @param territoryId id of the territory that is being claimed
     * @param acknowledgementId id of the acknowledgement to be sent
      */
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
