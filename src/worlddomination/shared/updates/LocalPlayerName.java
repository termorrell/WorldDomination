package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class LocalPlayerName extends Update {
	
	private String logUpdate;
	private String name;
	
	public LocalPlayerName() {
		super();
	}

	public LocalPlayerName(String logUpdate) {
		super();
		this.logUpdate = logUpdate;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogUpdate() {
		return logUpdate;
	}
}
