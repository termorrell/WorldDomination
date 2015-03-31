package controller;


import java.util.HashSet;
import java.util.Set;

public class AcknowledgementManager {
    int id;
    Set<Integer> players;
    boolean acknowledgedByAllPlayers;

    public AcknowledgementManager() {
        this.id = -1;
        this.acknowledgedByAllPlayers = false;
        this.players = new HashSet<>();
    }

    public boolean isAcknowledgedByAllPlayers( int numberOfPlayers) {
        if(acknowledgedByAllPlayers) {
            return true;
        }
        if(players.size() == numberOfPlayers) {
            acknowledgedByAllPlayers = true;
            id++;
        }
        return false;
    }

    public int addAcknowledgement(int playerId, int id) {
        if(this.id == id) {
            acknowledgedByAllPlayers = false;
            players.add(playerId);
            return 0;
        } else {
            return -1;
        }
    }
}
