package worlddomination.server.network;

import worlddomination.server.actions.*;
import worlddomination.server.controller.ClientController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClientApiManager implements ApiMethods {
    ClientController controller;

    public ClientApiManager(ClientController controller) {
        this.controller = controller;
    }

    /**
     * Parses a string into a Json objects
     *
     * @param response the string to be parsed
     * @return a new json object
     */
    public JSONObject parseResponse(String response) {
        JSONObject res = new JSONObject(response);
        String obj = res.getString("message").replaceAll("\\\"", "\"");
        return new JSONObject(obj);
    }

    /**
     * Receive information from the server in the form of JSON and analyses it
     * for each type of command
     *
     * @param json command sent to the client in JSONObject form
     */
    public void checkCommandRequest(int player_id, JSONObject json) {
        String command = json.getString("command");
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
                //todo ask caroline
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

    private void readyReceived(JSONObject json) {
        int ack_id = json.getInt("ack_id");
        int player_id = -1;
        if (!json.optString("player_id").equals("null")) {
            player_id = json.getInt("player_id");
        }
        Ready ready = new Ready(player_id, ack_id);
        controller.handleAction(ready);
        //TODO: Nothing happens
        
    }

    private void rejectJoinGameReceived(JSONObject json) {
        String payload = json.getString("payload");
        RejectJoinGame reject = new RejectJoinGame(payload);
        controller.handleAction(reject);
    }

    private void playersJoinedReceived(JSONObject json) {
    	System.out.println(json.toString());
        JSONArray players = json.getJSONArray("payload");
        Map<Integer, String[]> playersJoined = new HashMap<>();
        for (int i = 0; i < players.length(); i++) {
            String[] playerInfo = new String[2];
            playerInfo[0] = players.getJSONArray(i).getString(1);
            playerInfo[1] = "";
            playersJoined.put(players.getJSONArray(i).getInt(0), playerInfo);
        }
        PlayersJoined joined = new PlayersJoined(playersJoined);
        controller.handleAction(joined);
    }

    private void playCardsReceived(JSONObject json) {
        int armies = -1;
        JSONArray cards = null;
        int[][] cardsTraded;
        JSONObject payload;
        int numberOfCardsTraded = 0;
        int player_id = json.getInt("player_id");
        int ack_id = json.getInt("ack_id");

        if (json.optJSONObject("payload") != null) {
            payload = json.getJSONObject("payload");
            cards = payload.getJSONArray("cards");
            armies = payload.getInt("armies");
        }
        if (cards != null) {
            numberOfCardsTraded = cards.length();
            cardsTraded = new int[numberOfCardsTraded][numberOfCardsTraded];
            for (int i = 0; i < numberOfCardsTraded; i++) {
                JSONArray innerArray = cards.getJSONArray(i);
                for (int y = 0; y < cards.length(); y++) {
                    cardsTraded[i][y] = innerArray.getInt(y);
                }
            }
        } else {
            cardsTraded = new int[1][3];
            cardsTraded[0][0] = -1;
            cardsTraded[0][1] = -1;
            cardsTraded[0][2] = -1;
        }
        PlayCards play = new PlayCards(numberOfCardsTraded, cardsTraded, armies, player_id, ack_id);
        controller.handleAction(play);
    }

    private void acceptJoinGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        int playerId = payload.getInt("player_id");
        int acknowledgement_timeout = payload.getInt("acknowledgement_timeout");
        int move_timeout = payload.getInt("move_timeout");
        AcceptJoinGame joinGame = new AcceptJoinGame(playerId, acknowledgement_timeout, move_timeout);
        controller.handleAction(joinGame);

    }

    private void leaveGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        int response_code = payload.getInt("response");
        boolean receive_updates = payload.getBoolean("receive_updates");
        String message = payload.getString("message");
        int player_id = json.getInt("player_id");
        LeaveGame leave = new LeaveGame(player_id, response_code, message, receive_updates);
        controller.handleAction(leave);
    }

    private void pingReceived(JSONObject json) {
        int numberPlayers = 0;
        if(!json.isNull("payload")){
            numberPlayers = json.getInt("payload");
        }
        int player_id = 0;
        if (!json.isNull("player_id")) {
            player_id = json.getInt("player_id");
        }
        System.out.println("Ping sent with:" +numberPlayers +"   " + player_id);
        Ping ping = new Ping(numberPlayers,player_id);
        controller.handleAction(ping);
    }

    private void attackReceived(JSONObject json) {
        JSONArray payload = json.getJSONArray("payload");
        int source_territory = payload.getInt(0);
        int dest_territory = payload.getInt(1);
        int num_armies = payload.getInt(2);
        int ack_id = json.getInt("ack_id");
        int player_id = json.getInt("player_id");
        Attack attack = new Attack(source_territory, dest_territory, num_armies, player_id, ack_id);
        controller.handleAction(attack);
    }

    private void defendReceived(JSONObject json) {
        int no_armies = json.getInt("payload");
        int player_id = json.getInt("player_id");
        int ack_id = json.getInt("ack_id");
        Defend defend = new Defend(no_armies, player_id, ack_id);
        controller.handleAction(defend);
    }

    private void rollHashReceived(JSONObject json) {
        String payloadHash = json.getString("payload");
        int player_id = json.getInt("player_id");
        RollHash hash = new RollHash(player_id, payloadHash);
        controller.handleAction(hash);
    }

    private void rollNumberReceived(JSONObject json) {
        String payloadHash = json.getString("payload");
        int player_id = json.getInt("player_id");
        RollNumber number = new RollNumber(player_id, payloadHash);
        controller.handleAction(number);
    }

    private void setupReceived(JSONObject json) {
        int territory_id = json.getInt("payload");
        int ack_id = json.getInt("ack_id");
        int player_id = json.getInt("player_id");
        Setup setup = new Setup(player_id, territory_id, ack_id);
        controller.handleAction(setup);
    }

    private void deployReceived(JSONObject json) {
        JSONArray payload = json.getJSONArray("payload");
        int[][] deployMove;
        deployMove = new int[payload.length()][payload.length()];
        for (int i = 0; i < payload.length(); i++) {
            JSONArray innerArray = payload.getJSONArray(i);
            for (int y = 0; y < payload.length(); y++) {
                deployMove[i][y] = innerArray.getInt(y);
            }
        }
        int player_id = json.getInt("player_id");
        int ack_id = json.getInt("ack_id");
        Deploy deploy = new Deploy(deployMove, player_id, ack_id);
        controller.handleAction(deploy);
    }

    private void attackWonReceived(JSONObject json) {
        JSONArray armyMovement = json.getJSONArray("payload");
        int source_territory = armyMovement.getInt(0);
        int dest_territory = armyMovement.getInt(1);
        int num_armies = armyMovement.getInt(2);
        int ack_id = json.getInt("ack_id");
        int player_id = json.getInt("player_id");
        Attack attack = new Attack(source_territory, dest_territory, num_armies, player_id, ack_id);
        controller.handleAction(attack);
    }

    private void fortifyReceived(JSONObject json) {
        int ack_id = json.getInt("ack_id");
        int player_id = json.getInt("player_id");
        int source_territory = -1;
        int dest_territory = -1;
        int num_armies = -1;
        if (json.optJSONArray("payload") != null) {
            JSONArray armyMovement = json.getJSONArray("payload");
            source_territory = armyMovement.getInt(0);
            dest_territory = armyMovement.getInt(1);
            num_armies = armyMovement.getInt(2);
        }
        Fortify fortify = new Fortify(source_territory, dest_territory, num_armies, player_id, ack_id);
        controller.handleAction(fortify);
    }

    private void initGameReceived(JSONObject json) {
        JSONObject payload = json.getJSONObject("payload");
        double version = payload.getDouble("version");
        JSONArray supported_features = payload.getJSONArray("supported_features");
        String[] features = new String[supported_features.length()];
        for (int y = 0; y < features.length; y++) {
            features[y] = supported_features.getString(y);
        }
        InitialiseGame initGame = new InitialiseGame(version, features);
        controller.handleAction(initGame);
    }

    private void timeoutReceived(JSONObject json) {
        int player_left_id = json.getInt("payload");
        int ack_id = json.getInt("ack_id");
        Timeout timeout = new Timeout(ack_id, player_left_id);
        controller.handleAction(timeout);
    }

    private void unrecognisedCommand(JSONObject json) {

    }

}
