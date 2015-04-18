package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class CurrentPlayer extends Update {
	
	private String logUpdate;
	private int id;
	private boolean isMe;
	private boolean hasCards;

	public CurrentPlayer() {
		super();
	}
	
	public CurrentPlayer(String logUpdate, int id, boolean isMe, boolean hasCards) {
		super();
		this.logUpdate = logUpdate;
		this.id = id;
		this.isMe = isMe;
		this.hasCards = hasCards;
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

	public boolean getHasCards() {
		return hasCards;
	}

}
