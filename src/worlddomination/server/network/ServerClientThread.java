package worlddomination.server.network;

import worlddomination.server.controller.ServerController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 120011588 on 31/03/15.
 */
public class ServerClientThread implements Runnable {

    private BlockingQueue<JSONObject> messages;

    protected Socket clientSocket;
    BufferedReader clientMessages;
    BufferedWriter serverMessages;
    private int ackTimeout;
    private int moveTimeout;
    private String waitingResponse;
    private ServerApiManager api;
    public int player_id;

    public ServerClientThread(int player_id,Socket clientSocket, int ackTimeout, int moveTimeout, ServerController controller) {
        this.clientSocket = clientSocket;
        this.ackTimeout =ackTimeout;
        this.moveTimeout = moveTimeout;
        messages = new ArrayBlockingQueue<>(10);
        api = new ServerApiManager(controller);
        this.player_id = player_id;
    }

    public void run() {
        //TODO set playerID from join game message and timeouts
        String clientMessage;
        int timeout = 0;
        try {
            serverMessages = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            clientMessages = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            long timer = 0;
            while (true) {
                if (!messages.isEmpty()) {
                    serverMessages.write(messages.remove().toString());
                    serverMessages.flush();
                    timer = System.nanoTime();
                }
                if(waitingResponse!=null) {
                    if (waitingResponse.equals("ack")) {
                        timeout = ackTimeout;
                    } else {
                        timeout = moveTimeout;
                    }
                }
                //while (System.nanoTime() - timer < timeout) {
                        if(clientMessages.ready() && (clientMessage=clientMessages.readLine())!=null) {
                            System.out.println(clientMessage.toString());
                            JSONObject request = api.parseResponse(clientMessage.toString());
                            api.checkCommandRequest(this.player_id,request);
                    }
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendServerMessage(JSONObject message) {
        messages.add(message);
    }

    public int getPlayerId() {
        return player_id;
    }

    public void setWaitingResponse(String waitingResponse) {
        this.waitingResponse = waitingResponse;
    }

}
