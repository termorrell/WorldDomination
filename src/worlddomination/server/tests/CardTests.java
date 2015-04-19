package worlddomination.server.tests;

import worlddomination.server.factories.CardFactory;
import worlddomination.server.model.Card;
import worlddomination.server.model.Model;
import worlddomination.server.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

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
        controller.ClientCardMethod.collectCard(model.getGameState().getPlayers().get(0), model);
        assertEquals(model.getGameState().getPlayers().get(0).getCards().size(), 1);
    }

    /**
     * Check player can't collect an assigned card
     */
    @Test
    public void checkAssignedCollectCards() {
        model.getGameState().getCards().get(0).setAssigned(true);
        controller.ClientCardMethod.collectCard(model.getGameState().getPlayers().get(0), model);
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
        assertTrue(controller.ClientCardMethod.checkAllSameType(sameTypeCards));
        assertFalse(controller.ClientCardMethod.checkOneWildCard(sameTypeCards));
        assertFalse(controller.ClientCardMethod.checkAllDifferentType(sameTypeCards));

    }

    /**
     * Check cards of different type are accepted as valid
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
        assertTrue(controller.ClientCardMethod.checkAllDifferentType(difTypeCards));
        assertFalse(controller.ClientCardMethod.checkAllSameType(difTypeCards));
        assertFalse(controller.ClientCardMethod.checkOneWildCard(difTypeCards));
    }

    /**
     * Check card set with wild card present is accepted
     */
    @Test
    public void checkWildCardSetAccepted() {
        Card[] wildCardList = new Card[3];
        int allocated = 1;
        for (int i = 0; i < model.getGameState().getCards().size(); i++) {
            if (model.getGameState().getCards().get(i).getType().equals("Wild")) {
                wildCardList[0] = model.getGameState().getCards().get(i);
            } else {
                wildCardList[allocated] = model.getGameState().getCards().get(i);
                if (allocated == 1) {
                    allocated++;
                }
            }
        }
        assertTrue(controller.ClientCardMethod.checkOneWildCard(wildCardList));
        assertFalse(controller.ClientCardMethod.checkAllDifferentType(wildCardList));
        assertFalse(controller.ClientCardMethod.checkAllSameType(wildCardList));
    }

    /**
     * Check armies awarded calculated correctly
     */
    @Test
    public void checkArmiesAwarded() {
        model.getGameState().setCardsTradedIn(0);
        assertEquals(controller.ClientCardMethod.calculateArmiesAwarded(model),4);
        model.getGameState().setCardsTradedIn(4);
        assertEquals(controller.ClientCardMethod.calculateArmiesAwarded(model),12);
        model.getGameState().setCardsTradedIn(5);
        assertEquals(controller.ClientCardMethod.calculateArmiesAwarded(model),15);
        model.getGameState().setCardsTradedIn(6);
        assertEquals(controller.ClientCardMethod.calculateArmiesAwarded(model), 20);
        model.getGameState().setCardsTradedIn(9);
        assertEquals(controller.ClientCardMethod.calculateArmiesAwarded(model), 35);
    }

    @Test
    public void checkCanTradeInCardsWith3ValidSetCavalry(){
        LinkedList<Card> cards = new LinkedList<>();
        for(int i =0;i<model.getGameState().getCards().size();i++){
            if(cards.size()<3) {
                if (model.getGameState().getCards().get(i).getType().equals("Cavalry")) {
                    cards.add(model.getGameState().getCards().get(i));
                }
            }else{
                break;
            }
        }
        model.getGameState().getPlayers().get(0).setCards(cards);
        assertTrue(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0),model));
    }

    @Test
    public void checkCanTradeInCardsWith5ValidSet(){
        LinkedList<Card> cards = new LinkedList<>();
        for(int i =0;i<model.getGameState().getCards().size();i++){
            if(cards.size()<4) {
                if (!cards.contains(model.getGameState().getCards().get(i).getType())) {
                    cards.add(model.getGameState().getCards().get(i));
                }
            }else if(cards.size()==4){
                    cards.add(model.getGameState().getCards().get(i));
                    break;
                }
        }
        System.out.println(cards.size());
        model.getGameState().getPlayers().get(0).setCards(cards);
        assertTrue(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0), model));
    }
    @Test
    public void checkCantTradeInCardsWith2Set(){
        LinkedList<Card> cards = new LinkedList<>();
        cards.add(model.getGameState().getCards().get(2));
        cards.add(model.getGameState().getCards().get(4));
        model.getGameState().getPlayers().get(0).setCards(cards);
        assertFalse(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0), model));
    }
    @Test
    public void checkCantTradeInCardsWith3InvalidSet(){
        LinkedList<Card> cards = new LinkedList<>();
        boolean cav = false;
        for(int i =0;i<model.getGameState().getCards().size();i++) {
            if(cards.size()<3) {
                if (model.getGameState().getCards().get(i).getType().equals("Cavalry") &&!cav) {
                    cards.add(model.getGameState().getCards().get(i));
                    cav=true;
                } else if (model.getGameState().getCards().get(i).getType().equals("Artillery")) {
                    cards.add(model.getGameState().getCards().get(i));
                }
            }else{
                break;
            }
        }
            model.getGameState().getPlayers().get(0).setCards(cards);
            assertFalse(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0), model));

    }
    @Test
    public void checkCantTradeInCardsWith3ValidSetInfantry(){
        LinkedList<Card> cards = new LinkedList<>();
        for(int i =0;i<model.getGameState().getCards().size();i++){
            if(cards.size()<3) {
                if (model.getGameState().getCards().get(i).getType().equals("Infantry")) {
                    cards.add(model.getGameState().getCards().get(i));
                }
            }else{
                break;
            }
        }
        model.getGameState().getPlayers().get(0).setCards(cards);
        assertTrue(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0),model));
    }
    @Test
    public void checkCanTradeInCardsWith3ValidSetArtillery(){
        LinkedList<Card> cards = new LinkedList<>();
        for(int i =0;i<model.getGameState().getCards().size();i++){
            if(cards.size()<3) {
                if (model.getGameState().getCards().get(i).getType().equals("Artillery")) {
                    cards.add(model.getGameState().getCards().get(i));
                }
            }else{
                break;
            }
        }
        model.getGameState().getPlayers().get(0).setCards(cards);
        assertTrue(controller.ClientCardMethod.canTradeInCards(model.getGameState().getPlayers().get(0),model));

    }

}