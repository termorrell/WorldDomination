package tests;

import java.util.ArrayList;

import model.Model;
import model.Player;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import controller.Moves;
import exceptions.BoardException;
import exceptions.IllegalMoveException;

public class MoveTests {
	Model model;

	@Before
	public void setUp() throws Exception {
		model = new Model();
		ArrayList<Player> players = new ArrayList<>();
		players.add(new Player());
		players.add(new Player());
		players.add(new Player());
		model.getGameState().setPlayers(players);
	}

	/*
	 * Claiming an unoccupied territory using the reinforce method
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
	 * another player
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
	 * Reinforcing a territory that is already owned with multiple armies
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
}
