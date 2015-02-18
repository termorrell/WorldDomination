package network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by ${mm280} on 18/02/15.
 */
public class TestClient {
    public Client client;
    public Scanner scanner;

    public TestClient(){
        scanner = new Scanner(System.in);
        client = new Client();
        register();
        NetworkListener n1 = new NetworkListener();
        n1.init(client);
        client.addListener(n1);
        client.start();
        try {
            client.connect(5000,scanner.nextLine(),54555);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void register(){
        Kryo kryo = client.getKryo(); //Kryo is a serializer (code info to readable manner to be sent over networks)

    }

    public static void main(String[] args){
        new TestClient();
        Log.set(Log.LEVEL_DEBUG);
    }
}
