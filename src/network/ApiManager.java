package network;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiManager implements ApiMethods {

	public void parseResponse(String response) {
		JSONObject obj = new JSONObject(response);
		receivedRequest(obj);
	}

	/**
	 * Sends a request to server Takes in a string and converts to JSON
	 */
	public void sendRequest(String json) {
		JSONObject obj = new JSONObject(json);

	}

	/**
	 * Receive information from the server in the form of JSON and analyses it
	 * for each type of command
	 */
	public void receivedRequest(JSONObject response) {
		String command = response.getString("command");
		command.toLowerCase();
		JSONArray payload = response.getJSONArray("payload");

		switch (command) {
		case "accept_join_game":
			acceptJoinGameReceived(payload);
			break;
			
		case "ping":
			pingReceived(payload);
			break;

		case "attack":
			attackReceived(payload);
			break;

		case "defend":
			attackReceived(payload);
			break;

		

		case "roll":
			attackReceived(payload);
			break;

		case "roll_hash":
			attackReceived(payload);
			break;

		case "roll_number":
			attackReceived(payload);
			break;
			
		default:
			System.err.println("JSON message couldn't be recognised.");
			break;
		}
	}

	// if (command.equals("attack")) {
	// attackReceived(payload);
	// } else if (command.equals("defend")) {
	// defendReceived(payload);
	// } else if (command.equals("join_game")) {
	// joinGameReceived(payload);
	// } else if (command.equals("roll")) {
	// rollReceived(payload);
	// } else if (command.equals("roll_hash")) {
	// rollHashReceived(payload);
	// } else if (command.equals("roll_number")) {
	// rollNumberReceived(payload);
	// } else if (command.equals("setup")) {
	// setupReceived(payload);
	// } else if (command.equals("acknowledgement")) {
	// acknowledgementReceived(payload);
	// } else if (command.equals("trade_in_cards")) {
	// tradeInReceived(payload);
	// } else if (command.equals("deploy")) {
	// deployReceived(payload);
	// } else if (command.equals("attack_capture")) {
	// attackWonReceived(payload);
	// } else if (command.equals("fortify")) {
	// fortifyReceived(payload);
	// } else if (command.equals("win")) {
	// winReceived(payload);
	// } else {
	// unrecognisedResponse(payload);
	// }

	private void acceptJoinGameReceived(JSONArray json){
		json.getJSONObject(0);
		
	}
	
	private void pingReceived(JSONArray json) {
		String name = "name";
		System.out.println(json);
	}
	
	public void attackReceived(JSONArray json) {

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


	public void attackWonReceived(JSONArray json) {

	}
 
	public void fortifyReceived(JSONArray json) {

	}

	public void winReceived(JSONArray json) {

	}

	public void unrecognisedResponse(JSONArray json) {

	}

}
