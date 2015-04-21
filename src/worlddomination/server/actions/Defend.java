package worlddomination.server.actions;


public class Defend extends Action {
    int numberOfArmies;
    int playerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'defend' command
     * @param numberOfArmies number of armies to defend with
     * @param playerId id of the player who sent the command
     * @param acknowledgementId id of the acknowledgement
     */
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
