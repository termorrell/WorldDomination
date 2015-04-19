package worlddomination.server.actions;

public class RollHash extends Action{
    String hash;
    int playerId;

    public RollHash(int playerId, String hash) {
        this.playerId = playerId;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public int getPlayerId() {
        return playerId;
    }
}
