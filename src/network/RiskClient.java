package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class RiskClient{
    public Client client;

    public RiskClient(){
        client = new Client();
        register();
        ClientsNetworkListener n1 = new ClientsNetworkListener();
        client.addListener(n1);
        new Thread(client).start();
        try {
            client.connect(5000,"127.0.0.1",54555);
        } catch (IOException e) {
           System.err.println("A problem occured connecting to the server.");
        }
    }

    public void sendMessage(JSONObject responseObject) {
        NetworkPacket packet = new NetworkPacket();
        packet.setJsonStringResponse(responseObject.toString());
        client.sendTCP(packet);
    }

    public void register(){
        Kryo kryo = client.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)
        kryo.register(NetworkPacket.class);
        kryo.register(org.json.JSONObject.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(org.json.JSONArray.class);
        kryo.register(java.util.ArrayList.class);

    }


//    public static void main(String[] args){
//        new RiskClient();
//        //Log.set(Log.LEVEL_DEBUG);
//    }

}
