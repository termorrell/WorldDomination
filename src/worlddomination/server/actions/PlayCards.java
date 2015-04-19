package worlddomination.server.actions;


public class PlayCards extends Action {
    int numberOfSetsTraded;
    int[][] setsTradedIn; // ids of cards in sets
    int numberOfArmiesForCards; // TODO verify that this is actually meant in the protocol
    int playerId;
    int acknowledgementId;

    public PlayCards(int numberOfSetsTraded, int[][] setsTradedIn, int numberOfArmiesForCards, int playerId, int acknowledgementId) {
        this.numberOfSetsTraded = numberOfSetsTraded;
        this.setsTradedIn = setsTradedIn;
        this.numberOfArmiesForCards = numberOfArmiesForCards;
        this.playerId = playerId;
        this.acknowledgementId = acknowledgementId;
    }

    public int getNumberOfSetsTraded() {
        return numberOfSetsTraded;
    }

    public int[][] getSetsTradedIn() {
        return setsTradedIn;
    }

    public int getNumberOfArmiesForCards() {
        return numberOfArmiesForCards;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }
}
