package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class NetworkListener extends Listener {

    private Client client;
    public void init(Client client){
        this.client=client;

    }
    public void connected(Connection connection) {
        Log.info("[Server] Someone is trying to connect.");
    }

    public void disconnected(Connection connection) {
        Log.info("[Server] Someone is trying to disconnect.");
    }

    //Object is thing server has received from client
    public void received(Connection connection, Object object) {
        if(object instanceof NetworkPacket){
            NetworkPacket response = (NetworkPacket)object;
            System.out.println(response.getJsonStringResponse());
            NetworkPacket packet = new NetworkPacket();

            packet.setJsonStringResponse("hello");
            connection.sendTCP(packet);
            connection.close();
        }
    }
}
