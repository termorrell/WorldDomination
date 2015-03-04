package network;

import org.json.JSONObject;

/**
 * Created by ${mm280} on 04/03/15.
 */
public class ServerResponseGenerator {

    public static JSONObject acceptJoinGameGenerator(int player_id, int ack_timeout, int move_timeout){
        JSONObject accept_game = new JSONObject();
        accept_game.put("command", "accept_join_game");
        JSONObject payload = new JSONObject();
        payload.put("player_id",player_id);
        payload.put("acknowledgement_timeout", ack_timeout);
        payload.put("move_timeout", move_timeout);
        accept_game.put("payload",payload);
        return accept_game;
    }
}
