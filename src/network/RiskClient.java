package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class RiskClient {
    public Client client;

    public RiskClient(){
        client = new Client();
        register();
        ClientNetworkListener n1 = new ClientNetworkListener();
        client.addListener(n1);
        new Thread(client).start();
        try {
            client.connect(5000,"127.0.0.1",54555);
            NetworkPacket packet = new NetworkPacket();
            packet.setJsonStringResponse("work!!");
            client.sendTCP(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void register(){
        Kryo kryo = client.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)
        kryo.register(NetworkPacket.class);
    }

    public static void main(String[] args){
        new RiskClient();
        Log.set(Log.LEVEL_DEBUG);
    }
}
