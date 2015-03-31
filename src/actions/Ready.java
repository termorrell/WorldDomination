package actions;


public class Ready extends Action{
    int playerId;
    int ackId;

    public Ready(int playerId, int ackId) {
        this.playerId = playerId;
        this.ackId = ackId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAckId() {
        return ackId;
    }
}
