package network;

import model.Model;
import org.json.JSONObject;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApiManagerTest {

    Model model;
    ApiMethods api;
    @Before
    public void setUp() throws Exception {
        api = new ApiManager();
        model = new Model();
    }

    @Test
    public void checkJoinGameJson(){

        String jsonRequest ="{\n" +
                "\t\"command\": \"join_game\",\n" +
                "\t\"payload\": {\n" +
                "\t\t\"supported_versions\": [1],\n" +
                "\t\t\"supported_features\": [\"custom_map\"]\n" +
                "\t}\n" +
                "}";
        JSONObject obj = api.sendRequest(jsonRequest);
        System.out.println(obj.getString("command"));
        assertTrue(obj.getString("command").equals("join_game"));
    }
}