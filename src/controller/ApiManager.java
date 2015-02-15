package controller;

import org.json.JSONArray;
import org.json.JSONObject;

abstract class ApiManager implements ApiMethods {


    /**
     * Sends a request to server
     * Takes in a string of Json?
     */
    public void sendRequest(String json) {

    }


    /**
     * Receive information from the server in the form of JSON and analyses it for each type of command
     */
    public void receiveRequest(JSONObject response) {
        String command = response.getString("command");
        command.toLowerCase();
        JSONArray payload = response.getJSONArray("payload");
        if (command.equals("attack")) {
            attackReceived(payload);
        } else if (command.equals("defend")) {
            defendReceived(payload);
        } else if (command.equals("join_game")) {
            joinGameReceived(payload);
        } else if (command.equals("roll")) {
            rollReceived(payload);
        } else if (command.equals("roll_hash")) {
            rollHashReceived(payload);
        } else if (command.equals("roll_number")) {
            rollNumberReceived(payload);
        } else if (command.equals("setup")) {
            setupReceived(payload);
        } else if (command.equals("acknowledgement")) {
            acknowledgementReceived(payload);
        } else if (command.equals("trade_in_cards")) {
            tradeInReceived(payload);
        } else if (command.equals("deploy")) {
            deployReceived(payload);
        } else if (command.equals("attack_capture")) {
            attackWonReceived(payload);
        } else if (command.equals("fortify")) {
            fortifyReceived(payload);
        } else if (command.equals("win")) {
            winReceived(payload);
        } else {
            unrecognisedResponse(payload);
        }
    }
}
