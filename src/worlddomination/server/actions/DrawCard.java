package worlddomination.server.actions;


public class DrawCard extends Action{
    int cardId;
    int playerId;
    int acknowledgementId;

    /**
     * Stores the attributes necessary for sending and receiving an 'draw_card' command
     * @param cardId id of the card drawn
     * @param playerId id of the player who sent the message
     * @param acknowledgementId id of the acknowledgement
     */
    public DrawCard(int cardId, int playerId, int acknowledgementId) {
        this.cardId = cardId;
        this.playerId = playerId;
        this.acknowledgementId = acknowledgementId;
    }

    public int getCardId() {
        return cardId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAcknowledgementId() {
        return acknowledgementId;
    }
}
