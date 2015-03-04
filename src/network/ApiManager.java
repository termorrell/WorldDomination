package network;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager implements ApiMethods {

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
	 */
	public JSONObject clientCheckCommandRequest(JSONObject response) {
		String command = response.getString("command");
		command.toLowerCase();
		switch (command) {
			case "accept_join_game":
				acceptJoinGameReceived(response);
				break;
			case "reject_join_game":

				break;
			case "players_joined":

				break;
			case "ping":
				pingReceived(response);
				break;
			case "ready":
				readyReceived(response);
				break;
			case "initialise_game":

				break;
			case "roll":
				rollReceived(response);
				break;
			case "timeout":

				break;
			default:
				System.err.println("JSON message couldn't be recognised.");
				break;
		}
		return null;
	}

	public JSONObject serverCheckCommandRequest(JSONObject request) {
		String command = request.getString("command");
		JSONObject response = new JSONObject();
		command.toLowerCase();
		switch (command){
			case "join_game":
				joinGameReceived(request);
				return response;
			case "ping":
				pingReceived(request);
				break;
			case "setup":
				setupReceived(request);
				break;
			case "play_cards":
				playCardsReceived(request);
				break;
			case "deploy":
				deployReceived(request);
				break;
			case "attack":
				attackReceived(request);
				break;
			case "defend":
				defendReceived(request);
				break;
			case "attack_capture":
				attackWonReceived(request);
				break;
			case "fortify":
				fortifyReceived(request);
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

				break;
			default:
				System.err.println("Unrecognised message");
				break;
		}
		return null;
	}

	//TODO CALL RELEVANT METHODS
	//todo check correct with rep final protocol

	private void joinGameReceived(JSONObject json){
		JSONObject payload = json.getJSONObject("payload");
		JSONArray supported_versions = payload.getJSONArray("supported_versions");
		JSONArray supported_features = payload.getJSONArray("custom_map");
		//TODO PROPER PLAYER ID ETC
		JSONObject server_response = ServerResponseGenerator.acceptJoinGameGenerator(0,0,0);
		
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
		int no_armies = json.getInt("payload");
		int player_id = json.getInt("player_id");
		int ack_id = json.getInt("ack_id");
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

	public void playCardsReceived(JSONObject json) {
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
