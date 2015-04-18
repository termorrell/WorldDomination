package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class Ready extends Update {
		
	private boolean isReady;
	
	public Ready() {
		super();
	}
	
	public void setIsReady(boolean isReady) {
		this.isReady = isReady;
	}
	
	public boolean getIsReady() {
		return isReady;
	}
}
