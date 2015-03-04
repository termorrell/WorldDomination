package network;

import org.json.JSONObject;

/**
 * Created by Maria on 27/11/14.
 */
public interface ApiMethods {

    public JSONObject parseResponse(String response);
    public JSONObject clientCheckCommandRequest(JSONObject response);
    public JSONObject serverCheckCommandRequest(JSONObject request);

}
