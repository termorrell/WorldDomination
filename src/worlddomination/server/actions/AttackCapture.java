package worlddomination.server.actions;

public class AttackCapture extends Action{
    int originTerritory;
    int destinationTerritory;
    int numberOfArmies;
    int playerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'attack_capture' command
     * @param originTerritory origin territory
     * @param destinationTerritory territory being attacked
     * @param numberOfArmies number of armies attacking with
     * @param playerId id of the player who sent the command
     * @param acknowledgementId id of the acknowledgement 
     */
    public AttackCapture(int originTerritory, int destinationTerritory, int numberOfArmies, int playerId, int acknowledgementId) {
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
