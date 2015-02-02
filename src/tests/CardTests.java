package tests;

import static org.junit.Assert.*;

import factories.CardFactory;
import model.*;
import controller.*;
import org.junit.Before;
import org.junit.Test;
import view.*;

import java.util.LinkedList;

public class CardTests {
    Model model;

    @Before
    public void setUp(){
        model  = new Model();
        Player[] players = {new Player(),new Player(), new Player()};
        model.getGameState().setPlayers(players);
        //IView view = new Input();
        //Controller controller = new Controller(model,view);
        CardFactory cardFactory = new CardFactory(model.getGameState().getBoard());
        model.getGameState().setCards(cardFactory.getCards());
    }

    /**
     * Checks that 44 cards have been created and added to gameState
     */
    @Test
    public void checkCardsCreated(){
        assertEquals(model.getGameState().getCards().size(),44);
    }

    /**
     * Checks that 14 infantry cards have been created
     */
    @Test
    public void checkInfantryCardsCreated(){
        int count =0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for(int i=0; i<cards.size();i++){
            if(cards.get(i).getType()=="Infantry"){
                count++;
            }
        }
        assertEquals(count,14);
    }
    /**
     * Checks that 14 Cavalry cards have been created
     */
    @Test
    public void checkCavalryCardsCreated(){
        int cavalryCount =0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for(int i=0; i<cards.size();i++){
            if(cards.get(i).getType()=="Cavalry"){
                cavalryCount++;
            }
        }
        assertEquals(cavalryCount,14);
    }

    /**
     * Checks that 14 Artillery cards have been created
     */
    @Test
    public void checkArtilleryCardsCreated(){
        int count =0;
        LinkedList<Card> cards = model.getGameState().getCards();
        for(int i=0; i<cards.size();i++){
            if(cards.get(i).getType()=="Artillery"){
                count++;
            }
        }
        assertEquals(count,14);
    }
}