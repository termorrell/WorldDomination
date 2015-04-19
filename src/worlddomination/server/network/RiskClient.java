package worlddomination.server.network;

import worlddomination.server.controller.ClientController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 120011588 on 18/02/15.
 */
public class RiskClient {

    private static BufferedReader serverMessages;
    private static BufferedWriter clientMessages;
    private BlockingQueue<JSONObject> messages;
    private int port;
    private String host;
    private Socket client;
    private ClientApiManager api;

    public RiskClient(int port, String host, ClientController controller){
        this.port = port;
        this.host = host;
        messages =  new ArrayBlockingQueue<JSONObject>(10);
        api =  new ClientApiManager(controller);
        runClient();
    }
    public void runClient(){
        //todo make sure controller assigns playerid
        try {
            client = new Socket(host,port);
            String serverMessage = "";
            clientMessages = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            serverMessages = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(true) {
                if (!messages.isEmpty()) {
                    clientMessages.write(messages.take().toString());
                    clientMessages.flush();
                }
                if(serverMessages.ready()) {
                    if(serverMessages.ready() && (serverMessage = serverMessages.readLine())!=null) {
                        JSONObject request  = api.parseResponse(serverMessage);
                        api.checkCommandRequest(0,request);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendClientMessage(JSONObject message){
        messages.add(message);
    }

    public void closeClient(){
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
