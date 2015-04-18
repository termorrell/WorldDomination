package worlddomination.server.tests;

import java.util.ArrayList;

import worlddomination.server.model.Model;
import worlddomination.server.model.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import worlddomination.server.controller.Moves;
import worlddomination.server.exceptions.BoardException;
import worlddomination.server.exceptions.IllegalMoveException;

public class MoveTests {
	Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		model.getGameState().setPlayers(players);
	}

	/*
	 * Claiming an unoccupied territory using the reinforce method.
	 */
	@Test
	public void claimTerritory() throws BoardException, IllegalMoveException {
		Player territoryClaimer = model.getGameState().getPlayers().get(0);
		int territoryId = 12;
		Moves.reinforce(territoryClaimer, model.getGameState(), territoryId, 1);
		assertEquals(1,	model.getGameState().getBoard().getTerritoriesById(territoryId)
						.getOccupyingArmies().size());
		assertEquals(1,territoryClaimer.getArmies().size());
	}

	/*
	 * A player shouldn't be able to claim a territory that is already owned by
	 * another player.
	 */
	@Test(expected = IllegalMoveException.class)
	public void claimOccupiedTerritory() throws BoardException,
			IllegalMoveException {
		Player territoryClaimer = model.getGameState().getPlayers().get(0);
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int territoryId = 12;
		Moves.reinforce(territoryOwner, model.getGameState(), territoryId, 1);
		Moves.reinforce(territoryClaimer, model.getGameState(), territoryId, 1);
	}

	/*
	 * Reinforcing a territory that is already owned with multiple armies.
	 */
	@Test
	public void reinforceTerritoryWithTwoArmies() throws BoardException, IllegalMoveException {
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int territoryId = 15;
		Moves.reinforce(territoryOwner, model.getGameState(), territoryId, 1);
		Moves.reinforce(territoryOwner, model.getGameState(), territoryId, 2);
		assertEquals(3,	model.getGameState().getBoard().getTerritoriesById(territoryId)
						.getOccupyingArmies().size());
		assertEquals(3,territoryOwner.getArmies().size());
	}
	
	/*
	 * Fortifying a territory that is already owned with one army.
	 */
	@Test
	public void fortifyMoveOneArmy() throws BoardException, IllegalMoveException {
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int originTerritory = 1;
		int destinationTerritory = 3;
		Moves.reinforce(territoryOwner, model.getGameState(), originTerritory, 2);
		Moves.reinforce(territoryOwner, model.getGameState(), destinationTerritory, 1);
		Moves.fortify(territoryOwner, model.getGameState(), originTerritory, destinationTerritory, 1);
		assertEquals(1,	model.getGameState().getBoard().getTerritoriesById(originTerritory)
						.getOccupyingArmies().size());
		assertEquals(2,	model.getGameState().getBoard().getTerritoriesById(destinationTerritory)
				.getOccupyingArmies().size());
		assertEquals(3,territoryOwner.getArmies().size());
	}
	
	/*
	 * Fortifying a territory that is already owned with multiple armies.
	 */
	@Test
	public void fortifyMoveTwoArmies() throws BoardException, IllegalMoveException {
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int originTerritory = 3;
		int destinationTerritory = 1;
		Moves.reinforce(territoryOwner, model.getGameState(), originTerritory, 3);
		Moves.reinforce(territoryOwner, model.getGameState(), destinationTerritory, 1);
		Moves.fortify(territoryOwner, model.getGameState(), originTerritory, destinationTerritory, 2);
		assertEquals(1,	model.getGameState().getBoard().getTerritoriesById(originTerritory)
						.getOccupyingArmies().size());
		assertEquals(3,	model.getGameState().getBoard().getTerritoriesById(destinationTerritory)
				.getOccupyingArmies().size());
		assertEquals(4,territoryOwner.getArmies().size());
	}
	
	/*
	 * Can't fortify a territory that isn't owned by the same player.
	 */
	@Test(expected = IllegalMoveException.class)
	public void fortifyTerriotryOfOtherPlayer() throws BoardException,
			IllegalMoveException {
		Player fortifier = model.getGameState().getPlayers().get(0);
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int originTerritory = 11;
		int destinationTerritory = 12;
		Moves.reinforce(fortifier, model.getGameState(), originTerritory, 2);
		Moves.reinforce(territoryOwner, model.getGameState(), destinationTerritory, 1);
		Moves.fortify(fortifier, model.getGameState(), originTerritory, destinationTerritory, 1);
	}
	
	/*
	 * Can't fortify if it would leave a territory unoccupied.
	 */
	@Test(expected = IllegalMoveException.class)
	public void fortifyAllArmies() throws BoardException,
			IllegalMoveException {
		Player territoryOwner = model.getGameState().getPlayers().get(1);	
		int originTerritory = 11;
		int destinationTerritory = 12;
		Moves.reinforce(territoryOwner, model.getGameState(), originTerritory, 2);
		Moves.reinforce(territoryOwner, model.getGameState(), destinationTerritory, 1);
		Moves.fortify(territoryOwner, model.getGameState(), originTerritory, destinationTerritory, 2);
	}
	
	/*
	 * Only possible to fortify between neighbouring countries.
	 */
	@Test(expected = IllegalMoveException.class)
	public void fortifyNotBetweenNeighbouringArmies() throws BoardException,
			IllegalMoveException {
		Player territoryOwner = model.getGameState().getPlayers().get(1);
		int originTerritory = 12;
		int destinationTerritory = 23;
		Moves.reinforce(territoryOwner, model.getGameState(), originTerritory, 2);
		Moves.reinforce(territoryOwner, model.getGameState(), destinationTerritory, 1);
		Moves.fortify(territoryOwner, model.getGameState(), originTerritory, destinationTerritory, 1);
	}
	
}
