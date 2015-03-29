package network;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ${mm280} on 04/03/15.
 */

/**
 * Creates responses for the server to send in the form of JSONObjects, following class reps format
 */
public class ServerResponseGenerator {

    /**
     * Creates the JSONObject for the join game method
     * @param player_id id of the player connecting
     * @param ack_timeout time server will wait for an acknowledgement
     * @param move_timeout time server will wait for a move
     * @return the JSONObject for Join Game
     */
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

    /**
     * Creates a JSONObject for when the server wishes to send the reject game json
     * @return the reject game json
     */
    public static JSONObject rejectJoinGameGenerator(){
        JSONObject response = new JSONObject();
        response.put("command","reject_join_game");
        response.put("payload","Game in Progress");
        return response;
    }

    /**
     * Creates a JSONObject for when the server sends the players joined json
     * @param player_name String array of all the player names
     * @return players joined json
     */
    public static JSONObject playersJoined(String[] player_name){
        JSONObject response = new JSONObject();
        response.put("command", "players_joined");
        JSONArray payload = new JSONArray(player_name);
        response.put("payload",payload);
        return response;
    }

    /**
     * Creates a JSONObject for when the server sends the ping json
     * @param players_joined the number of players already joined the game
     * @param player_id id of the current player, may be null if host not a player
     * @return ping command json
     */
    public static JSONObject pingGenerator(int players_joined, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","ping");
        response.put("payload", players_joined);
        response.put("player_id", player_id);
        return response;
    }

    /**
     * Creates a JSONObject for the ready command
     * @param player_id id of the player, may be null in case of non-player host
     * @param ack_id the acknowledgement id number
     * @return ready command json
     */
    public static JSONObject readyGenerator(int player_id, int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","ready");
        response.put("payload","null");
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Creates a JSONObject for the initialise game command
     * @param version version the game will be played using
     * @param supported_features features this server supports
     * @return initialise game command json
     */
    public static JSONObject initGameGenerated(int version, String[] supported_features){
        JSONObject response = new JSONObject();
        response.put("command","initialise_game");
        JSONObject payload = new JSONObject();
        payload.put("version", version);
        JSONArray supported_feature = new JSONArray(supported_features);
        payload.put("supported_features",supported_feature);
        response.put("payload",payload);
        return response;
    }

    /**
     * Creates a JSONObject for the roll command
     * @param dice_count amount of die being rolled
     * @param dice_faces number of faces on dice
     * @param player_id id of player
     * @return roll command json
     */
    public static JSONObject rollGenerator(int dice_count,int dice_faces,int player_id){
        JSONObject response = new JSONObject();
        JSONObject payload = new JSONObject();
        payload.put("dice_count",dice_count);
        payload.put("dice_faces",dice_faces);
        response.put("command","roll");
        response.put("payload",payload);
        response.put("player_id",player_id);
        return response;
    }

    /**
     * Creates a JSONObject of the timeout command
     * @param player_id id of the player
     * @param ack_id id of the acknowledgment
     * @param player_left_id id of the player who has left
     * @return timeout command json
     */
    public static JSONObject timeoutGenerator(int player_id,int ack_id,int player_left_id){
        JSONObject response = new JSONObject();
        response.put("command","timeout");
        response.put("payload",player_left_id);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Creates a JSONObect for the leave game command
     * @param response_code code specifying reason for leaving
     * @param receive_updates boolean explaining whether player wishes to continue receiving updates of game
     * @param player_id id of player
     * @return leave game command json
     */
    public static JSONObject leaveGameGenerator(int response_code,boolean receive_updates,int player_id){
        JSONObject response = new JSONObject();
        JSONObject payload = new JSONObject();
        payload.put("response",response_code);
        payload.put("receive_updates", receive_updates);
        response.put("command","leave_game");
        response.put("payload",payload);
        response.put("player_id",player_id);
        return response;
    }}
