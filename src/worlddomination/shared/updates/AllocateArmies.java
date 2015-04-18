package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class AllocateArmies extends Update {

	private String logUpdate;
	private int sourceTerritory;
	private int destinationTerritory;
	private int maximumNumberOfArmies;
	private int armiesMoved;

	public AllocateArmies() {
		super();
	}
	
	public AllocateArmies(String logUpdate, int sourceTerritory, int destinationTerritory, int maximumNumberOfArmies) {
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
	
	public int getArmiesMoved() {
		return armiesMoved;
	}

	public void setArmiesMoved(int armiesMoved) {
		this.armiesMoved = armiesMoved;
	}
}
