package worlddomination.server.network;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ${mm280} on 04/03/15.
 */

/**
 * Creates responses for the server to send in the form of JSONObjects, following class reps format
 */
public class ServerResponseGenerator {

    /**
     * Creates the JSONObject for the join game method
     *
     * @param player_id    id of the player connecting
     * @param ack_timeout  time server will wait for an acknowledgement
     * @param move_timeout time server will wait for a move
     * @return the JSONObject for Join Game
     */
    public static JSONObject acceptJoinGameGenerator(int player_id, int ack_timeout, int move_timeout) {
        JSONObject message = new JSONObject();
        JSONObject accept_game = new JSONObject();
        accept_game.put("command", "accept_join_game");
        JSONObject payload = new JSONObject();
        if(player_id==-1){
            payload.put("player_id", JSONObject.NULL);
        }else{
            payload.put("player_id", player_id);

        }
        payload.put("acknowledgement_timeout", ack_timeout);
        payload.put("move_timeout", move_timeout);
        accept_game.put("payload", payload);
        String obj = accept_game.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message",obj);
        System.out.println(message.toString());
        return message;
    }


    /**
     * Creates a JSONObject for when the server wishes to send the reject game json
     *
     * @return the reject game json
     */
    public JSONObject rejectJoinGameGenerator(String errorMessage) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "reject_join_game");
        response.put("payload", errorMessage);
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message",obj);
        System.out.println(message.toString());
        return message;
    }

    public JSONObject ackGenerator(int ack_id, int player_id) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "acknowledgement");
        response.put("payload", ack_id);
        if(player_id==-1){
            response.put("player_id", JSONObject.NULL);
        }else{
            response.put("player_id", player_id);
        }
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message",obj);
        System.out.println(message.toString());
        return message;
    }
    /**
     * Creates a JSONObject for when the server sends the players joined json
     *
     * @param players_joined array of all the player names
     * @return players joined json
     */
    public JSONObject playersJoined(HashMap<Integer, String> players_joined) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "players_joined");
        JSONArray payload = convertHashMapToJsonArray(players_joined);
        response.put("payload",payload);
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message",obj);
        System.out.println(message.toString());
        return message;
    }

    private JSONArray convertHashMapToJsonArray(HashMap<Integer, String> players_joined) {
        JSONArray payload = new JSONArray();
        for (Map.Entry<Integer, String> entry : players_joined.entrySet()) {
            JSONArray player = new JSONArray();
            player.put(entry.getKey());
            player.put(entry.getValue());
            payload.put(player);
        }
        return payload;
    }

    /**
     * Creates a JSONObject for when the server sends the ping json
     *
     * @param players_joined the number of players already joined the game
     * @param player_id      id of the current player, may be null if host not a player
     * @return ping command json
     */
    public JSONObject pingGenerator(int players_joined, int player_id) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "ping");
        System.out.println(players_joined);
        if (players_joined == -1) {
            response.put("payload",JSONObject.NULL);
        } else {
            response.put("payload", players_joined);
        }
        if(player_id==-1){
            response.put("player_id",JSONObject.NULL);
        }else{
            response.put("player_id", player_id);
        }
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message", obj);
        System.out.println(message.toString());
        return message;
    }

    /**
     * Creates a JSONObject for the ready command
     *
     * @param player_id id of the player, may be null in case of non-player host
     * @param ack_id    the acknowledgement id number
     * @return ready command json
     */
    public JSONObject readyGenerator(int player_id, int ack_id) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "ready");
        response.put("payload", JSONObject.NULL);
        if (player_id == -1) {
            response.put("player_id",JSONObject.NULL);
        } else {
            response.put("player_id", player_id);
        }
        response.put("ack_id", ack_id);
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message", obj);
        System.out.println(message.toString());
        return message;
    }

    /**
     * Creates a JSONObject for the initialise game command
     *
     * @param version            version the game will be played using
     * @param supported_features features this server supports
     * @return initialise game command json
     */
    public JSONObject initGameGenerated(double version, String[] supported_features) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "initialise_game");
        JSONObject payload = new JSONObject();
        payload.put("version", version);
        JSONArray supported_feature = new JSONArray(supported_features);
        payload.put("supported_features",supported_feature);
        response.put("payload",payload);
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message", obj);
        System.out.println(message.toString());
        return message;
    }

    /**
     * Creates a JSONObject of the timeout command
     *
     * @param player_id      id of the player
     * @param ack_id         id of the acknowledgment
     * @param player_left_id id of the player who has left
     * @return timeout command json
     */
    public JSONObject timeoutGenerator(int player_id, int ack_id, int player_left_id) {
        JSONObject message = new JSONObject();
        JSONObject response = new JSONObject();
        response.put("command", "timeout");
        response.put("payload", player_left_id);
        if (player_id == -1) {
            response.put("player_id",JSONObject.NULL);
        } else {
            response.put("player_id", player_id);
        }
        response.put("ack_id", ack_id);
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        message.put("message", obj);
        System.out.println(message.toString());
        return message;
    }

    /**
     * Creates a JSONObect for the leave game command
     *
     * @param response_code   code specifying reason for leaving
     * @param receive_updates boolean explaining whether player wishes to continue receiving updates of game
     * @param player_id       id of player
     * @return leave game command json
     */
    public JSONObject leaveGameGenerator(int response_code, boolean receive_updates, int player_id, String message) {
        JSONObject messages = new JSONObject();
        JSONObject response = new JSONObject();
        JSONObject payload =new JSONObject();
        payload.put("response", response_code);
        payload.put("message", message);
        payload.put("receive_updates\"",receive_updates);
        response.put("command", "leave_game");
        response.put("payload", payload);
        if(player_id==-1){
            response.put("player_id",JSONObject.NULL);
        }else{
            payload.put("player_id", player_id);

        }
        String obj = response.toString();
        obj = obj.replaceAll("\"","\\\"");
        messages.put("message", obj);
        System.out.println(messages.toString());
        return messages;
    }
}
