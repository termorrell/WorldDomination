package network;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Maria on 27/11/14.
 */
public interface ApiMethods {

    public void sendRequest(String json);
    public void parseResponse(String response);
    public void receivedRequest(JSONObject response);


}
