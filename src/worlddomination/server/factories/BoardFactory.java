package worlddomination.server.factories;

import worlddomination.server.model.Board;
import worlddomination.server.model.Continent;
import worlddomination.server.model.Territory;

public class BoardFactory {

    private Board defaultBoard = null;

    public BoardFactory() {
        initDefaultBoard();
    }

    public Board getBoard() {
        return defaultBoard;
    }

    private void initDefaultBoard() {
        defaultBoard = new Board(6, 42);

        String[] northAmericanTerritoryNames = {"Alaska",
                "Northwest territory", "Greenland", "Alberta", "Ontario",
                "Quebec", "Western United States", "Eastern United States",
                "Central America"};
        initContinent(0, "North America", northAmericanTerritoryNames, 5, 0,
                defaultBoard);

        String[] southAmericanTerritoryNames = {"Venezuela", "Peru", "Brazil",
                "Argentina"};
        initContinent(1, "South America", southAmericanTerritoryNames, 2, 9,
                defaultBoard);

        String[] europeanTerritoryNames = {"Iceland", "Scandinavia",
                "Ukraine", "Great Britain", "Northern Europe",
                "Western Europe", "Southern Europe"};
        initContinent(2, "Europe", europeanTerritoryNames, 5, 13, defaultBoard);

        String[] africanTerritoryNames = {"North Africa", "Egypt", "Congo",
                "East Africa", "South Africa", "Madagascar"};
        initContinent(3, "Africa", africanTerritoryNames, 3, 20, defaultBoard);

        String[] asianTerritoryNames = {"Ural", "Siberia", "Yakutsk",
                "Kamchatka", "Irkutsk", "Mongolia", "Japan", "Afghanistan",
                "China", "Middle East", "India", "Siam"};
        initContinent(4, "Asia", asianTerritoryNames, 7, 26, defaultBoard);

        String[] australianTerritoryNames = {"Indonesia", "New Guinea",
                "Western Australia", "Eastern Australia"};
        initContinent(5, "Australia", australianTerritoryNames, 2, 38,
                defaultBoard);

        setNeighbouringTerritories();
    }

    private void initContinent(int continentId, String name,
                               String[] territories, int reinforcementArmies, int firstTerritoryId, Board board) {

        int numberOfTerritories = territories.length;
        board.getContinents()[continentId] = new Continent(continentId, name,
                new Territory[numberOfTerritories], reinforcementArmies);

        for (int i = 0; i < territories.length; i++) {
            board.getTerritories()[i + firstTerritoryId] = new Territory(i
                    + firstTerritoryId, territories[i],
                    board.getContinents()[continentId]);
            board.getContinents()[continentId].getTerritories()[i] = defaultBoard
                    .getTerritories()[i + firstTerritoryId];
        }
    }

    private void setNeighbouringTerritories() {
        // Alaska
        Territory[] neighbours0 = {defaultBoard.getTerritories()[1],
                defaultBoard.getTerritories()[3],
                defaultBoard.getTerritories()[29]};
        defaultBoard.getTerritories()[0].setNeighbours(neighbours0);

        // Northwest territory
        Territory[] neighbours1 = {defaultBoard.getTerritories()[0],
                defaultBoard.getTerritories()[2],
                defaultBoard.getTerritories()[3],
                defaultBoard.getTerritories()[4]};
        defaultBoard.getTerritories()[1].setNeighbours(neighbours1);

        // Greenland
        Territory[] neighbours2 = {defaultBoard.getTerritories()[1],
                defaultBoard.getTerritories()[4],
                defaultBoard.getTerritories()[5],
                defaultBoard.getTerritories()[13]};
        defaultBoard.getTerritories()[2].setNeighbours(neighbours2);

        // Alberta
        Territory[] neighbours3 = {defaultBoard.getTerritories()[0],
                defaultBoard.getTerritories()[1],
                defaultBoard.getTerritories()[4],
                defaultBoard.getTerritories()[6]};
        defaultBoard.getTerritories()[3].setNeighbours(neighbours3);

        // Ontario
        Territory[] neighbours4 = {defaultBoard.getTerritories()[1],
                defaultBoard.getTerritories()[2],
                defaultBoard.getTerritories()[3],
                defaultBoard.getTerritories()[6],
                defaultBoard.getTerritories()[7]};
        defaultBoard.getTerritories()[4].setNeighbours(neighbours4);

        // Quebec
        Territory[] neighbours5 = {defaultBoard.getTerritories()[2],
                defaultBoard.getTerritories()[4],
                defaultBoard.getTerritories()[7]};
        defaultBoard.getTerritories()[5].setNeighbours(neighbours5);

        // Western United States
        Territory[] neighbours6 = {defaultBoard.getTerritories()[3],
                defaultBoard.getTerritories()[4],
                defaultBoard.getTerritories()[7],
                defaultBoard.getTerritories()[8]};
        defaultBoard.getTerritories()[6].setNeighbours(neighbours6);

        // Eastern United States
        Territory[] neighbours7 = {defaultBoard.getTerritories()[4],
                defaultBoard.getTerritories()[5],
                defaultBoard.getTerritories()[6],
                defaultBoard.getTerritories()[8]};
        defaultBoard.getTerritories()[7].setNeighbours(neighbours7);

        // Central America
        Territory[] neighbours8 = {defaultBoard.getTerritories()[6],
                defaultBoard.getTerritories()[7],
                defaultBoard.getTerritories()[9]};
        defaultBoard.getTerritories()[8].setNeighbours(neighbours8);

        // Venezuela
        Territory[] neighbours9 = {defaultBoard.getTerritories()[8],
                defaultBoard.getTerritories()[10],
                defaultBoard.getTerritories()[11]};
        defaultBoard.getTerritories()[9].setNeighbours(neighbours9);

        // Peru
        Territory[] neighbours10 = {defaultBoard.getTerritories()[9],
                defaultBoard.getTerritories()[11],
                defaultBoard.getTerritories()[12]};
        defaultBoard.getTerritories()[10].setNeighbours(neighbours10);

        // Brazil
        Territory[] neighbours11 = {defaultBoard.getTerritories()[9],
                defaultBoard.getTerritories()[10],
                defaultBoard.getTerritories()[12],
                defaultBoard.getTerritories()[20]};
        defaultBoard.getTerritories()[11].setNeighbours(neighbours11);

        // Argentina
        Territory[] neighbours12 = {defaultBoard.getTerritories()[10],
                defaultBoard.getTerritories()[11]};
        defaultBoard.getTerritories()[12].setNeighbours(neighbours12);

        // Iceland
        Territory[] neighbours13 = {defaultBoard.getTerritories()[2],
                defaultBoard.getTerritories()[14],
                defaultBoard.getTerritories()[16]};
        defaultBoard.getTerritories()[13].setNeighbours(neighbours13);

        // Scandinavia
        Territory[] neighbours14 = {defaultBoard.getTerritories()[13],
                defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[16],
                defaultBoard.getTerritories()[17]};
        defaultBoard.getTerritories()[14].setNeighbours(neighbours14);

        // Ukraine
        Territory[] neighbours15 = {defaultBoard.getTerritories()[14],
                defaultBoard.getTerritories()[17],
                defaultBoard.getTerritories()[19],
                defaultBoard.getTerritories()[26],
                defaultBoard.getTerritories()[33],
                defaultBoard.getTerritories()[35]};
        defaultBoard.getTerritories()[15].setNeighbours(neighbours15);

        // Great Britain
        Territory[] neighbours16 = {defaultBoard.getTerritories()[13],
                defaultBoard.getTerritories()[14],
                defaultBoard.getTerritories()[17],
                defaultBoard.getTerritories()[18]};
        defaultBoard.getTerritories()[16].setNeighbours(neighbours16);

        // Northern Europe
        Territory[] neighbours17 = {defaultBoard.getTerritories()[14],
                defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[16],
                defaultBoard.getTerritories()[18],
                defaultBoard.getTerritories()[19]};
        defaultBoard.getTerritories()[17].setNeighbours(neighbours17);

        // Western Europe
        Territory[] neighbours18 = {defaultBoard.getTerritories()[16],
                defaultBoard.getTerritories()[17],
                defaultBoard.getTerritories()[19],
                defaultBoard.getTerritories()[20]};
        defaultBoard.getTerritories()[18].setNeighbours(neighbours18);

        // Southern Europe
        Territory[] neighbours19 = {defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[17],
                defaultBoard.getTerritories()[18],
                defaultBoard.getTerritories()[20],
                defaultBoard.getTerritories()[21],
                defaultBoard.getTerritories()[35]};
        defaultBoard.getTerritories()[19].setNeighbours(neighbours19);

        // Northern Africa
        Territory[] neighbours20 = {defaultBoard.getTerritories()[11],
                defaultBoard.getTerritories()[18],
                defaultBoard.getTerritories()[19],
                defaultBoard.getTerritories()[21],
                defaultBoard.getTerritories()[22],
                defaultBoard.getTerritories()[23]};
        defaultBoard.getTerritories()[20].setNeighbours(neighbours20);

        // Egypt
        Territory[] neighbours21 = {defaultBoard.getTerritories()[19],
                defaultBoard.getTerritories()[20],
                defaultBoard.getTerritories()[23],
                defaultBoard.getTerritories()[35]};
        defaultBoard.getTerritories()[21].setNeighbours(neighbours21);

        // Congo
        Territory[] neighbours22 = {defaultBoard.getTerritories()[20],
                defaultBoard.getTerritories()[23],
                defaultBoard.getTerritories()[24]};
        defaultBoard.getTerritories()[22].setNeighbours(neighbours22);

        // East Africa
        Territory[] neighbours23 = {defaultBoard.getTerritories()[20],
                defaultBoard.getTerritories()[21],
                defaultBoard.getTerritories()[22],
                defaultBoard.getTerritories()[24],
                defaultBoard.getTerritories()[25],
                defaultBoard.getTerritories()[35]};
        defaultBoard.getTerritories()[23].setNeighbours(neighbours23);

        // South Africa
        Territory[] neighbours24 = {defaultBoard.getTerritories()[22],
                defaultBoard.getTerritories()[23],
                defaultBoard.getTerritories()[25]};
        defaultBoard.getTerritories()[24].setNeighbours(neighbours24);

        // Madagaskar
        Territory[] neighbours25 = {defaultBoard.getTerritories()[23],
                defaultBoard.getTerritories()[24]};
        defaultBoard.getTerritories()[25].setNeighbours(neighbours25);

        // Ural
        Territory[] neighbours26 = {defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[27],
                defaultBoard.getTerritories()[33],
                defaultBoard.getTerritories()[34]};
        defaultBoard.getTerritories()[26].setNeighbours(neighbours26);

        // Siberia
        Territory[] neighbours27 = {defaultBoard.getTerritories()[26],
                defaultBoard.getTerritories()[28],
                defaultBoard.getTerritories()[30],
                defaultBoard.getTerritories()[31],
                defaultBoard.getTerritories()[34]};
        defaultBoard.getTerritories()[27].setNeighbours(neighbours27);

        // Yakutsk
        Territory[] neighbours28 = {defaultBoard.getTerritories()[27],
                defaultBoard.getTerritories()[29],
                defaultBoard.getTerritories()[30]};
        defaultBoard.getTerritories()[28].setNeighbours(neighbours28);

        // Kamichatka
        Territory[] neighbours29 = {defaultBoard.getTerritories()[0],
                defaultBoard.getTerritories()[28],
                defaultBoard.getTerritories()[30],
                defaultBoard.getTerritories()[31],
                defaultBoard.getTerritories()[32]};
        defaultBoard.getTerritories()[29].setNeighbours(neighbours29);

        // Irkutsk
        Territory[] neighbours30 = {defaultBoard.getTerritories()[27],
                defaultBoard.getTerritories()[28],
                defaultBoard.getTerritories()[29],
                defaultBoard.getTerritories()[31],
                defaultBoard.getTerritories()[32]};
        defaultBoard.getTerritories()[30].setNeighbours(neighbours30);

        // Mongolia
        Territory[] neighbours31 = {defaultBoard.getTerritories()[27],
                defaultBoard.getTerritories()[29],
                defaultBoard.getTerritories()[30],
                defaultBoard.getTerritories()[34]};
        defaultBoard.getTerritories()[31].setNeighbours(neighbours31);

        // Japan
        Territory[] neighbours32 = {defaultBoard.getTerritories()[29],
                defaultBoard.getTerritories()[31]};
        defaultBoard.getTerritories()[32].setNeighbours(neighbours32);

        // Afghanistan
        Territory[] neighbours33 = {defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[26],
                defaultBoard.getTerritories()[34],
                defaultBoard.getTerritories()[35],
                defaultBoard.getTerritories()[36]};
        defaultBoard.getTerritories()[33].setNeighbours(neighbours33);

        // China
        Territory[] neighbours34 = {defaultBoard.getTerritories()[26],
                defaultBoard.getTerritories()[27],
                defaultBoard.getTerritories()[31],
                defaultBoard.getTerritories()[33],
                defaultBoard.getTerritories()[36],
                defaultBoard.getTerritories()[37]};
        defaultBoard.getTerritories()[34].setNeighbours(neighbours34);

        // Middle East
        Territory[] neighbours35 = {defaultBoard.getTerritories()[15],
                defaultBoard.getTerritories()[19],
                defaultBoard.getTerritories()[21],
                defaultBoard.getTerritories()[23],
                defaultBoard.getTerritories()[33],
                defaultBoard.getTerritories()[36]};
        defaultBoard.getTerritories()[35].setNeighbours(neighbours35);

        // India
        Territory[] neighbours36 = {defaultBoard.getTerritories()[33],
                defaultBoard.getTerritories()[34],
                defaultBoard.getTerritories()[35],
                defaultBoard.getTerritories()[37]};
        defaultBoard.getTerritories()[36].setNeighbours(neighbours36);

        // Siam
        Territory[] neighbours37 = {defaultBoard.getTerritories()[34],
                defaultBoard.getTerritories()[35],
                defaultBoard.getTerritories()[38]};
        defaultBoard.getTerritories()[37].setNeighbours(neighbours37);

        // Indonisia
        Territory[] neighbours38 = {defaultBoard.getTerritories()[37],
                defaultBoard.getTerritories()[39],
                defaultBoard.getTerritories()[40]};
        defaultBoard.getTerritories()[38].setNeighbours(neighbours38);

        // New Guinea
        Territory[] neighbours39 = {defaultBoard.getTerritories()[38],
                defaultBoard.getTerritories()[40],
                defaultBoard.getTerritories()[41]};
        defaultBoard.getTerritories()[39].setNeighbours(neighbours39);

        // Western Australia
        Territory[] neighbours40 = {defaultBoard.getTerritories()[38],
                defaultBoard.getTerritories()[39],
                defaultBoard.getTerritories()[41]};
        defaultBoard.getTerritories()[40].setNeighbours(neighbours40);

        // Eastern Australia
        Territory[] neighbours41 = {defaultBoard.getTerritories()[39],
                defaultBoard.getTerritories()[40]};
        defaultBoard.getTerritories()[41].setNeighbours(neighbours41);

    }
}
