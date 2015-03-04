package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class ClientsNetworkListener extends Listener{


    public void connected(Connection connection) {
        Log.info("[Client] Connecting.");
    }

    public void disconnected(Connection connection) {
        Log.info("[Client] Disconnected.");
    }

    //Object is thing server has received from client
    public void received(Connection connection, Object object) {
        if(object instanceof NetworkPacket){
            NetworkPacket response = (NetworkPacket)object;

        }
    }
}

