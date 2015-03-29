package network;

import model.Model;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiManagerTest {

    Model model;
    ApiMethods api;
    String jsonJoinRequest;

    @Before
    public void setUp() throws Exception {
       // api = new ClientApiManager();
        model = new Model();
        jsonJoinRequest ="{\n" +
                "\t\"command\": \"join_game\",\n" +
                "\t\"payload\": {\n" +
                "\t\t\"supported_versions\": [1],\n" +
                "\t\t\"supported_features\": [\"custom_map\"]\n" +
                "\t}\n" +
                "}";

    }

    @Test
    public void checkParseJsonCorrectly(){


        JSONObject obj = api.parseResponse(jsonJoinRequest);
        JSONObject b = obj.getJSONObject("payload");

        assertEquals(obj.getString("command"), "join_game");
        assertEquals(b.getJSONArray("supported_versions").getInt(0), 1);
    }

}