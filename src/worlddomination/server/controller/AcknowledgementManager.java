package worlddomination.server.controller;


import java.util.HashSet;
import java.util.Set;

public class AcknowledgementManager {
    int id;
    Set<Integer> players;
    boolean acknowledgedByAllPlayers;

    /**
     * Constructor for acknowledgement manager
     */
    public AcknowledgementManager() {
        this.id = -1;
        this.acknowledgedByAllPlayers = false;
        this.players = new HashSet<>();
    }

    /**
     * check whether an acknowledgement has been received for all players
     * @param numberOfPlayers number of players in the game
     * @return true if it has been sent
     */
    public boolean isAcknowledgedByAllPlayers(int numberOfPlayers) {
        if (acknowledgedByAllPlayers) {
            return true;
        }
        if (players.size() == numberOfPlayers - 1) {
            acknowledgedByAllPlayers = true;
        }
        return acknowledgedByAllPlayers;
    }

    /**
     * Add a new acknowledgement when received
     * @param playerId player id who sent the acknowledgement
     * @param id id of the acknowledgement
     * @return error code
     */
    public int addAcknowledgement(int playerId, int id) {
        if (this.id == id) {
            players.add(playerId);
            return 0;
        } else {
            return -1;
        }
    }

    public Set<Integer> getPlayers() {
        return players;
    }

    public int getAcknowledgementId() {
        return id;
    }

    /**
     * Keep track of which acknowledgement game is on
     */
    public void incrementAcknowledgementIdAndExpectAcknowledgment() {
        acknowledgedByAllPlayers = false;
        players.clear();
        id++;
    }
}
