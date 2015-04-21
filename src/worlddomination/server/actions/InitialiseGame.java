package worlddomination.server.actions;

public class InitialiseGame extends Action {
    double version;
    String[] supportedFeatures;

    /**
     * Stores the attributes necessary for sending and receiving an 'init_game' command
     * @param version version being played
     * @param supportedFeatures features being played
     */
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
