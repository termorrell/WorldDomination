package controller;


import model.*;
import view.IView;

import java.io.BufferedReader;

/**
 * Created by ${mm280} on 03/02/15.
 */
public class CardMethods {
    //Called at end of each turn
    // TODO SHUFFLE CARDS?
    public static void collectCard(Player activePlayer, Model model){
        for(int i =0; i<model.getGameState().getCards().size();i++){
            if(!model.getGameState().getCards().get(i).isAssigned()){
                activePlayer.getCards().add(model.getGameState().getCards().get(i));
                model.getGameState().getCards().get(i).setAssigned(true);
                int playerCards = activePlayer.getNoCards();
                playerCards++;
                activePlayer.setNoCards(playerCards);
                break;
            }
        }
    }

    //Called at beginning of turn
    public static void  tradeInCards(Player activePlayer, BufferedReader reader, IView view, Model model){
        //Force player to trade in cards
        if(activePlayer.getNoCards()>=5){
            System.out.println("You have to trade in some of your cards");
            activePlayer.printCards(activePlayer.getCards());
            cardTrader(activePlayer, reader, view,model);

        }else{
            //Ask player to if they want to
            activePlayer.printCards(activePlayer.getCards());
            String input = view.getInput("Would you like to trade in your cards?(Y/N)", reader);
            input.toLowerCase();
            if(input.equals("y")){
                cardTrader(activePlayer, reader, view, model);
            }
        }
    }

    public static void cardTrader(Player activePlayer, BufferedReader reader, IView view, Model model) {
        Card[] selectedCards = new Card[2];

        // Gather selected cards
        for (int i = 0; i < 3; i++) {
            int response = view.getNumber("Please enter the number of a card you would like to trade", reader);
            selectedCards[i] = activePlayer.getCards().get(response);
        }
        // Checks cards are all the same type
        if ((selectedCards[0].getTerritory().getName()).equals(selectedCards[1].getTerritory().getName())
                && (selectedCards[0].getTerritory().getName().equals(selectedCards[2].getTerritory().getName()))) {
            awardArmies(model,activePlayer,selectedCards,view,reader);
        } else {
            System.out.println("Error: Cards aren't the same type");
            tradeInCards(activePlayer, reader,view,model);
        }
    }

    public static void awardArmies(Model model, Player activePlayer, Card[] selectedCards, IView view, BufferedReader reader){
        int armiesAwarded = 0;
        int selectedArmies;
        Territory selectedTerritory;
        String playerTerritorySelection = null;

        int cardsTraded = model.getGameState().getCardsTradedIn();
        if (cardsTraded == 0) {
            armiesAwarded = 4;
        } else if (cardsTraded == 1) {
            armiesAwarded = 6;
        } else if (cardsTraded == 2) {
            armiesAwarded = 8;
        } else if (cardsTraded == 3) {
            armiesAwarded = 10;
        } else if (cardsTraded == 4) {
            armiesAwarded = 12;
        } else if (cardsTraded == 5) {
            armiesAwarded = 15;
        } else if (cardsTraded == 6) {
            armiesAwarded = 20;
        } else if (cardsTraded == 7) {
            armiesAwarded = 25;
        } else if (cardsTraded == 8) {
            armiesAwarded = 30;
        }
        cardsTraded++;
        model.getGameState().setCardsTradedIn(cardsTraded);
        for (int i = 0; i < 3; i++) {
            if (activePlayer.getArmies().containsValue(selectedCards[i].getTerritory().getName())) {
                armiesAwarded += 2;
                selectedTerritory = selectedCards[i].getTerritory();
                break;
            }
        }
        while(armiesAwarded>= 0) {
            playerTerritorySelection = view.getInput("What territory would you like to put your armies in?", reader);
            selectedArmies = view.getNumber("How many armies would you like to put on this territory? ", reader);
            if(armiesAwarded<=selectedArmies) {
                armiesAwarded = armiesAwarded - selectedArmies;
                //call reinforce method
            }
        }
    }
}
