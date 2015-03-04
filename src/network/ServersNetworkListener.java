package network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class ServersNetworkListener extends Listener {


    //todo kick client from game??
    ApiMethods api = new ApiManager();
    private Client client;
    private Queue<Client> clients =  new LinkedList<>();

    public void init(Client client){
        this.client=client;

    }
    public void connected(Connection connection) {
        Log.info("[Server] Someone is trying to connect.");
        Thread t  = new Thread(client);
        clients.add(client);
        t.run();
    }

    public void disconnected(Connection connection) {
        Log.info("[Server] Someone is trying to disconnect.");
    }

    //Object is thing server has received from client
    public void received(Connection connection, Object object) {
        if(object instanceof NetworkPacket){
            NetworkPacket response = (NetworkPacket)object;
            JSONObject message = api.parseResponse(response.getJsonStringResponse().toString());
            JSONObject server_response = api.serverCheckCommandRequest(message);
            connection.sendTCP(server_response);
            // TODO call methods to look at response

        }
    }
}
