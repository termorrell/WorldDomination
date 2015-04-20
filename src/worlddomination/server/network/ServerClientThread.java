package worlddomination.server.network;

import worlddomination.server.controller.ServerController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

    /**
     * Constructor for client thread
     * @param player_id id of the client
     * @param clientSocket socket for connections
     * @param ackTimeout time allowed for acknowledgement to occur
     * @param moveTimeout time allowed for a move to be sent
     * @param controller controller for dealing with messages
     */
    public ServerClientThread(int player_id,Socket clientSocket, int ackTimeout, int moveTimeout, ServerController controller) {
        this.clientSocket = clientSocket;
        this.ackTimeout =ackTimeout;
        this.moveTimeout = moveTimeout;
        messages = new ArrayBlockingQueue<>(10);
        api = new ServerApiManager(controller);
        this.player_id = player_id;
    }

    /**
     * Continuously checks for messages to be sent or read
     */
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
                    String mess = messages.remove().toString();
                    mess += '\n';
                    serverMessages.write(mess);
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
                            System.out.println("Received: " + clientMessage.toString());
                            JSONObject request = api.parseResponse(clientMessage.toString());
                            api.checkCommandRequest(this.player_id,request);
                    }
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a message to the queue to be sent
     * @param message message to be sent
     */
    public void sendServerMessage(JSONObject message) {
        messages.add(message);
    }

    /**
     * Gets id of current thread
     * @return playerid
     */
    public int getPlayerId() {
        return player_id;
    }

    public void setWaitingResponse(String waitingResponse) {
        this.waitingResponse = waitingResponse;
    }

}
