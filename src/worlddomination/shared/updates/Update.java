package worlddomination.shared.updates;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Update implements Serializable {

    boolean shouldTerminate = false;
	
	public Update() {}
	
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setToTerminate( ) {
        this.shouldTerminate = true;
    }
}
