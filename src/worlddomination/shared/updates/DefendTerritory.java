package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class DefendTerritory extends Update {

	private String logUpdate;
	private int sourceTerritory;
	private int destinationTerritory;
	private int maximumNumberOfArmies;
	private int armiesUsed;

	public DefendTerritory() {
		super();
	}
	
	public DefendTerritory(String logUpdate, int sourceTerritory, int destinationTerritory, int maximumNumberOfArmies) {
		super();
		this.logUpdate = logUpdate;
		this.sourceTerritory = sourceTerritory;
		this.destinationTerritory = destinationTerritory;
		this.maximumNumberOfArmies = maximumNumberOfArmies;
	}

	public String getLogUpdate() {
		return logUpdate;
	}

	public int getSourceTerritory() {
		return sourceTerritory;
	}

	public int getDestinationTerritory() {
		return destinationTerritory;
	}

	public int getMaximumNumberOfArmies() {
		return maximumNumberOfArmies;
	}
	
	public int getArmiesUsed() {
		return armiesUsed;
	}

	public void setArmiesUsed(int armiesUsed) {
		this.armiesUsed = armiesUsed;
	}
}
