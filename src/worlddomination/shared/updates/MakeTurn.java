package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class MakeTurn extends Update {

	private String logUpdate;
	private String timeOut;
	private boolean allowTradeIn; 
	private String type;
	private int sourceTerritory;
	private int destinationTerritory;
	private int numberOfArmies;
	
	public MakeTurn() {
		super();
	}
	
	public MakeTurn(String logUpdate, String timeOut, boolean allowTradeIn) {
		super();
		this.logUpdate = logUpdate;
		this.timeOut = timeOut;
		this.allowTradeIn = allowTradeIn;
	}

	public String getLogUpdate() {
		return logUpdate;
	}
	
	public String getTimeOut() {
		return timeOut;
	}
	
	public boolean getAllowTradeIn() {
		return allowTradeIn;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setSourceTerritory(int sourceTerritory) {
		this.sourceTerritory = sourceTerritory;
	}

	public void setDestinationTerritory(int destinationTerritory) {
		this.destinationTerritory = destinationTerritory;
	}

	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}

	public String getType() {
		return type;
	}
	public int getSourceTerritory() {
		return sourceTerritory;
	}

	public int getDestinationTerritory() {
		return destinationTerritory;
	}

	public int getNumberOfArmies() {
		return numberOfArmies;
	}
}
