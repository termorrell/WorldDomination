package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class AllocateCard extends Update {

	private String logUpdate;
	private int territoryID;
	private String cardType;
	
	public AllocateCard() {
		super();
	}
	
	public AllocateCard(String logUpdate, int territoryID, String cardType) {
		super();
		this.logUpdate = logUpdate;
		this.territoryID = territoryID;
		this.cardType = cardType;
	}
	
	public String getLogUpdate() {
		return logUpdate;
	}
	public int getTerritoryID() {
		return territoryID;
	}
	public String getCardType() {
		return cardType;
	}
}
