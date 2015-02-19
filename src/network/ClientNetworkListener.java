package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class ClientNetworkListener extends Listener{

    ApiMethods api = new ApiManager();
    public void connected(Connection connection) {
        Log.info("[Client] Someone is trying to connect.");
    }

    public void disconnected(Connection connection) {
        Log.info("[Client] Someone is trying to disconnect.");
    }

    //Object is thing server has received from client
    public void received(Connection connection, Object object) {
        if(object instanceof NetworkPacket){
            NetworkPacket response = (NetworkPacket)object;
            // TODO call methods to look at response, parse methods stored in API
            api.parseResponse(response.getJsonStringResponse());
            System.out.println(response.getJsonStringResponse());
        }
    }
}

