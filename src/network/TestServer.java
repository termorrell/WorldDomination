package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.minlog.Log;
import com.esotericsoftware.kryonet.*;

import java.io.IOException;

/**erver server
 * Created by ${mm280} on 18/02/15.
 */
public class TestServer {

    private Server server;

    public TestServer() throws IOException{
        server = new Server();
        registerPackets();
        server.addListener(new NetworkListener());
        server.bind(1234);
        server.start();

    }

    //Register all of packets that will be sent, packet will be something that
    // will contain a bunch of variables and client will read variables
    private void registerPackets(){
        Kryo kryo = server.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)

    }

    public static void main(String[] args){
        try {
            new TestServer();
            Log.set(Log.LEVEL_DEBUG); // Logging utility for server

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
