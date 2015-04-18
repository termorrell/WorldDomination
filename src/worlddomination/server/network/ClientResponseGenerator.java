package worlddomination.server.network;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by ${mm280} on 04/03/15.
 */
public class ClientResponseGenerator {

    RiskClient connection;

    public ClientResponseGenerator(RiskClient connection) {
        this.connection = connection;
    }

    /**
     * Sent by a client to a host attempting to join a game.
     * First command sent upon opening a socket connection to a host.
     * Generates the relevant json for the command.
     * @param supported_versions an array of floats specifying the API versions supported by the client.
     *                           At least one version must be specified.
     * @param supported_features an array of strings representing extension features supported by the client.
     *                           Extra features are optional, and an empty array may be passed.
     * @param name optional, string specifying the real name of the player.
     * @return json object for join_game command
     */
    public void joinGameGenerator(float[] supported_versions, String[] supported_features,String name){
        JSONObject response = new JSONObject();
        response.put("command","join_game");
        JSONObject payload = new JSONObject();
        JSONArray supported_version = new JSONArray(supported_versions);
        JSONArray supported_feature = new JSONArray(supported_features);
        payload.put("supported_versions",supported_version);
        payload.put("supported_features",supported_feature);
        payload.put("name",name);
        response.put("payload",payload);
        connection.sendMessage(response.toString());
    }

    /**
     * Sent by a host at the start of a game, and by all clients in response to this initial ping.
     * "player_id" may be null in the case of a non-player host.
     * Generates the JSONObject for the ping command
     * @param players_joined the total number of players who have joined the game, when sent by the host.
     * @param player_id May be null in the case of a non-player host.
     */
    public JSONObject pingGenerator(int players_joined, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","ping");
        response.put("payload","null");
        response.put("player_id", player_id);
        return response;
    }

    /**
     *  Sent by each player in turn at the start of the game to claim a territory or reinforce an owned territory (once all have been claimed).
     * Method generates the setup json
     * @param player_id id of player sending message
     * @param territory_id territory ID being claimed/reinforced
     * @param ack_id id of acknowledgement
     * @return setup command jsonObject
     */
    public JSONObject setupGenerator(int player_id, int territory_id, int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","setup");
        response.put("payload",territory_id);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent by each player at the start of their turn, specifying group(s) of cards to trade in for armies, and the number of armies they are expecting to receive.
     * This command must always be sent at the start of a turn, even if no cards are being traded.
     * Method generates the jsonObject for the play cards command
     * @param card_ids 2D array of integer card IDs being traded in (grouped into the sets of three being traded)
     * @param armies integer number of armies the player expects to receive from this trade
     * @param player_id id of player sending message
     * @param ack_id id of the acknowledgment message
     * @return JSONOBject for play cards command
     */
    public JSONObject playCardsGenerator(int[][] card_ids, int armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","play_cards");
        JSONObject payload = new JSONObject();
        JSONArray card = new JSONArray(card_ids);
        payload.put("cards",card);
        payload.put("armies",armies);
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent by a player during a turn after their final “attack” has been completed, if any territories have been claimed.
     * Generates a JSONObject for the draw cards command
     * @param card_id the ID of the card being drawn
     * @param player_id id of the player
     * @param ack_id id of the acknowledgment
     * @return JSONObject for draw cards command
     */
    public JSONObject drawCardsGenerator(int card_id, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","draw_card");
        response.put("payload",card_id);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent by a player at the start of their turn after the “play_cards” command.
     * Generates the deploy JSONObject
     * @param armies 2D int array of territory ID/army count pairs
     * @param player_id id of player
     * @param ack_id id of acknowledgment
     * @return JSONObject for deploy
     */
    public JSONObject deployGenerator(int[][]armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","deploy");
        JSONArray payload = new JSONArray(armies);
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent zero or more times by a player during their turn.
     * Describes an attack from an owned territory to one owned by another player.
     * Specifies the ID of the territory to attack from, the ID to attack and the number of armies to attack with.
     * Generates the JSONObject for the attack command
     * @param attack int array, source territory ID/destination territory ID/army count triple
     * @param player_id id of the player
     * @param ack_id id of the acknowledgement
     * @return attack command JSONObject
     */
    public JSONObject attackGenerator(int[] attack, int player_id, int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(attack);
        response.put("command","attack");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent by the defending player immediately following an “attack” command.
     * Specifies the number of armies being used to defend with.
     * Army count must be 1 or 2, and the territory must contain at least the same number of armies as is being used to defend.
     * @param armies_no number of armies being used to defend the territory
     * @param player_id player id
     * @param ack_id id of the acknowledgement
     * @return JSONObject for defend command
     */
    public JSONObject defendGenerator(int armies_no,int player_id,int ack_id){
        JSONObject response = new JSONObject();
        response.put("command","defend");
        response.put("payload", armies_no);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     *Sent by an attacking player upon capturing an opposing territory.
     * Specifies how many armies will be moved into the captured territory.
     * @param attack int array, source territory ID/destination territory ID/army count triple
     * @param player_id the id of the player sending the message
     * @param ack_id the id of the acknowledgement
     * @return JSONObject for the attack capture command
     */
    public JSONObject attackCaptureGenerator(int[] attack,int player_id,int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(attack);
        response.put("command","attack_capture");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     * Sent by a player to signify the end of their turn.
     * Optionally fortifying a single territory by moving a number of armies into it from another.
     * @param armies int array, source territory ID/destination territory ID/army count triple (when fortifying)
     *               null (when ending the turn without fortifying)
     * @param player_id the id of the player sending the message
     * @param ack_id the id of the acknowledgement
     * @return JSONObject for the fortify command
     */
    public JSONObject fortifyGenerator(int[] armies, int player_id,int ack_id){
        JSONObject response = new JSONObject();
        JSONArray payload = new JSONArray(armies);
        response.put("command","fortify");
        response.put("payload",payload);
        response.put("player_id",player_id);
        response.put("ack_id",ack_id);
        return response;
    }

    /**
     *Sent by each player in response to any command specifying an “ack_id” being received.
     * @param ack_id the id of the acknowledgement
     * @param response_id integer response code (as defined below) specifying either agreement or an error with the received command.
     *                    Default is 0 (no error).
     * @param player_id the id of the player sending the message
     * @return JSONObject for the acknowledgement command
     */
    public JSONObject ackGenerator(int ack_id, int player_id){
        // TODO not up to date
        JSONObject response = new JSONObject();
        JSONObject payload = new JSONObject();
        payload.put("ack_id",ack_id);
       // payload.put("response",response_id);
        response.put("command","acknowledgement");
        response.put("payload",payload);
        response.put("player_id",player_id);
        return response;
    }

    /**
     * Sent by the host/a player to request a dice roll for a number of dice with a number of faces.
     * The roll(s) require input from all players to avoid any player being able to influence the game.
     * "player_id" may be null in the case of a non-player host requesting a dice roll to determine the starting player.
     * @param dice_count number of dice to roll
     * @param dice_faces number of faces on each die
     * @param player_id the id of the player sending the message
     * @return JSONObject for the roll generator command
     */
    public JSONObject rollGenerator(int dice_count,int dice_faces,int player_id){
        JSONObject response = new JSONObject();
        JSONObject payload = new JSONObject();
        payload.put("dice_count",dice_count);
        payload.put("dice_faces",dice_faces);
        response.put("command","roll");
        response.put("payload",payload);
        response.put("player_id",player_id);
        return response;
    }

    /**
     * Sent by each player in receipt of a “roll” command being received.
     * The hash should be a string representation of the SHA-256 hash in hexadecimal.
     * @param sha SHA-256 hash as a hexadecimal string
     * @param player_id the id of the player sending the message
     * @return JSONObject for the roll hash command
     */
    public JSONObject rollHashGenerator(String sha, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","roll_hash");
        response.put("payload",sha);
        response.put("player_id",player_id);
        return response;
    }

    /**
     *Sent by each player following the receipt of a “roll_hash” being received from all other players.
     * The payload should correspond to the previously sent hash.
     * @param sha 256-bit random number as a hexadecimal string
     * @param player_id the id of the player sending the message
     * @return JSONObject for the roll number command
     */
    public JSONObject rollNumberGenerator(String sha, int player_id){
        JSONObject response = new JSONObject();
        response.put("command","roll_number");
        response.put("payload",sha);
        response.put("player_id",player_id);
        return response;
    }
}
