package actions;


public class Attack extends Action{
    int originTerritory;
    int destinationTerritory;
    int numberOfArmies;
    int playerId;
    int acknowledgementId;

    public Attack(int originTerritory, int destinationTerritory, int numberOfArmies, int playerId, int acknowledgementId) {
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
