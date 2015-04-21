package worlddomination.server.actions;


public class RollNumber extends Action{
    String number;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'roll_number' command
     * @param playerId id of the player who sent the command
     * @param number hash of the number sent
     */
    public RollNumber(int playerId, String number) {
        this.playerId = playerId;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public int getPlayerId() {
        return playerId;
    }
}
