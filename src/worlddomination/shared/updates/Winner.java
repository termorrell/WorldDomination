package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class Winner extends Update {
	
	private String logUpdate;
	private boolean isMe;
	private Player[] positions;
	
	public Winner() {
		super();
	}
	
	public Winner(String logUpdate, boolean isMe, Player[] positions) {
		super();
		this.logUpdate = logUpdate;
		this.isMe = isMe;
		this.positions = positions;
	}

	public String getLogUpdate() {
		return logUpdate;
	}

	public boolean isMe() {
		return isMe;
	}
	
	public Player[] getPositions() {
		return positions;
	}
}
