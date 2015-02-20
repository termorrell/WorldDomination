package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import org.json.JSONObject;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class ServersNetworkListener extends Listener {

    ApiMethods api = new ApiManager();
    private Client client;
    public void init(Client client){
        this.client=client;

    }
    public void connected(Connection connection) {
        Log.info("[Server] Someone is trying to connect.");
        Thread t  = new Thread(client);
        t.run();

    }

    public void disconnected(Connection connection) {
        Log.info("[Server] Someone is trying to disconnect.");
    }

    //Object is thing server has received from client
    public void received(Connection connection, Object object) {
        if(object instanceof NetworkPacket){
            NetworkPacket response = (NetworkPacket)object;
            System.out.println(response.getJsonStringResponse());
            JSONObject message = api.parseResponse(response.getJsonStringResponse().toString());
            // TODO call methods to look at response, parse methods stored in API
            NetworkPacket result = new NetworkPacket();
            result.setJsonStringResponse(message.toString());
            connection.sendTCP(result);
        }
    }
}
