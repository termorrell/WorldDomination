package tests;

import static org.junit.Assert.*;

import factories.CardFactory;
import model.*;
import controller.*;
import org.junit.Before;
import org.junit.Test;
import view.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class CardTests {
    Model model;

    @Before
    public void setUp() {

        model = new Model();

        Player[] players = {new Player(), new Player(), new Player()};
        model.getGameState().setPlayers(players);
        CardFactory cardFactory = new CardFactory(model.getGameState().getBoard());
        model.getGameState().setCards(cardFactory.getCards());
    }

    /**
     * Checks that 44 cards have been created and added to gameState
     */
    @Test
    public void checkCardsCreated() {
        assertEquals(model.getGameState().getCards().size(), 44);
    }

    /**
     * Checks that 14 infantry cards have been created
     */
    @Test
    public void checkInfantryCardsCreated() {
        int count = 0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType().equals("Infantry")) {
                count++;
            }
        }
        assertEquals(count, 14);
    }

    /**
     * Checks that 14 Cavalry cards have been created
     */
    @Test
    public void checkCavalryCardsCreated() {
        int cavalryCount = 0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType().equals("Cavalry")) {
                cavalryCount++;
            }
        }
        assertEquals(cavalryCount, 14);
    }

    /**
     * Checks that 14 Artillery cards have been created
     */
    @Test
    public void checkArtilleryCardsCreated() {
        int count = 0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getType().equals("Artillery")) {
                count++;
            }
        }
        assertEquals(count, 14);
    }

    /**
    * Check that collect cards method assigns new cards to player
    */
    @Test
    public void checkCollectCards(){
        controller.CardMethods.collectCard(model.getGameState().getPlayers()[0],model);
        assertEquals(model.getGameState().getPlayers()[0].getNoCards(),1);
    }

    /**
     * Check player can't collect an assigned card
     */
    @Test
    public void checkAssignedCollectCards(){
        model.getGameState().getCards().get(0).setAssigned(true);
        controller.CardMethods.collectCard(model.getGameState().getPlayers()[0],model);
        assertTrue(model.getGameState().getPlayers()[0].getCards().contains(model.getGameState().getCards().get(1)));
    }

}