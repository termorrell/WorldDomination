package controller;

/**
 * Created by Maria on 27/11/14.
 */
public interface ApiMethods {


    public void attackReceived(String json);
    public void defendReceived(String json);
    public void joinGameReceived(String json);
    public void rollReceived(String json);
    public void rollHashReceived(String json);
    public void rollNumberReceived(String json);
    public void setupReceived(String json);
    public void acknowledgementReceived(String json);
    public void tradeInReceived(String json);
    public void deployReceived(String json);
    public void attackWonReceived(String json);
    public void fortifyReceived(String json);
    public void winReceived(String json);



}
