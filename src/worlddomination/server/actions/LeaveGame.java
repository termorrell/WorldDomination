package worlddomination.server.actions;


public class LeaveGame extends Action{
    int playerId;
    int responseCode;
    String message;

    public LeaveGame(int playerId, int responseCode, String message) {
        this.playerId = playerId;
        this.responseCode = responseCode;
        this.message = message;
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
}
