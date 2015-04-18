package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class ClaimTerritory extends Update {

	private String logUpdate;
	private int territoryID;
	
	public ClaimTerritory() {
		super();
	}
	
	public ClaimTerritory(String logUpdate) {
		super();
		this.logUpdate = logUpdate;
	}
	
	public String getLogUpdate() {
		return logUpdate;
	}

	public void setTerritoryID(int territoryID) {
		this.territoryID = territoryID;
	}
	
	public int getTerritoryID() {
		return territoryID;
	}
}
