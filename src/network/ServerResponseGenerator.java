package network;

import org.json.JSONArray;
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
    //TODO NEVER CALLED?
    public static JSONObject rejectJoinGameGenerator(){
        JSONObject response = new JSONObject();
        response.put("command","reject_join_game");
        response.put("payload","Game in Progress");
        return response;
    }
    //todo players name???
    public static JSONObject playersJoined(String[] player_name){
        JSONObject response = new JSONObject();
        response.put("command", "players_joined");
        JSONArray payload = new JSONArray(player_name);
        response.put("payload",payload);
        return response;
    }
    public static JSONObject pingGenerator(int players_joined, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","ping");
        response.put("payload", players_joined);
        response.put("player_id", player_id);
        return response;
    }
    public static JSONObject readyGenerator(int player_id, int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","ready");
        response.put("payload","null");
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
}
