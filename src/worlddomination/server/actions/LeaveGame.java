package worlddomination.server.actions;


public class LeaveGame extends Action{
    int playerId;
    int responseCode;
    String message;
    boolean receiveUpdates;

    /**
     * Stores the attributes necessary for sending and receiving an 'leave_game' command
     * @param playerId id of the player who sent the request
     * @param responseCode code to say why
     * @param message  message to say why left
     * @param receiveUpdates would the user like to receive more updates
     */
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

