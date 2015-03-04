package network;

import model.Card;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ${mm280} on 04/03/15.
 */
public class ClientResponseGenerator {

    public static JSONObject sendJoinGame(int[] supported_versions, String[] supported_features){
        JSONObject response = new JSONObject();
        response.put("command","join_game");
        JSONObject payload = new JSONObject();
        JSONArray supported_version = new JSONArray(supported_versions);
        JSONArray supported_feature = new JSONArray(supported_features);
        payload.put("supported_versions",supported_version);
        payload.put("supported_features",supported_feature);
        response.put("payload",payload);
        return response;
    }
    public static JSONObject pingGenerator(int players_joined, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","ping");
        response.put("payload","null");
        response.put("player_id", player_id);
        return response;
    }
    public static JSONObject setupGenerator(int player_id, int territory_id, int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","setup");
        response.put("payload",territory_id);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject playCardsGenerator(Card[][] cards, int armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","play_cards");
        JSONObject payload = new JSONObject();
        JSONArray card = new JSONArray(cards);
        payload.put("cards",card);
        payload.put("armies",armies);
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject drawCardsGenerator(int card_id, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","draw_card");
        response.put("payload",card_id);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject deployGenerator(int[][]armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","deploy");
        JSONArray payload = new JSONArray(armies);
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject attackGenerator(int[] attack, int player_id, int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(attack);
        response.put("command","attack");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject defendGenerator(int armies_no,int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","defend");
        response.put("payload", armies_no);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject attackCaptureGenerator(int[] attack,int player_id,int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(attack);
        response.put("command","attack_capture");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject fortifyGenerator(int[] armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(armies);
        response.put("command","fortify");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }
    public static JSONObject ackGenerator(int ack_id, int response_id,int player_id){
        JSONObject response = new JSONObject();
        JSONObject payload = new JSONObject();
        payload.put("ack_id",ack_id);
        payload.put("response",response_id);
        response.put("command","acknowledgement");
        response.put("payload",payload);
        response.put("player_id",player_id);
        return response;
    }
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
    public static JSONObject rollHashGenerator(String sha, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","roll_hash");
        response.put("payload",sha);
        response.put("player_id",player_id);
        return response;
    }
    public static JSONObject rollNumberGenerator(String sha, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","roll_number");
        response.put("payload",sha);
        response.put("player_id",player_id);
        return response;
    }
}
