package updates;

public class Claim extends Update{
	int territoryId;

	public Claim() {
		super();
	}

	public int getTerritoryId() {
		return territoryId;
	}

	public void setTerritoryId(int territoryId) {
		this.territoryId = territoryId;
	}
}
