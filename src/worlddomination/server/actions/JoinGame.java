package worlddomination.server.actions;


public class JoinGame  extends Action{

    String player_name;
    float[] supported_versions;
    String[] supported_features;

    public JoinGame(String name, float[] supported_versions, String[] supported_features){
        this.player_name = name;
        this.supported_features = supported_features;
        this.supported_versions =  supported_versions;
    }

    public String[] getSupported_features() {
        return supported_features;
    }

    public float[] getSupported_versions() {
        return supported_versions;
    }

    public String getPlayer_name() {
        return player_name;
    }


}
