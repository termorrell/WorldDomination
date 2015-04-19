package worlddomination.server.network;

import worlddomination.server.actions.*;
import worlddomination.server.controller.ServerController;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerApiManager implements ApiMethods {

    ServerController controller;

    public ServerApiManager(ServerController controller) {
        this.controller = controller;
    }

    /**
     * Parses a string into a Json objects
     *
     * @param response the string to be parsed
     * @return a new json object
     */
    public JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }

    public void checkCommandRequest(int player_id, JSONObject request) {
        String command = request.getString("command");
        switch (command) {
            case "join_game":
                joinGameReceived(player_id,request);
                break;
            case "ping":
                pingReceived(request);
                break;
            case "acknowledgement":
                acknowledgementReceived(request);
                break;
            case "roll":
                //todo call forward message
                break;
            case "roll_hash":
                //todo forward message
                break;
            case "roll_number":
                //todo call forward message
                break;
            case "leave_game":
                leaveGameReceived(request);
                break;
            case "setup":
                setUpReceived(request);
                break;
            case "play_cards":
                //TODO CALL FORWARD MESSAGE
                break;
            case "deploy":
                //TODO CALL FORWARD MESSAGE
                break;
            case "attack":
                //TODO CALL FORWARD MESSAGE
                break;
            case "defend":
                //TODO CALL FORWARD MESSAGE
                break;
            default:
                forwardCommand(request);
                break;
        }
    }


    private void joinGameReceived(int player_id,JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        JSONArray supported_versions = payload.getJSONArray("supported_versions");
        JSONArray supported_features = payload.getJSONArray("supported_features");
        String name = payload.getString("name");
        String [] versions = new String[supported_versions.length()];
        String [] features = new String[supported_features.length()];
        for(int i =0;i<versions.length;i++){
            versions[i] = supported_versions.getString(i);
        }
        for(int i =0;i<features.length;i++){
            features[i] = supported_features.getString(i);
        }
        JoinGame join = new JoinGame(name,features,versions);
        controller.handleAction(join);
    }



    private void setUpReceived(JSONObject JSON){
        int territory_id = JSON.getInt("payload");
        int player_id = JSON.getInt("player_id");
        int ack_id = JSON.getInt("ack_id");
        Setup setup = new Setup(player_id,territory_id,ack_id);
        controller.handleAction(setup);
    }
    private void leaveGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        int response_code = payload.getInt("response");
        String message = payload.getString("message");
        boolean receive_updates = payload.getBoolean("receive_updates");
        int player_id = json.getInt("player_id");
        LeaveGame leave = new LeaveGame(player_id,response_code,message,receive_updates);
        controller.handleAction(leave);
    }

    private void pingReceived(JSONObject json) {
        String numberOfPlayers = json.getString("payload");
        int numberPlayers;
        if(!numberOfPlayers.equals("null")){
            numberPlayers = Integer.parseInt(numberOfPlayers);
        }else{
            numberPlayers = -1;
        }
        int player_id = json.getInt("player_id");
        Ping ping = new Ping(numberPlayers,player_id);
        controller.handleAction(ping);
    }

    private void acknowledgementReceived(JSONObject json) {
        int player_id = json.getInt("player_id");
        int ack_id = json.getInt("payload");
        Acknowledgement ack = new Acknowledgement(ack_id,player_id);
        controller.handleAction(ack);
    }

    private void forwardCommand(JSONObject json) {
        // TODO forward message to all connected clients
    }
}