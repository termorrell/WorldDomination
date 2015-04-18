package worlddomination.server.network;

import worlddomination.server.controller.ServerController;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerApiManager implements ApiMethods{

    ServerController controller;

    public ServerApiManager(ServerController controller) {
        this.controller = controller;
    }

    /**
     * Parses a string into a Json objects
     * @param response the string to be parsed
     * @return a new json object
     */
    public JSONObject parseResponse(String response) {
        JSONObject obj = new JSONObject(response);
        return obj;
    }

    public void checkCommandRequest(JSONObject request) {
        String command = request.getString("command");
        command.toLowerCase();
        switch (command) {
            case "join_game":
                joinGameReceived(request);
                break;
            case "ping":
                pingReceived(request);
                break;
            case "acknowledgement":
                acknowledgementReceived(request);
                break;
            case "roll":
                rollReceived(request);
                break;
            case "roll_hash":
                rollHashReceived(request);
                break;
            case "roll_number":
                rollNumberReceived(request);
                break;
            case "leave_game":
                leaveGameReceived(request);
                break;
            default:
                forwardCommand(request);
                break;
        }
    }

    private void joinGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        JSONArray supported_versions = payload.getJSONArray("supported_versions");
        JSONArray supported_features = payload.getJSONArray("custom_map");
    }


    private void leaveGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        int response_code = payload.getInt("response");
        boolean receive_updates = payload.getBoolean("receive_updates");
        int player_id = json.getInt("player_id");
    }

    private void pingReceived(JSONObject json) {
        int payload = json.getInt("payload");
        int player_id = json.getInt("player_id");
        //TODO controller add player
        // name will hopefully be added in the next representative meeting.
    }

    private void rollReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        int dice_count = payload.getInt("dice_count");
        int dice_faces = payload.getInt("dice_faces");
        int player_id = json.getInt("player_id");
        //TODO CHECK ROLL
    }

    private void rollHashReceived(JSONObject json) {
        String payloadHash = json.getString("payload");
        int player_id = json.getInt("player_id");
        //TODO ROLL HASH
    }

    private void rollNumberReceived(JSONObject json) {
        String payloadHash = json.getString("payload");
        int player_id = json.getInt("player_id");
        //TODO ROLL NUMBER
    }

    private void acknowledgementReceived(JSONObject json) {
        int player_id = json.getInt("player_id");
        JSONObject payload = json.getJSONObject("payload");
        int ack_id = payload.getInt("ack_id");
        int response = payload.getInt("response");
        String data = payload.getString("data");

    }

    private void forwardCommand(JSONObject json) {
        // TODO forward message to all connected clients
    }
}