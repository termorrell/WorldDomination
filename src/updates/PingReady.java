package updates;

public class PingReady extends Update{
	boolean ready;

	public PingReady() {
		super();
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}	
}
