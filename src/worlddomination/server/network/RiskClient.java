package worlddomination.server.network;

import worlddomination.server.controller.ClientController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class RiskClient implements Runnable{

    private static BufferedReader serverMessages;
    private static BufferedWriter clientMessages;
    private BlockingQueue<JSONObject> messages;
    private int port;
    private String host;
    private Socket client;
    private ClientApiManager api;

    /**
     * Constructor for creating a new client
     * @param port port to connect to
     * @param host ip of host to connect to
     * @param controller controller for handling messages
     */
    public RiskClient(int port, String host, ClientController controller) {
        this.port = port;
        this.host = host;
        messages =  new ArrayBlockingQueue<JSONObject>(10);
        api =  new ClientApiManager(controller);
    }
    
    /**
     * Runs the main loop within the client, for checking for messages to be sent or read
     */
    public void run(){
        //todo make sure controller assigns playerid
        try {
            client = new Socket(host,port);
            String serverMessage = "";
            clientMessages = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            serverMessages = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while(true) {
                if (!messages.isEmpty()) {
                    String mess = messages.remove().toString();
                    mess += '\n';
                    clientMessages.write(mess);
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
        }
    }

    /**
     * Adds a message to the queue to be sent
     * @param message message to be sent
     */
    public void sendClientMessage(JSONObject message){
        messages.add(message);
    }



}
