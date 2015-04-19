package worlddomination.server.actions;


public class LeaveGame extends Action{
    int playerId;
    int responseCode;
    String message;
    boolean receiveUpdates;

    public LeaveGame(int playerId, int responseCode, String message, boolean receiveUpdates) {
        this.playerId = playerId;
        this.responseCode = responseCode;
        this.message = message;
        this.receiveUpdates = receiveUpdates;
    }

    public String getMessage() {
        return message;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getResponseCode() {
        return responseCode;
    }
    public boolean getReceiveUpdates(){return receiveUpdates;}
}

