package worlddomination.server.controller;

import java.io.BufferedReader;
import java.util.LinkedList;

import worlddomination.server.factories.BoardFactory;
import worlddomination.server.factories.CardFactory;
import worlddomination.server.model.Board;
import worlddomination.server.model.Card;

public class DataManager {

    public static Board getBoard() {
        BoardFactory boardFactory = new BoardFactory();
        return boardFactory.getBoard();
    }

    public static LinkedList<Card> getCards(Board board) {
        CardFactory cardFactory = new CardFactory(board);
        return cardFactory.getCards();
    }

}
