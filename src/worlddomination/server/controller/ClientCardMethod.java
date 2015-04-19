package worlddomination.server.controller;

import worlddomination.server.exceptions.BoardException;
import worlddomination.server.exceptions.IllegalMoveException;
import worlddomination.server.model.Card;
import worlddomination.server.model.Model;
import worlddomination.server.model.Player;

import java.util.LinkedList;

/**
 * Created by 120011588 on 18/04/15.
 */
public class ClientCardMethod {

    public static void collectCard(Player activePlayer, Model model) {
        for (int i = 0; i < model.getGameState().getCards().size(); i++) {
            if (!model.getGameState().getCards().get(i).isAssigned()) {
                activePlayer.getCards().add(model.getGameState().getCards().get(i));
                model.getGameState().getCards().get(i).setAssigned(true);
                break;
            }
        }
    }

    /**
     * Trades in cards
     *
     * @param activePlayer  player trading in cards
     * @param selectedCards 3cards they want to trade
     * @param model         model of info
     * @return number of armies awarded, -1 if invalid trade
     * @throws BoardException
     * @throws IllegalMoveException
     */
    public static int tradeInCards(Player activePlayer, Card[] selectedCards, Model model) throws BoardException, IllegalMoveException {
        int playerCards = activePlayer.getCards().size();
        int armiesAwarded;
        if (verifyCardTrade(selectedCards)) {
            for (Card card : selectedCards) {
                activePlayer.getCards().remove(card);
            }
            armiesAwarded = calculateArmiesAwarded(model);
        } else {
            armiesAwarded = -1;
        }
        return armiesAwarded;
    }

    /**
     * Check if there is a valid card trade
     * @param activePlayer player to check cards against
     * @param model model of gamestate
     * @return true if cards can be traded in
     */
    public static boolean canTradeInCards(Player activePlayer, Model model) {
        LinkedList<Card> playerCards = activePlayer.getCards();
        Card[] selectedCards = new Card[3];
        if (playerCards.size() >= 3) {
            for (int i = 0; i < playerCards.size() - 2; i++) {
                for (int y = i+1; y < playerCards.size() - 1; y++) {
                    for (int k = y+1; k < playerCards.size(); k++) {
                        selectedCards[0] = playerCards.get(i);
                        selectedCards[1] = playerCards.get(y);
                        selectedCards[2] = playerCards.get(k);
                        if (verifyCardTrade(selectedCards)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            return false;
        }
        return false;
    }

    public static boolean verifyCardTrade(Card[] selectedCards) {
        if (selectedCards.length == 3) {
            if (checkOneWildCard(selectedCards)) {
                return true;
            } else if (checkAllSameType(selectedCards)) {
                return true;
            } else return checkAllDifferentType(selectedCards);
        }
        return false;
    }

    public static boolean checkOneWildCard(Card[] selectedCards) {
        int wildCardNo = 0;
        for (Card selectedCard : selectedCards) {
            if (selectedCard.getType().equals("Wild")) {
                wildCardNo++;
            }
        }
        return wildCardNo == 1;
    }

    public static boolean checkAllSameType(Card[] selectedCards) {

        return selectedCards[0].getType().equals(selectedCards[1].getType()) && selectedCards[0].getType().equals(selectedCards[2].getType());
    }

    public static boolean checkAllDifferentType(Card[] selectedCards) {
        String cardType = selectedCards[0].getType();
        if (checkOneWildCard(selectedCards)) {
            return false;
        }
        if (!cardType.equals(selectedCards[1].getType()) && !cardType.equals(selectedCards[2].getType())) {
            if (!selectedCards[2].getType().equals(selectedCards[1].getType())) {
                return true;
            }
        }
        return false;
    }

    public static int calculateArmiesAwarded(Model model) {
        int armiesAwarded = 2;
        int cardsTraded = model.getGameState().getCardsTradedIn();
        for (int i = 0; i <= cardsTraded; i++) {
            if (i <= 4) {
                armiesAwarded += 2;
            } else if (i == 5) {
                armiesAwarded += 3;
            } else if (i > 5) {
                armiesAwarded += 5;
            }
        }
        cardsTraded++;
        model.getGameState().setCardsTradedIn(cardsTraded);
        return armiesAwarded;
    }
}
