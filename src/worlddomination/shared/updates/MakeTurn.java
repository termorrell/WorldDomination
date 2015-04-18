package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class MakeTurn extends Update {

	private String logUpdate;
	private String type;
	private String timeOut;
	private int sourceTerritory;
	private int destinationTerritory;
	private int numberOfArmies;
	
	public MakeTurn() {
		super();
	}
	
	public MakeTurn(String logUpdate) {
		super();
		this.logUpdate = logUpdate;
	}

	public String getLogUpdate() {
		return logUpdate;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setTimeOut(String date) {
		this.timeOut = date;
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
	
	public String getTimeOut() {
		return timeOut;
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
