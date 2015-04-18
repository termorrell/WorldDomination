package worlddomination.server.network;

import worlddomination.server.controller.ClientController;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientApiManager implements ApiMethods {
    ClientController controller;

    public ClientApiManager(ClientController controller) {
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

	/**
	 * Receive information from the server in the form of JSON and analyses it
	 * for each type of command
	 * @param json command sent to the client in JSONObject form
	 */
	public void checkCommandRequest(JSONObject json) {
		String command = json.getString("command");
		command.toLowerCase();
		switch (command) {
            case "accept_join_game":
                acceptJoinGameReceived(json);
                break;
            case "reject_join_game":
                rejectJoinGameReceived(json);
                break;
            case "players_joined":
                playersJoinedReceived(json);
                break;
            case "ping":
                pingReceived(json);
                break;
            case "ready":
                readyReceived(json);
                break;
            case "initialise_game":
                initGameReceived(json);
                break;
            case "setup":
                setupReceived(json);
                break;
            case "play_cards":
                playCardsReceived(json);
                break;
            case "deploy":
                deployReceived(json);
                break;
            case "attack":
                attackReceived(json);
                break;
            case "defend":
                defendReceived(json);
                break;
            case "attack_capture":
                attackWonReceived(json);
                break;
            case "fortify":
                fortifyReceived(json);
                break;
            case "acknowledgement":
                acknowledgementReceived(json);
                break;
            case "roll":
                rollReceived(json);
                break;
            case "roll_hash":
                rollHashReceived(json);
                break;
            case "roll_number":
                rollNumberReceived(json);
                break;
            case "leave_game":
                leaveGameReceived(json);
                break;
            case "timeout":
                timeoutReceived(json);
                break;
            default:
                unrecognisedCommand(json);
                break;
        }
	}



	//TODO CALL RELEVANT METHODS
	//todo check correct with rep final protocol


	private void readyReceived(JSONObject json){
		int ack_id = json.getInt("ack_id");
		//TODO checkReady call
	}
	private void rejectJoinGameReceived(JSONObject json){
	String payload = json.getString("payload");

}
	private void playersJoinedReceived(JSONObject json){
		JSONArray players = json.getJSONArray("payload");

	}
	private void acceptJoinGameReceived(JSONObject json){
		JSONObject payload = json.getJSONObject("payload");
		int playerId = payload.getInt("player_id");
		//TODO controller add local player
		int acknowledgement_timeout = payload.getInt("acknowledgement_timeout");
		int move_timeout = payload.getInt("move_timeout");
	}
	private void leaveGameReceived(JSONObject json){
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
	private void attackReceived(JSONObject json) {
		JSONArray payload = json.getJSONArray("payload");
		int source_territory = payload.getInt(0);
		int dest_territory = payload.getInt(1);
		int num_armies = payload.getInt(2);
		int ack_id = json.getInt("ack_id");
		int player_id = json.getInt("player_id");
	}
	private void defendReceived(JSONObject json) {
		int no_armies = json.getInt("payload");
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
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
	private void setupReceived(JSONObject json) {
		int territory_id = json.getInt("payload");
		int ack_id = json.getInt("ack_id");
		int player_id = json.getInt("player_id");
	}
	private void acknowledgementReceived(JSONObject json) {
		int player_id = json.getInt("player_id");
		JSONObject payload = json.getJSONObject("payload");
		int ack_id = payload.getInt("ack_id");
		int response = payload.getInt("response");
		String data = payload.getString("data");

	}
	private void playCardsReceived(JSONObject json) {
		String payload = json.getString("payload");
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
	}
	private void deployReceived(JSONObject json) {
		JSONArray payload = json.getJSONArray("payload");
		int territory_id = payload.getInt(0);
		int num_armies = payload.getInt(1);
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
	}
	private void attackWonReceived(JSONObject json) {
		JSONArray armyMovement = json.getJSONArray("payload");
		int source_territory = armyMovement.getInt(0);
		int dest_territory = armyMovement.getInt(1);
		int num_armies = armyMovement.getInt(2);
		int ack_id = json.getInt("ack_id");
	}
	private void fortifyReceived(JSONObject json) {
		int ack_id = json.getInt("ack_id");
		if(json.getJSONArray("payload")!= null){
			JSONArray armyMovement = json.getJSONArray("payload");
			int source_territory = armyMovement.getInt(0);
			int dest_territory = armyMovement.getInt(1);
			int num_armies = armyMovement.getInt(2);
		}else{
			//TODO?
		}
	}
	private void initGameReceived(JSONObject json){
		JSONObject payload = json.getJSONObject("payload");
		int version = payload.getInt("version");
		JSONArray supported_features = payload.getJSONArray("supported_features");
	}
	private void timeoutReceived(JSONObject json){
		int player_left_id = json.getInt("payload");
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
	}
	private void unrecognisedCommand(JSONObject json){

	}

}
