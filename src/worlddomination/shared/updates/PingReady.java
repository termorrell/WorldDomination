package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class PingReady extends Update {
		
	private boolean isReady;
	
	public PingReady() {
		super();
	}
	
	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public boolean getIsReady() {
		return isReady;
	}
}
