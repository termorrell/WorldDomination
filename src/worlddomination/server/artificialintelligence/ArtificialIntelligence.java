package worlddomination.server.artificialintelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import worlddomination.server.model.Continent;
import worlddomination.server.model.Model;
import worlddomination.server.model.Territory;
import worlddomination.shared.updates.AllocateArmies;
import worlddomination.shared.updates.ClaimTerritory;
import worlddomination.shared.updates.DefendTerritory;
import worlddomination.shared.updates.DistributeArmy;
import worlddomination.shared.updates.LocalPlayerName;
import worlddomination.shared.updates.MakeTurn;
import worlddomination.shared.updates.PingReady;
import worlddomination.shared.updates.Reinforce;
import worlddomination.shared.updates.Update;

public class ArtificialIntelligence {

	private Model gameModel;
	private int numberOfTurnsPlayed = 0;
	private int aiPlayerId;
	private Random random = new Random();
	private static ArtificialIntelligence instance = null;
	private static final int DISTRIBUTION_DENSITY_RATIO = 5;
	
	protected ArtificialIntelligence() {}

	/**
	 * Constructor for the AI
	 * @param gameModel model with all of the game state stored within it
	 */
	protected ArtificialIntelligence(Model gameModel) {
		this.gameModel = gameModel;
		this.aiPlayerId = gameModel.getPlayerInfo().getId();
	}

	/**
	 * Checks if an AI is already instantiated
	 * @param gameModel model with all of the game state stored within it
	 * @return a new AI instance
	 */
	public static ArtificialIntelligence getInstance(Model gameModel) {
		
		if(instance == null) {
			instance = new ArtificialIntelligence(gameModel);
	    }
		return instance;
	}

	/**
	 * Get the update
	 * @param update the update from the GUI
	 * @return a new update
	 */
	public Update getUpdateResponse(Update update) {

		if (update instanceof LocalPlayerName) {

			LocalPlayerName localPlayerName = (LocalPlayerName)update;
			configureLocalPlayerNameResponse(localPlayerName);
		} else if (update instanceof PingReady) {

			PingReady pingReady = (PingReady)update;
			configurePingReadyResponse(pingReady);
		} else if (update instanceof ClaimTerritory) {

			ClaimTerritory claimTerritory = (ClaimTerritory)update;
			configureClaimTerritoryResponse(claimTerritory);
		} else if (update instanceof DistributeArmy) {

			DistributeArmy distributeArmy = (DistributeArmy)update;
			configureDistributeArmyResponse(distributeArmy);
		} else if (update instanceof Reinforce) {

			Reinforce reinforce = (Reinforce)update;
			configureReinforceResponse(reinforce);
		} else if (update instanceof MakeTurn) {

			MakeTurn makeTurn = (MakeTurn)update;
			configureMakeTurnResponse(makeTurn);
		} else if (update instanceof DefendTerritory) {

			DefendTerritory defendTerritory = (DefendTerritory)update;
			configureDefendTerritoryResponse(defendTerritory);
		} else if (update instanceof AllocateArmies) {

			AllocateArmies allocateArmies = (AllocateArmies)update;
			configureAllocateArmiesResponse(allocateArmies);
		}

		return update;
	}

	/**
	 * Create a local player name for the GUI
	 * @param localPlayerName name to be given
	 * @return new local player name
	 */
	private LocalPlayerName configureLocalPlayerNameResponse(LocalPlayerName localPlayerName) {

		// Only response is the name the player wants to use.
		localPlayerName.setName("Artificial Intelligence");
		return localPlayerName;
	}

	/**
	 * Create the ping response
	 * @param pingReady confirm the user is ready to play the game
	 * @return 
	 */
	private PingReady configurePingReadyResponse(PingReady pingReady) {

		// Confirm the user is ready to play the game, this should always be true.
		pingReady.setIsReady(true);
		return pingReady;
	}

	/**
	 * Tries to find an appropriate territory to claim
	 * @param claim territory to claim
	 * @return territory that has been claimed
	 */
	private ClaimTerritory configureClaimTerritoryResponse(ClaimTerritory claim) {

		// Choose a territory which has not yet been selected

		// Create an ArrayList of available territories
		ArrayList<Territory> availableTerritories = getAvailableFocusTerritories();

		// Get the territory with the highest priority
		Territory chosenTerritory = availableTerritories.get(0);

		// Set the territory ID and return the update
		claim.setTerritoryID(chosenTerritory.getId());
		return claim;
	}

	/**
	 * Trying to find a good place to distribute an army
	 * @param distribute
	 * @return good place
	 */
	private DistributeArmy configureDistributeArmyResponse(DistributeArmy distribute) {

		// Choose a territory which is already owned by the AI, for this we want to inspect the model

		// Create an ArrayList of owned territories
		ArrayList<Territory> ownedTerritories = getOwnedTerritories();

		// Select a random index to use
		int max = ownedTerritories.size();	// Exclusive value
		int min = 0; 						// Inclusive value
		int index = random.nextInt(max - min) + min;

		// TODO: Add some sort of weighting to choose territories where it neighbours with itself
		Territory chosenTerritory = ownedTerritories.get(index);

		// Set the territory ID and return the update
		distribute.setTerritoryID(chosenTerritory.getId());
		return distribute;
	}

	/**
	 * Trying to find an appropriate move
	 * @param reinforce
	 * @return the appropriate reinforce move
	 */
	private Reinforce configureReinforceResponse(Reinforce reinforce) {

		// Choose the number of territories requested which are owned by the AI, for this we want to inspect the model
		int numberOfArmiesToAllocate = reinforce.getNumberOfArmies();

		// Create an ArrayList of ownedTerritories and selectedTerritories
		ArrayList<Territory> ownedTerritories = getOwnedTerritories();
		ArrayList<Integer> selectedTerritoryIds = new ArrayList<Integer> ();

		for (int i = 0; i < numberOfArmiesToAllocate; i++) {

			// Select a random index to use
			int max = ownedTerritories.size();	// Exclusive value
			int min = 0; 						// Inclusive value
			int index = random.nextInt(max - min) + min;

			// TODO: Add some sort of weighting to choose territories where it neighbours with itself
			Territory chosenTerritory = ownedTerritories.get(index);
			selectedTerritoryIds.add(chosenTerritory.getId());
		}

		// Create an int array to set for the update
		Integer[] allocationOfArmies = new Integer[selectedTerritoryIds.size()];
		int[] primitiveAllocationOfArmies = ArrayUtils.toPrimitive(allocationOfArmies);
		reinforce.setAllocationOfArmies(primitiveAllocationOfArmies);
		return reinforce;
	}

	/**
	 * Trying to figure out the best move to make
	 * @param makeTurn
	 * @return best move
	 */
	private MakeTurn configureMakeTurnResponse(MakeTurn makeTurn) {

		// Need to choose whether or not to quit
		if (numberOfTurnsPlayed < 5) {
			if (numberOfTurnsPlayed == 0) {

				boolean shouldTradeIn = false;

				// First turn, trade in cards if available
				if (shouldTradeIn) {

					makeTurn.setType("TradeIn");
				}
			} else {

				// Create an ArrayList of owned territories
				ArrayList<Territory> ownedTerritories = getOwnedTerritories();
				ArrayList<Territory> filteredTerritories = filter(ownedTerritories, sufficientArmiesPredicate);

				// Select a random index to use
				int max = ownedTerritories.size();	// Exclusive value
				int min = 0; 						// Inclusive value
				int index = random.nextInt(max - min) + min;

				// TODO: Add some sort of weighting to choose territories where it neighbours with itself
				Territory sourceTerritory = filteredTerritories.get(index);

				// Create an array list of the neighbouring opponents
				ArrayList<Territory> neighbouringOpponents = getNeighbouringOpponentsTerritories(sourceTerritory);

				// Select a random index to use
				max = neighbouringOpponents.size();	// Exclusive value
				min = 0; 							// Inclusive value
				index = random.nextInt(max - min) + min;
				
				// TODO: Add some sort of weighting to choose territories to attack
				// TODO: Might fail if surrounded by own
				Territory destinationTerritory = neighbouringOpponents.get(index);
				
				// Make an attack
				makeTurn.setType("Attack");
				makeTurn.setSourceTerritory(sourceTerritory.getId());
				makeTurn.setDestinationTerritory(destinationTerritory.getId());
				makeTurn.setNumberOfArmies(sourceTerritory.getOccupyingArmies().size() - 1);
			}
		} else {

			// Quit the turn
			makeTurn.setType("Quit");

			// Reset the number of consecutive turns played
			numberOfTurnsPlayed = 0;
		}

		return makeTurn;
	}

	/**
	 * Trying to figure out how many armies to defend
	 * @param defendTerritory 
	 * @return best defend move
	 */
	private DefendTerritory configureDefendTerritoryResponse(DefendTerritory defendTerritory) {

		// Choose the maximum number of territories which are allowed, no need to inspect the model
		int armiesUsed = defendTerritory.getMaximumNumberOfArmies();
		// Set the armies used to the maximum available
		defendTerritory.setArmiesUsed(armiesUsed);
		return defendTerritory;
	}
	/**
	 * Figure out the best response for placing armies
	 * @param allocateArmies
	 * @return best response for army placement
	 */
	private AllocateArmies configureAllocateArmiesResponse(AllocateArmies allocateArmies) {

		// Choose the maximum number of territories which are allowed, no need to inspect the model
		int armiesMoved = allocateArmies.getMaximumNumberOfArmies();
		// Set the armies used to the maximum available
		allocateArmies.setArmiesMoved(armiesMoved);
		return allocateArmies;
	}

	/**
	 * Get all the territories it currently owns
	 * @return arraylist of territories
	 */
	private ArrayList<Territory> getOwnedTerritories() {

		// Create a list of all territories on the board
		Territory[] allTerritoriesArray = gameModel.getGameState().getBoard().getTerritories();
		ArrayList<Territory> allTerritories = new ArrayList<Territory> (Arrays.asList(allTerritoriesArray));

		// Create a list of all territories owned by the AI
		ArrayList<Territory> ownedTerritories = new ArrayList<Territory> ();

		// Iterate through all territories to find ones owned by the AI
		for (Territory territory: allTerritories) {
			if (territory.getOwner() != null) {

				// Check if the Id is the same as the ai, if it is add it to the list
				if (territory.getOwner().getId() == aiPlayerId) {
					ownedTerritories.add(territory);
				}
			}
		}

		// Return the ArrayList which has had empty territories added
		return ownedTerritories;
	}

	/**
	 * Get all the available territories the AI can claim
	 * @return list of available territories
	 */
	private ArrayList<Territory> getAvailableTerritories() {

		// Create a list of all territories on the board
		Territory[] allTerritoriesArray = gameModel.getGameState().getBoard().getTerritories();
		ArrayList<Territory> allTerritories = new ArrayList<Territory> (Arrays.asList(allTerritoriesArray));

		// Create a list of all available territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory> ();

		// Iterate through all territories to find empty ones
		for (Territory territory: allTerritories) {
			if (territory.getOwner() == null) {
				availableTerritories.add(territory);
			}
		}

		// Return the ArrayList which has had empty territories added
		return availableTerritories;
	}
	
	/**
	 * Get available territories within a continent
	 * @param continent continent to check
	 * @return list of available territories
	 */
	private ArrayList<Territory> getAvailableTerritoriesForContinent(Continent continent) {

		// Create a list of all available territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory> ();
		
		// Iterate through all territories in a continent
		Territory[] continentTerritoriesArray = continent.getTerritories();
		ArrayList<Territory> continentTerritories = new ArrayList<Territory> (Arrays.asList(continentTerritoriesArray));
		for (Territory territory: continentTerritories) {
			if (territory.getOwner() == null) {
				
				// If the territory is empty add it
				availableTerritories.add(territory);
			}
		}
		return availableTerritories;
	}
	
	/**
	 * Get all the territories available
	 * @return list of territories
	 */
	private ArrayList<Territory> getAvailableFocusTerritories() {
		
		// Create a list of all territories on the board
		Continent[] allContinentsArray = gameModel.getGameState().getBoard().getContinents();
		ArrayList<Continent> allContinents = new ArrayList<Continent> (Arrays.asList(allContinentsArray));

		// Create a list of all available territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory> ();

		// Iterate through all territories and prioritise territories in South America or Australia
		Continent australia = allContinents.get(5);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(australia));
		Continent southAmerica = allContinents.get(1);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(southAmerica));
		Continent northAmerica = allContinents.get(0);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(northAmerica));
		Continent africa = allContinents.get(3);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(africa));
		Continent europe = allContinents.get(2);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(europe));
		Continent asia = allContinents.get(4);
		availableTerritories.addAll(getAvailableTerritoriesForContinent(asia));

		// Return the ArrayList which has had empty territories added
		return availableTerritories;
	}
	
	/**
	 * Get focus territories currently owned by AI
	 * @param numberOfArmies number of armies in territories
	 * @return list of territories
	 */
	private ArrayList<Territory> getOwnedFocusTerritores(int numberOfArmies) {
		

		// Create a list of all territories on the board
		Continent[] allContinentsArray = gameModel.getGameState().getBoard().getContinents();
		ArrayList<Continent> allContinents = new ArrayList<Continent> (Arrays.asList(allContinentsArray));

		// Create a list of all available territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory> ();

		// Iterate through all territories and prioritise territories in South America or Australia
		Continent australia = allContinents.get(5);
		Continent southAmerica = allContinents.get(1);
		Continent northAmerica = allContinents.get(0);
		Continent africa = allContinents.get(3);
		Continent europe = allContinents.get(2);
		Continent asia = allContinents.get(4);

		// Return the ArrayList which has had empty territories added
		return availableTerritories;
	}

	/**
	 * NUmber of territories neighbouring a territory
	 * @param territory territory to check
	 * @return list of territories
	 */
	private ArrayList<Territory> getNeighbouringOpponentsTerritories(Territory territory) {

		// Create a list of all neighbouring territories
		Territory[] allNeighboursArray = territory.getNeighbours();
		ArrayList<Territory> allNeighbours = new ArrayList<Territory> (Arrays.asList(allNeighboursArray));

		// Create a list of all neighbouring opponents territories
		ArrayList<Territory> opponentsTerritories = new ArrayList<Territory> ();

		// Iterate through all neighbours to find opponents ones
		for (Territory neighbour: allNeighbours) {
			if (neighbour.getOwner() != null) {

				// Check if the Id is the same as the AI, if it is not we assume it is an opponent
				if (neighbour.getOwner().getId() != aiPlayerId) {
					opponentsTerritories.add(neighbour);
				}
			}
		}

		// Return the ArrayList which has had neighbouring opponent territories added
		return opponentsTerritories;
	}

	/**
	 * Number of territories owned by AI surrounding a specific territory
	 * @param territory territory to check
	 * @return list of territories
	 */
	private ArrayList<Territory> getNeighbouringOwnTerritories(Territory territory) {

		// Create a list of all neighbouring territories
		Territory[] allNeighboursArray = territory.getNeighbours();
		ArrayList<Territory> allNeighbours = new ArrayList<Territory> (Arrays.asList(allNeighboursArray));

		// Create a list of all neighbouring own territories
		ArrayList<Territory> ownTerritories = new ArrayList<Territory> ();

		// Iterate through all neighbours to find own ones
		for (Territory neighbour: allNeighbours) {
			if (neighbour.getOwner() != null) {

				// Check if the Id is the same as the AI, if it is we assume it it is owned by the AI
				if (neighbour.getOwner().getId() == aiPlayerId) {
					ownTerritories.add(neighbour);
				}
			}
		}

		// Return the ArrayList which has had neighbouring own territories added
		return ownTerritories;
	}
	
	/**
	 * Check whether it is a good move to add armies to continent
	 * @param continent continent to check
	 * @return true or false
	 */
	public boolean shouldReinforceContinent(Continent continent) {
		
		int totalArmiesInContinent = 0;
		int totalTerritoriesOccupied = 0;

		// Iterate through all territories in a continent
		Territory[] continentTerritoriesArray = continent.getTerritories();
		ArrayList<Territory> continentTerritories = new ArrayList<Territory> (Arrays.asList(continentTerritoriesArray));
		for (Territory territory: continentTerritories) {
			if (territory.getOwner() != null) {
				
				// Check if the territory is owned by the AI
				if (territory.getOwner().getId() == aiPlayerId) {
					
					totalArmiesInContinent += territory.getOccupyingArmies().size();
					totalTerritoriesOccupied ++;
				}
			}
		}
		
		// Check if any territories are owned in the continent
		if (totalTerritoriesOccupied > 0) {
			
			// Ensure the continent has a high distribution of armies
			double distributionRatio = totalArmiesInContinent/totalTerritoriesOccupied;
			if (distributionRatio < DISTRIBUTION_DENSITY_RATIO) {
				
				return true;
			} else {
				
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * Reinforce a continent by deciding which territory to reinforce within it
	 * @param continent continent to reinforce
	 * @param numberOfArmies armies available to place
	 * @return arraylist of territories
	 */
	public ArrayList<Territory> reinforceContinent(Continent continent, int numberOfArmies) {
		
		// Iterate through all territories in a continent
		Territory[] continentTerritoriesArray = continent.getTerritories();
		ArrayList<Territory> continentTerritories = new ArrayList<Territory> (Arrays.asList(continentTerritoriesArray));
		
		// Create a list of all neighbouring own territories
		ArrayList<Territory> territoriesToArm = new ArrayList<Territory> ();
		
		for (Territory territory: continentTerritories) {
			if (territory.getOwner() != null) {
				
				// Check if the territory is owned by the AI
				if (territory.getOwner().getId() == aiPlayerId) {
					
					//TODO: Ratio should be relative to the number of armies on the board
					if (territory.getOccupyingArmies().size() < DISTRIBUTION_DENSITY_RATIO) {
						
						territoriesToArm.add(territory);
					}
				}
			}
		}
		
		return territoriesToArm;
	}

	public interface Predicate<T> { 
		boolean sufficientArmies(T type); 
	}

	Predicate<Territory> sufficientArmiesPredicate = new Predicate<Territory>() {
		public boolean sufficientArmies(Territory territory) {
			return (territory.getOccupyingArmies().size() > 1);
		}
	};

	public static <T> ArrayList<T> filter(Collection<T> col, Predicate<T> predicate) {
		ArrayList<T> result = new ArrayList<T>();
		for (T element: col) {
			if (predicate.sufficientArmies(element)) {
				result.add(element);
			}
		}
		return result;
	}
}
