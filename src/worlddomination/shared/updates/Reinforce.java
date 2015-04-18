package worlddomination.shared.updates;

@SuppressWarnings("serial")
public class Reinforce extends Update {

	private String logUpdate;
	private int numberOfArmies;
	private int[] allocationOfArmies;
	
	public Reinforce() {
		super();
	}
	
	public Reinforce(String logUpdate, int numberOfArmies) {
		super();
		this.logUpdate = logUpdate;
		this.numberOfArmies = numberOfArmies;
	}

	public String getLogUpdate() {
		return logUpdate;
	}

	public int getNumberOfArmies() {
		return numberOfArmies;
	}
	
	public int[] getAllocationOfArmies() {
		return allocationOfArmies;
	}

	public void setAllocationOfArmies(int[] allocationOfArmies) {
		this.allocationOfArmies = allocationOfArmies;
	}
}
