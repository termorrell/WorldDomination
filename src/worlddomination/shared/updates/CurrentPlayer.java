package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class CurrentPlayer extends Update {
	
	private String logUpdate;
	private int id;
	private boolean isMe;

	public CurrentPlayer() {
		super();
	}
	
	public CurrentPlayer(String logUpdate, int id, boolean isMe) {
		super();
		this.logUpdate = logUpdate;
		this.id = id;
		this.isMe = isMe;
	}
	
	public String getLogUpdate() {
		return logUpdate;
	}
	
	public int getID() {
		return id;
	}

	public boolean getIsMe() {
		return isMe;
	}
}
