package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class RiskClient {
    public Client client;

    private NetworkPacket clientResponse;

    public RiskClient() {
        clientResponse = new NetworkPacket();
        client = new Client();
        register();
        client.addListener(new Listener() {
            public void connected(Connection connection) {
                Log.info("[Client] Connecting.");
            }

            public void disconnected(Connection connection) {
                Log.info("[Client] Disconnected.");
            }

            public void received(Connection connection, Object object) {
                if (object instanceof NetworkPacket) {
                    NetworkPacket serverMessage = (NetworkPacket) object;
                    //TODO CALL CONTROLLER TO UPDATE CLIENT RESPONSE
                }
            }
        });

        new Thread(client).start();
        try {
            //TODO FEED IN FROM GUI
            client.connect(5000, "127.0.0.1", 54555);
        } catch (IOException e) {
            System.err.println("A problem occured connecting to the server.");
        }
    }

    public void sendMessage(String content) {
        NetworkPacket packet = new NetworkPacket();
        packet.setJsonStringResponse(content);
        client.sendTCP(packet);
    }

    public void register() {
        Kryo kryo = client.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)
        kryo.register(NetworkPacket.class);
        kryo.register(org.json.JSONObject.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(org.json.JSONArray.class);
        kryo.register(java.util.ArrayList.class);
    }

}
