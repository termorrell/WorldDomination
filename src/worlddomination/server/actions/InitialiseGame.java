package worlddomination.server.actions;

public class InitialiseGame extends Action {
    float version;
    String[] supportedFeatures;

    public InitialiseGame(float version, String[] supportedFeatures) {
        this.version = version;
        this.supportedFeatures = supportedFeatures;
    }

    public float getVersion() {
        return version;
    }

    public String[] getSupportedFeatures() {
        return supportedFeatures;
    }
}
