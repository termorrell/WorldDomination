package controller;

import java.*;
/**
 * Created by Maria on 27/11/14.
 */
abstract class ApiManager implements ApiMethods {


    /**
     * Sends a request to server
     * Takes in a string of Json?
     */
    public void sendRequest(String json) {

    }


    /**
     * Receive information from the server and analyses it for each type of command
     */
    public void receiveRequest() {
        String serverResponse = "";
        if (serverResponse.contains("attack")) {
            attackReceived(serverResponse);
        } else if (serverResponse.contains("defend")) {
            defendReceived(serverResponse);
        } else if (serverResponse.contains("join_game")) {
            joinGameReceived(serverResponse);
        } else if (serverResponse.contains("roll")) {
            rollReceived(serverResponse);
        } else if (serverResponse.contains("roll_hash")) {
            rollHashReceived(serverResponse);
        } else if (serverResponse.contains("roll_number")) {
            rollNumberReceived(serverResponse);
        } else if (serverResponse.contains("setup")) {
            setupReceived(serverResponse);
        } else if (serverResponse.contains("acknowledgement")) {
            acknowledgementReceived(serverResponse);
        } else if (serverResponse.contains("trade_in_cards")) {
            tradeInReceived(serverResponse);
        } else if (serverResponse.contains("deploy")) {
            deployReceived(serverResponse);
        } else if (serverResponse.contains("attack_capture")) {
            attackWonReceived(serverResponse);
        } else if (serverResponse.contains("fortify")) {
            fortifyReceived(serverResponse);
        } else if (serverResponse.contains("win")) {
            winReceived(serverResponse);
        } else {

        }
    }
}
