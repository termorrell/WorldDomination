package network;

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
}
