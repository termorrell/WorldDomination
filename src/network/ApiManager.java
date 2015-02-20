package network;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager implements ApiMethods {

	public JSONObject parseResponse(String response) {
		JSONObject obj = new JSONObject(response);
		JSONObject reply;
		reply = receivedRequest(obj);
		return reply;
	}

	/**
	 * Sends a request to server Takes in a string and converts to JSON
	 * @return 
	 */
	public JSONObject sendRequest(String json) {
		JSONObject obj = new JSONObject(json);
		return obj;
	}

	/**
	 * Receive information from the server in the form of JSON and analyses it
	 * for each type of command
	 */
	public JSONObject receivedRequest(JSONObject response) {
		JSONObject reply;
		String command = response.getString("command");
		command.toLowerCase();
		switch (command) {
			case "join_game":
				reply = joinGameReceived(response);
				return reply;
			case "accept_join_game":
				acceptJoinGameReceived(response);
				break;
			case "ping":
				pingReceived(response);
				break;
			case "attack":
				attackReceived(response);
				break;
			case "defend":
				defendReceived(response);
				break;
			case "roll":
				rollReceived(response);
				break;
			case "roll_hash":
				rollHashReceived(response);
				break;
			case "roll_number":
				rollNumberReceived(response);
				break;
			case "ready":
				readyReceived(response);
				break;
			case "acknowledgement":
				acknowledgementReceived(response);
				break;
			case "setup":
				setupReceived(response);
				break;
			case "play_cards":
				tradeInReceived(response);
				break;
			case "deploy":
				deployReceived(response);
				break;
			case "attack_capture":
				attackWonReceived(response);
				break;
			case "fortify":
				fortifyReceived(response);
				break;
			case "win":
				winReceived(response);
				break;
			default:
				System.err.println("JSON message couldn't be recognised.");
				break;
		}
		return null;
	}

	//TODO CALL RELEVANT METHODS
	//todo check correct with rep final protocol

	private JSONObject joinGameReceived(JSONObject json){
		JSONObject respond;
		String accept = "{\n" +
				"\t\"command\": \"join_game\",\n" +
				"\t\"payload\": {\n" +
				"\t\t\"supported_versions\": [1],\n" +
				"\t\t\"supported_features\": [\"custom_map\"]\n" +
				"\t}\n" +
				"}";
		respond  = sendRequest(accept);
		return respond;
	}
	private void readyReceived(JSONObject json){
		int ack_id = json.getInt("ack_id");
		//TODO checkReady call
	}

	private void acceptJoinGameReceived(JSONObject json){
		JSONObject payload = json.getJSONObject("payload");
		int playerId = payload.getInt("player_id");
		//TODO controller add local player
		int acknowlegement_timeout = payload.getInt("acknowlegement_timeout");
		int move_timeout = payload.getInt("move_timeout");
	}
	
	private void pingReceived(JSONObject json) {
		int payload = json.getInt("payload");
		int player_id = json.getInt("player_id");
		//TODO controller add player
		// name will hopefully be added in the next representative meeting.
	}

	public void attackReceived(JSONObject json) {
		JSONArray payload = json.getJSONArray("payload");
		int source_territory = payload.getInt(0);
		int dest_territory = payload.getInt(1);
		int num_armies = payload.getInt(2);
		int ack_id = json.getInt("ack_id");
		int player_id = json.getInt("player_id");
	}

	public void defendReceived(JSONObject json) {

	}

	public void rollReceived(JSONObject json) {
		JSONObject payload = json.getJSONObject("payload");
		int dice_count = payload.getInt("dice_count");
		int dice_faces = payload.getInt("dice_faces");
		int player_id = json.getInt("player_id");
		//TODO CHECK ROLL
	}

	public void rollHashReceived(JSONObject json) {
		String payloadHash = json.getString("payload");
		int player_id = json.getInt("player_id");
		//TODO ROLL HASH
	}

	public void rollNumberReceived(JSONObject json) {
		String payloadHash = json.getString("payload");
		int player_id = json.getInt("player_id");
		//TODO ROLL NUMBER
	}

	public void setupReceived(JSONObject json) {
		int territory_id = json.getInt("payload");
		int ack_id = json.getInt("ack_id");
		int player_id = json.getInt("player_id");
	}

	public void acknowledgementReceived(JSONObject json) {
		int player_id = json.getInt("player_id");
		JSONObject payload = json.getJSONObject("payload");
		int ack_id = payload.getInt("ack_id");
		int response = payload.getInt("response");
		String data = payload.getString("data");

	}

	public void tradeInReceived(JSONObject json) {
		String payload = json.getString("payload");
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
	}

	public void deployReceived(JSONObject json) {
		JSONArray payload = json.getJSONArray("payload");
		int territory_id = payload.getInt(0);
		int num_armies = payload.getInt(1);
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
	}

	public void attackWonReceived(JSONObject json) {
		JSONArray armyMovement = json.getJSONArray("payload");
		int source_territory = armyMovement.getInt(0);
		int dest_territory = armyMovement.getInt(1);
		int num_armies = armyMovement.getInt(2);
		int ack_id = json.getInt("ack_id");
	}

	public void fortifyReceived(JSONObject json) {
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

	public void winReceived(JSONObject json) {

	}

}
