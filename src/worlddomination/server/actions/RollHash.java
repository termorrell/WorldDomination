package worlddomination.server.actions;

public class RollHash extends Action{
    String hash;
    int playerId;

    /**
     * Stores the attributes necessary for sending and receiving an 'roll_hash' command
     * @param playerId id of the player who sent the command
     * @param hash hash for the player
     */
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
