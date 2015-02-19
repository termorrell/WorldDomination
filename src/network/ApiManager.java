package network;

import model.Model;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager implements ApiMethods {
        //TODO Figure out active player????
    Player activePlayer = new Player();
    Model model = new Model();

    public void parseResponse(String response){
        JSONObject obj = new JSONObject(response);
        receivedRequest(obj);
    }
    /**
     * Sends a request to server
     * Takes in a string and converts to JSON
     */
    public JSONObject sendRequest(String json) {
    JSONObject obj = new JSONObject(json);
        return obj;
    }
    /**
     * Receive information from the server in the form of JSON and analyses it for each type of command
     */
    public void receivedRequest(JSONObject response) {
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

    public void attackReceived(JSONArray json) {

        //Moves.attack(activePlayer,model.getGameState(),json.getInt(0),json.getInt(1),json.getInt(2));
    }
    public void defendReceived(JSONArray json) {

    }

    public void joinGameReceived(JSONArray json) {
    	System.out.println("join");
    }


    public void rollReceived(JSONArray json) {

    }


    public void rollHashReceived(JSONArray json) {
    	
    }

    public void rollNumberReceived(JSONArray json) {

    }

    public void setupReceived(JSONArray json) {

    }

    public void acknowledgementReceived(JSONArray json) {

    }


    public void tradeInReceived(JSONArray json) {

    }
    public void deployReceived(JSONArray json) {

    }

    @Override
    public void attackWonReceived(JSONArray json) {

    }

    @Override
    public void fortifyReceived(JSONArray json) {

    }

    @Override
    public void winReceived(JSONArray json) {

    }

    @Override
    public void unrecognisedResponse(JSONArray json) {

    }

}
