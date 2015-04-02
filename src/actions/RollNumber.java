package actions;


public class RollNumber extends Action{
    String number;
    int playerId;

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
