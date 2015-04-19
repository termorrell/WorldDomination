package worlddomination.server.actions;

public class InitialiseGame extends Action {
    double version;
    String[] supportedFeatures;

    public InitialiseGame(double version, String[] supportedFeatures) {
        this.version = version;
        this.supportedFeatures = supportedFeatures;
    }

    public double getVersion() {
        return version;
    }

    public String[] getSupportedFeatures() {
        return supportedFeatures;
    }
}
