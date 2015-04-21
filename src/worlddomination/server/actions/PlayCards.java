package worlddomination.server.actions;


public class PlayCards extends Action {
    int numberOfSetsTraded;
    int[][] setsTradedIn; // ids of cards in sets
    int numberOfArmiesForCards; // TODO verify that this is actually meant in the protocol
    int playerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'play_cards' command
     * @param numberOfSetsTraded number of sets being traded
     * @param setsTradedIn ids of the cards being traded
     * @param numberOfArmiesForCards number of armies awarded
     * @param playerId id of play who sent the command
     * @param acknowledgementId id of the acknowledgement
     */
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
