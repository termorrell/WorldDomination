package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class DistributeArmy extends Update {

	private String logUpdate;
	private int territoryID;

	public DistributeArmy() {
		super();
	}
	
	public DistributeArmy(String logUpdate) {
		super();
		this.logUpdate = logUpdate;
	}

	public String getLogUpdate() {
		return logUpdate;
	}

	public int getTerritoryID() {
		return territoryID;
	}

	public void setTerritoryID(int territoryID) {
		this.territoryID = territoryID;
	}
}
