package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import controller.ServerController;
import org.json.JSONObject;

import java.io.IOException;

/**
 * erver server
 * Created by ${mm280} on 18/02/15.
 */
public class RiskServer {

    private Server server;
    private NetworkPacket serverResponse = null;
    ServerController controller;

    public RiskServer() throws IOException {
        serverResponse = new NetworkPacket();
        server = new Server();
        registerPackets();
        server.addListener(new Listener() {
            public void connected(Connection connection) {
                Log.info("[Server] Someone is trying to connect.");
            }

            public void received(Connection connection, Object object) {
                if (object instanceof NetworkPacket) {
                    NetworkPacket clientMessage = (NetworkPacket) object;
                    server.sendToAllExceptTCP(connection.getID(),clientMessage);
                    //TODO CALL CONTROLLER
                    if (serverResponse != null) {
                        server.sendToAllTCP(serverResponse);
                    } else {
                        Log.info("[SERVER] Received null response object from controller");
                    }
                }
            }

            public void disconnected(Connection connection) {
                Log.info("[Server] Someone is trying to disconnect.");
            }
        });
        //todo feed in from gui
        server.bind(54555);
        //TODO TIME OUT
        server.start();


    }

    /**
     * Used by controller to send messages to the server to be sent to clients
     *
     * @param message a JSONObject with message to be sent
     */
    public void sendServerResponse(JSONObject message) {
        serverResponse.setJsonStringResponse(message.toString());
    }

    //Register all of packets that will be sent, packet will be something that
    // will contain a bunch of variables and client will read variables
    private void registerPackets() {
        Kryo kryo = server.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)
        kryo.register(NetworkPacket.class);
        kryo.register(org.json.JSONObject.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(org.json.JSONArray.class);
        kryo.register(java.util.ArrayList.class);

    }

    public void setServerResponse(NetworkPacket serverResponse) {
        this.serverResponse = serverResponse;
    }


    public static void main(String[] args){
        try {
            new RiskServer();
           // Log.set(Log.LEVEL_DEBUG); // Logging utility for server

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
