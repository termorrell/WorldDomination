package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class LogUpdate extends Update {
	
	private String logUpdate;
	
	public LogUpdate() {
		super();
	}
	
	public LogUpdate(String entry) {
		super();
		this.logUpdate = entry;
	}
	
	public String getLogUpdate() {
		return logUpdate;
	}

}
