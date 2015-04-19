package worlddomination.server.network;

import worlddomination.server.controller.ServerController;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by 120011588 on 18/02/15.
 */
public class RiskServer {

    public static ArrayList<ServerClientThread> clientThreads =  new ArrayList<ServerClientThread>();
    private int port;
    private ServerSocket ssocket;
    private int ackTimeout;
    private int moveTimeout;
    private ServerController controller;

    public RiskServer(int port, int ackTimeout, int moveTimeout, ServerController controller){
        this.port = port;
        this.ackTimeout = ackTimeout;
        this.moveTimeout = moveTimeout;
        this.controller = controller;
        runServer();
    }
    private void runServer(){
        try {
            int i=0;
            ssocket = new ServerSocket(port);
            System.out.println("listening");
            while(true){
                Socket socket = ssocket.accept();
                ServerClientThread client = new ServerClientThread(i,socket, ackTimeout,moveTimeout, controller);
                clientThreads.add(client);
                Thread t = new Thread(client);
                t.start();
                System.out.println("connected");
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeServer(){
        try {
            ssocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessageToAll(JSONObject message){
        System.out.println(clientThreads.size());
        for (ServerClientThread clientThread : clientThreads) {
            clientThread.sendServerMessage(message);
        }
    }
    public static void sendMessageToAllExceptSender(int playerId, JSONObject message){
        for (ServerClientThread clientThread : clientThreads) {
            if (clientThread.getPlayerId() != playerId) {
                clientThread.sendServerMessage(message);
            }
        }
    }
    public void sendToOne(int playerId, JSONObject message){
        for (ServerClientThread clientThread : clientThreads) {
            if (clientThread.getPlayerId() == playerId) {
                clientThread.sendServerMessage(message);
            }
        }
    }
}
