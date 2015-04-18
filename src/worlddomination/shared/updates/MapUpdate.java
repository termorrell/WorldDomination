package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class MapUpdate extends Update {
	
	private String logUpdate;
	private String mapState;

	public MapUpdate() {
		super();
	}
	
	public MapUpdate(String logUpdate, String mapState) {
		super();
		this.logUpdate = logUpdate;
		this.mapState = mapState;
	}

	public String getLogUpdate() {
		return logUpdate;
	}
	
	public String getMapState() {
		return mapState;
	}
}
