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

    public boolean isAcknowledgedByAllPlayers(int numberOfPlayers) {
        if (acknowledgedByAllPlayers) {
            return true;
        }
        if (players.size() == numberOfPlayers - 1) {
            acknowledgedByAllPlayers = true;
        }
        return acknowledgedByAllPlayers;
    }

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

    public void expectAcknowledgement() {
        acknowledgedByAllPlayers = false;
        players.clear();
        id++;
    }
}
