package worlddomination.server.actions;

public class Fortify extends Action{
    int originTerritory;
    int destinationTerritory;
    int numberOfArmies;
    int playerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'fortify' command
     * @param originTerritory origin territory
     * @param destinationTerritory territory armies move to
     * @param numberOfArmies number of armies being moved
     * @param playerId id of the player who sent the command
     * @param acknowledgementId id of the acknowledgement
     */
    public Fortify(int originTerritory, int destinationTerritory, int numberOfArmies, int playerId, int acknowledgementId) {
        this.originTerritory = originTerritory;
        this.destinationTerritory = destinationTerritory;
        this.numberOfArmies = numberOfArmies;
        this.playerId = playerId;
        this.acknowledgementId = acknowledgementId;
    }

    public int getOriginTerritory() {
        return originTerritory;
    }

    public int getDestinationTerritory() {
        return destinationTerritory;
    }

    public int getNumberOfArmies() {
        return numberOfArmies;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }
}
