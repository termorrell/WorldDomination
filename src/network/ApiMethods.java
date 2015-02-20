package network;

import org.json.JSONObject;

/**
 * Created by Maria on 27/11/14.
 */
public interface ApiMethods {

    public JSONObject sendRequest(String json);
    public JSONObject parseResponse(String response);
    public JSONObject receivedRequest(JSONObject response);


}
