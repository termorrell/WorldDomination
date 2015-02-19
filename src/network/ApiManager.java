package network;

import org.json.JSONObject;

public class ApiManager implements ApiMethods {

	public void parseResponse(String response) {
		JSONObject obj = new JSONObject(response);
		receivedRequest(obj);
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
	public void receivedRequest(JSONObject response) {
		String command = response.getString("command");
		command.toLowerCase();
		switch (command) {
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
				attackReceived(response);
				break;
			case "roll":
				attackReceived(response);
				break;
			case "roll_hash":
				attackReceived(response);
				break;
			case "roll_number":
				attackReceived(response);
				break;
			case "ready":
				//
				break;
			case "acknowledgement":
				acknowledgementReceived(response);
				break;
			case "setup":
				setupReceived(response);
				break;
			case "trade_in_cards":
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
			default:
				System.err.println("JSON message couldn't be recognised.");
				break;
		}
	}

	private void acceptJoinGameReceived(JSONObject json){

		
	}
	
	private void pingReceived(JSONObject json) {
		String name = "name";
	}
	
	public void attackReceived(JSONObject json) {

	}

	public void defendReceived(JSONObject json) {

	}

	public void joinGameReceived(JSONObject json) {
		System.out.println("join");
	}

	public void rollReceived(JSONObject json) {

	}

	public void rollHashReceived(JSONObject json) {

	}

	public void rollNumberReceived(JSONObject json) {

	}

	public void setupReceived(JSONObject json) {

	}

	public void acknowledgementReceived(JSONObject json) {

	}

	public void tradeInReceived(JSONObject json) {

	}

	public void deployReceived(JSONObject json) {

	}


	public void attackWonReceived(JSONObject json) {

	}
 
	public void fortifyReceived(JSONObject json) {

	}

	public void winReceived(JSONObject json) {

	}

	public void unrecognisedResponse(JSONObject json) {

	}

}
