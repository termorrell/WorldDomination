package actions;


public class DrawCard extends Action{
    int cardId;
    int playerId;
    int acknowledgementId;

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
