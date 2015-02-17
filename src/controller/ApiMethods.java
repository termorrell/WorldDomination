package controller;

import org.json.JSONArray;

/**
 * Created by Maria on 27/11/14.
 */
public interface ApiMethods {


    public void attackReceived(JSONArray json);
    public void defendReceived(JSONArray json);
    public void joinGameReceived(JSONArray json);
    public void rollReceived(JSONArray json);
    public void rollHashReceived(JSONArray json);
    public void rollNumberReceived(JSONArray json);
    public void setupReceived(JSONArray json);
    public void acknowledgementReceived(JSONArray json);
    public void tradeInReceived(JSONArray json);
    public void deployReceived(JSONArray json);
    public void attackWonReceived(JSONArray json);
    public void fortifyReceived(JSONArray json);
    public void winReceived(JSONArray json);
    public void unrecognisedResponse(JSONArray json);


}
