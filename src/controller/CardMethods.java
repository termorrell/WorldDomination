package controller;


import model.*;
import view.IView;

import java.io.BufferedReader;

/**
 * Created by ${mm280} on 03/02/15.
 */
public class CardMethods {
    //Called at end of each turn if territory gained
    // TODO SHUFFLE CARDS?
    // TODO change attack method to return boolean?
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
        //Check you can trade in cards
        if(verifyCardTrade(selectedCards)){
            awardArmies(model,activePlayer,selectedCards,view,reader);
        }else{
            System.out.println("Those cards can't be traded");
            cardTrader(activePlayer,reader,view,model);
        }
    }

    public static boolean verifyCardTrade(Card[] selectedCards) {
        String currentType = null;
        if(checkOneWildCard(selectedCards)){
            return true;
        }else if(checkAllSameType(selectedCards)){
            return true;
        }else if(checkAllDifferentType(selectedCards)){
            return true;
        }else{
            System.out.println("ERROR: Cards aren't the same");
        }
        return false;

    }

    public static boolean checkOneWildCard(Card[] selectedCards){
        int wildCardNo =0;
        for(int i=0;i<selectedCards.length;i++){
            if(selectedCards[i].getType().equals("Wild")){
                wildCardNo++;
            }
        }
        return wildCardNo == 1;
    }
    public static boolean checkAllSameType(Card[] selectedCards){

        return selectedCards[0].getType().equals(selectedCards[1].getType())
                && selectedCards[0].getType().equals(selectedCards[2].getType());
    }

    public static boolean checkAllDifferentType(Card[] selectedCards){
        String cardType = selectedCards[0].getType();
        if(checkOneWildCard(selectedCards)){
            return false;
        }
        if(!cardType.equals(selectedCards[1].getType()) && !cardType.equals(selectedCards[2].getType())){
            if(!selectedCards[2].getType().equals(selectedCards[1].getType())){
                return true;
            }
        }
        return false;
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
