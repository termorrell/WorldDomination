package worlddomination.server.network;

import org.json.JSONObject;

public interface ApiMethods {

    public JSONObject parseResponse(String response);
    public void checkCommandRequest(int playerId, JSONObject response);


}
