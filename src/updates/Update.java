package updates;

public abstract class Update {
    boolean shouldTerminate = false;

    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setToTerminate( ) {
        this.shouldTerminate = true;
    }
}
