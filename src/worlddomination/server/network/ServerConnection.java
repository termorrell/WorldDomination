package worlddomination.server.network;

/**
 * Created by 120011588 on 18/04/15.
 */
public class ServerConnection {

    String name;
    int player_id;
    ServerClientThread connection;

    public ServerConnection(int player_id,ServerClientThread connection){
        this.player_id = player_id;
        this.connection=connection;
    }


    public ServerClientThread getConnection() {
        return connection;
    }

    public void setConnection(ServerClientThread connection) {
        this.connection = connection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

}
