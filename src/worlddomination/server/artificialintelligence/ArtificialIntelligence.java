package worlddomination.server.artificialintelligence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

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

	public ArtificialIntelligence(Model gameModel) {
		this.gameModel = gameModel;
		this.aiPlayerId = gameModel.getPlayerInfo().getId();
	}

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

	private LocalPlayerName configureLocalPlayerNameResponse(LocalPlayerName localPlayerName) {

		// Only response is the name the player wants to use.
		localPlayerName.setName("Artificial Intelligence");
		return localPlayerName;
	}

	private PingReady configurePingReadyResponse(PingReady pingReady) {

		// Confirm the user is ready to play the game, this should always be true.
		pingReady.setIsReady(true);
		return pingReady;
	}

	private ClaimTerritory configureClaimTerritoryResponse(ClaimTerritory claim) {

		// Choose a territory which has not yet been selected

		// Create an ArrayList of available territories
		ArrayList<Territory> availableTerritories = getAvailableTerritories();

		// Select a random index to use
		int max = availableTerritories.size(); 	// Exclusive value
		int min = 0; 							// Inclusive value
		int index = random.nextInt(max - min) + min;

		// TODO: Add some sort of weighting to choose territories in the same continent
		// TODO: Add a weighting to Australia as its easier to defend
		Territory chosenTerritory = availableTerritories.get(index);

		// Set the territory ID and return the update
		claim.setTerritoryID(chosenTerritory.getId());
		return claim;
	}

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

	private DefendTerritory configureDefendTerritoryResponse(DefendTerritory defendTerritory) {

		// Choose the maximum number of territories which are allowed, no need to inspect the model
		int armiesUsed = defendTerritory.getMaximumNumberOfArmies();
		// Set the armies used to the maximum available
		defendTerritory.setArmiesUsed(armiesUsed);
		return defendTerritory;
	}

	private AllocateArmies configureAllocateArmiesResponse(AllocateArmies allocateArmies) {

		// Choose the maximum number of territories which are allowed, no need to inspect the model
		int armiesMoved = allocateArmies.getMaximumNumberOfArmies();
		// Set the armies used to the maximum available
		allocateArmies.setArmiesMoved(armiesMoved);
		return allocateArmies;
	}

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
