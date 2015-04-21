package worlddomination.server.network;

import worlddomination.server.actions.*;
import worlddomination.server.controller.ServerController;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServerApiManager implements ApiMethods{

    ServerController controller;
    ServerResponseGenerator ackGen = new ServerResponseGenerator();

    public ServerApiManager(ServerController controller) {
        this.controller = controller;
    }

    /**
     * Parses a string into a Json objects
     * @param response the string to be parsed
     * @return a new json object
     */
    public JSONObject parseResponse(String response) {
        JSONObject res = new JSONObject(response);
        String obj = res.getString("message").replaceAll("\\\"", "\"");
        return new JSONObject(obj);
    }

    public void checkCommandRequest(int player_id, JSONObject request) {
        String command = request.getString("command");
        int ack_id;
        JSONObject forwardResponse;
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
            	forwardResponse = revertToMessage(request);
                ack_id = request.getInt("ack_id");
                controller.server.sendToOne(player_id,ackGen.ackGenerator(ack_id,player_id));
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            case "roll_hash":
            	forwardResponse = revertToMessage(request);
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            case "roll_number":
            	forwardResponse = revertToMessage(request);
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            case "leave_game":
                leaveGameReceived(request);
                break;
            case "setup":
            	forwardResponse = revertToMessage(request);
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
            	break;
            case "play_cards":
                ack_id = request.getInt("ack_id");
            	forwardResponse = revertToMessage(request);
                controller.server.sendToOne(player_id,ackGen.ackGenerator(ack_id,player_id));
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            case "deploy":
                ack_id = request.getInt("ack_id");
            	forwardResponse = revertToMessage(request);
                controller.server.sendToOne(player_id,ackGen.ackGenerator(ack_id,player_id));
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            case "attack":
                ack_id = request.getInt("ack_id");
            	forwardResponse = revertToMessage(request);
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                controller.server.sendToOne(player_id,ackGen.ackGenerator(ack_id,player_id));
                break;
            case "defend":
                ack_id = request.getInt("ack_id");
            	forwardResponse = revertToMessage(request);
                controller.server.sendToOne(player_id,ackGen.ackGenerator(ack_id,player_id));
                controller.server.sendMessageToAllExceptSender(player_id,forwardResponse);
                break;
            default:
            	forwardResponse = revertToMessage(request);
                controller.server.sendMessageToAllExceptSender(player_id, forwardResponse);
                break;
        }
    }

    private JSONObject revertToMessage(JSONObject json){
    	JSONObject message = new JSONObject();
    	String messageString = json.toString();
    	message.put("message",messageString);
    	return message;
    }

    private void joinGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        JSONArray supported_versions = payload.getJSONArray("supported_versions");
        JSONArray supported_features = payload.getJSONArray("supported_features");
        String name = payload.getString("name");
        float [] versions = new float[supported_versions.length()];
        String [] features = new String[supported_features.length()];
        for(int i =0;i<versions.length;i++){
            versions[i] = (float)supported_versions.getDouble(i);
        }
        for(int i =0;i<features.length;i++){
            features[i] = supported_features.getString(i);
        }
        JoinGame join = new JoinGame(name,versions,features);
        controller.handleAction(join);
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
        int numberPlayers;
        if(!json.isNull("payload")){
            numberPlayers = json.getInt("payload");
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

}