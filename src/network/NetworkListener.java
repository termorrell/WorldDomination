package network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class NetworkListener extends Listener {
    @Override
    public void connected(Connection connection) {
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
    }
}
