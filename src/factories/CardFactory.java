package factories;

import java.util.LinkedList;

import model.Board;
import model.Card;
import model.GameState;

public class CardFactory {
    LinkedList<Card> cards;
    Board board;
    int numOfWildCards = 2;
    int numOfCards;

    public CardFactory(Board board) {
        this.board = board;
        numOfCards = numOfWildCards + board.getNumberOfTerritories();
        this.cards = new LinkedList<Card>();
    }

    private void initCards() {
        for (int i = 0; i < board.getNumberOfTerritories(); i++) {
            String territoryName = board.getTerritories()[i].getName();
            //Create infantry cards
            if (territoryName.equals("Brazil") || territoryName.equals("Southern Europe")
                    || territoryName.equals("Scandinavia") || territoryName.equals("Western Europe")
                    || territoryName.equals("Ukraine") || territoryName.equals("Great Britain")
                    || territoryName.equals("Indonesia") || territoryName.equals("North Africa")
                    || territoryName.equals("East Africa") || territoryName.equals("Afghanistan")
                    || territoryName.equals("Ural") || territoryName.equals("Siberia")
                    || territoryName.equals("Central America") || territoryName.equals("Argentina")) {
                cards.add(new Card(i,board.getTerritories()[i],"Infantry", false));

                //Create cavalry cards
            } else if (territoryName.equals("Peru") || territoryName.equals("New Guinea")
                    || territoryName.equals("Eastern Australia") || territoryName.equals("Madagascar")
                    || territoryName.equals("Northern Europe") || territoryName.equals("Iceland")
                    || territoryName.equals("Egypt") || territoryName.equals("Northwest territory")
                    || territoryName.equals("India") || territoryName.equals("Japan")
                    || territoryName.equals("Mongolia") || territoryName.equals("Kamchatka")
                    || territoryName.equals("Greenland") || territoryName.equals("Siam")) {
                cards.add(new Card(i,board.getTerritories()[i],"Cavalry",false));

                // Create artillery cards
            } else if (territoryName.equals("Venezuela") || territoryName.equals("Western Australia")
                    || territoryName.equals("Congo") || territoryName.equals("South Africa")
                    || territoryName.equals("Middle East") || territoryName.equals("China")
                    || territoryName.equals("Alaska") || territoryName.equals("Alberta")
                    || territoryName.equals("Western United States") || territoryName.equals("Eastern United States")
                    || territoryName.equals("Irkutsk") || territoryName.equals("Ontario")
                    || territoryName.equals("Quebec") || territoryName.equals("Yakutsk")) {
                cards.add(new Card(i,board.getTerritories()[i],"Artillery",false));

            } else {
                System.out.println("Invalid territory " + territoryName);
            }
        }
        for(int i = board.getNumberOfTerritories(); i<numOfCards; i++){
            cards.add(new Card(i, null, "Wild",false));
        }
    }

    public LinkedList<Card> getCards() {
        initCards();
        return cards;
    }
}
