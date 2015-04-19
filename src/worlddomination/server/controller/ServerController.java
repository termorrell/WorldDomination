package worlddomination.server.controller;

import worlddomination.server.actions.Action;
import worlddomination.server.actions.JoinGame;
import worlddomination.server.actions.Ping;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ServerController {

    Queue<Action> actions = new LinkedList<>();

    // TODO store player names and ids in some data structure

    public void run() {
        while (true) {
            executeActions();
        }
    }

    private void executeActions() {
        while (!actions.isEmpty()) {
            Action nextAction = actions.poll();
            executeAction(nextAction);
        }
    }


    private void executeAction(Action action) {

        if (action instanceof JoinGame) {
            // TODO call handler function, accept or reject and send ping if no players are going to join anymore
        } else if (action instanceof Ping) {
            // TODO call handler function, send ready if all players have pinged or timed out
        } else {
            // TODO call forwarding function
        }

    }

    public synchronized void handleAction(Action action) {
        actions.add(action);
    }
}
