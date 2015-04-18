package actions;


public class Defend extends Action {
    int numberOfArmies;
    int playerId;
    int acknowledgementId;

    public Defend(int numberOfArmies, int playerId, int acknowledgementId) {
        this.numberOfArmies = numberOfArmies;
        this.playerId = playerId;
        this.acknowledgementId = acknowledgementId;
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
