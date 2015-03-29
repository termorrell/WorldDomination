package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**erver server
 * Created by ${mm280} on 18/02/15.
 */
public class RiskServer {

    private Server server;

    public RiskServer() throws IOException{
        server = new Server();
        registerPackets();
        //todo controller?
        //server.addListener(new ServersNetworkListener());
        server.bind(54555);
        //TODO TIME OUT
        server.start();
    }

    //Register all of packets that will be sent, packet will be something that
    // will contain a bunch of variables and client will read variables
    private void registerPackets(){
        Kryo kryo = server.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)
        kryo.register(NetworkPacket.class);
        kryo.register(org.json.JSONObject.class);
        kryo.register(java.util.HashMap.class);
        kryo.register(org.json.JSONArray.class);
        kryo.register(java.util.ArrayList.class);

    }


//    public static void main(String[] args){
//        try {
//            new RiskServer();
//           // Log.set(Log.LEVEL_DEBUG); // Logging utility for server
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
