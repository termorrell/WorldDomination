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
import java.util.ArrayList;
import java.util.LinkedList;

public class CardTests {
    Model model;

    @Before
    public void setUp() {

        model = new Model();

        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player());
        }

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
    public void checkCollectCards() {
        controller.CardMethods.collectCard(model.getGameState().getPlayers().get(0), model);
        assertEquals(model.getGameState().getPlayers().get(0).getNoCards(), 1);
    }

    /**
     * Check player can't collect an assigned card
     */
    @Test
    public void checkAssignedCollectCards() {
        model.getGameState().getCards().get(0).setAssigned(true);
        controller.CardMethods.collectCard(model.getGameState().getPlayers().get(0), model);
        assertTrue(model.getGameState().getPlayers().get(0).getCards().contains(model.getGameState().getCards().get(1)));
    }

    /**
     * Check that 3 cards of the same type are accepted as valid
     */
    @Test
    public void checkCardsOfSameTypeAcceptedForTrade() {
        Card[] sameTypeCards = new Card[3];
        int y = 0;
        for (int i = 0; i < model.getGameState().getCards().size(); i++) {
            if (model.getGameState().getCards().get(i).getType().equals("Infantry")) {
                sameTypeCards[y] = model.getGameState().getCards().get(i);
                if (y < 2) {
                    y++;
                } else {
                    break;
                }
            }
        }
        assertTrue(controller.CardMethods.checkAllSameType(sameTypeCards));
    }

    /**
    Check cards of different type are accepted as valid
     */
    @Test
    public void checkCardsOfDifferentTypeAcceptedForTrade() {
        Card[] difTypeCards = new Card[3];
        for (int i = 0; i < model.getGameState().getCards().size(); i++) {
            if (model.getGameState().getCards().get(i).getType().equals("Infantry")) {
                difTypeCards[0] = model.getGameState().getCards().get(i);
            } else if (model.getGameState().getCards().get(i).getType().equals("Artillery")) {
                difTypeCards[1] = model.getGameState().getCards().get(i);
            } else if (model.getGameState().getCards().get(i).getType().equals("Cavalry")) {
                difTypeCards[2] = model.getGameState().getCards().get(i);
            }
        }
        assertTrue(controller.CardMethods.checkAllDifferentType(difTypeCards));
    }

    /**
     * Check card set with wild card present is accepted
     */
    @Test
    public void checkWildCardSetAccepted(){
        Card[] wildCardList = new Card[3];
        int allocated =1;
        for(int i=0;i<model.getGameState().getCards().size();i++){
            if(model.getGameState().getCards().get(i).getType().equals("Wild")){
                wildCardList[0] = model.getGameState().getCards().get(i);
            }else{
                wildCardList[allocated] = model.getGameState().getCards().get(i);
                if(allocated==1) {
                    allocated++;
                }
            }
        }
        assertTrue(controller.CardMethods.checkOneWildCard(wildCardList));
    }



}