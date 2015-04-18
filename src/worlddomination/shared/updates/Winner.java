package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class Winner extends Update {
	
	private String logUpdate;
	private boolean isMe;
	private LobbyPlayer[] positions;
	
	public Winner() {
		super();
	}
	
	public Winner(String logUpdate, boolean isMe, LobbyPlayer[] positions) {
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
	
	public LobbyPlayer[] getPositions() {
		return positions;
	}
}
