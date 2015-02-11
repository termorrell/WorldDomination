package tests;
import exceptions.BoardException;
import exceptions.IllegalMoveException;
import factories.BoardFactory;
import model.*;
import controller.*;
import view.IView;
import org.junit.*;
import view.Input;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BoardSetupTest {

        Model model;
        Controller controller;
        @Before
        public void setUp() throws Exception {

            model = new Model();

            ArrayList<Player> players = new ArrayList<Player>();
            for (int i = 0; i < 4; i++) {
                players.add(new Player());
            }

            model.getGameState().setPlayers(players);
            model.getGameState().setNumberOfPlayers(players.size());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            IView view = new Input(reader);
            // Set board
            BoardFactory boardFactory = new BoardFactory();
            model.getGameState().setBoard(boardFactory.getBoard());
            controller = new Controller(model, view);
        }

        /**Check 42 territories created*/
        @Test
        public void checkAllTerritoriesCreated(){
            assertEquals(42, model.getGameState().getBoard().getNumberOfTerritories());
            assertEquals(model.getGameState().getBoard().getNumberOfTerritories(), model.getGameState().getBoard().getTerritories().length);
        }

        /** Check continents contain territories*/
        @Test
        public void checkAllContinentsCreated(){
            assertEquals(6,model.getGameState().getBoard().getNumberOfContinent());
            assertEquals(model.getGameState().getBoard().getNumberOfContinent(),model.getGameState().getBoard().getContinents().length);

        }

        @Test
        public void checkTerritoryOwnerMethodCorrect() throws IllegalMoveException, BoardException{
            assertFalse(controller.checkForUnclaimedTerritories());
            Moves.reinforce(model.getGameState().getPlayers().get(0),model.getGameState(),model.getGameState().getBoard().getTerritories()[0].getId(),1);
            assertFalse(controller.checkForUnclaimedTerritories());
            for(int i=0;i<model.getGameState().getBoard().getNumberOfTerritories();i++){
                Moves.reinforce(model.getGameState().getPlayers().get(0),model.getGameState(),model.getGameState().getBoard().getTerritories()[i].getId(),1);
            }
            assertTrue(controller.checkForUnclaimedTerritories());
        }

        @Test
        public void checkCantReinforceTerritoryTwice()throws BoardException{
            try {
                Moves.reinforce(model.getGameState().getPlayers().get(0), model.getGameState(), model.getGameState().getBoard().getTerritories()[0].getId(), 1);
                Moves.reinforce(model.getGameState().getPlayers().get(1), model.getGameState(), model.getGameState().getBoard().getTerritories()[0].getId(), 1);
            }catch(IllegalMoveException e){
                assertTrue(true);
            }
        }

        @Test
        public void checkInitialArmiesAwardedCorrect(){
            assertEquals(30,controller.calculateNumberOfArmies());
            model.getGameState().setNumberOfPlayers(6);
            assertEquals(20,controller.calculateNumberOfArmies());
        }

        @Test
        public void checkNumberOfArmiesAwarded() throws IllegalMoveException,BoardException {
            assertEquals(3, controller.calculateNumberOfArmies(model.getGameState().getPlayers().get(0), model.getGameState()));
            for (int i = 0; i < 8; i++) {
                Moves.reinforce(model.getGameState().getPlayers().get(0), model.getGameState(), model.getGameState().getBoard().getTerritories()[i].getId(), 1);
            }
            assertEquals(3,controller.calculateNumberOfArmies(model.getGameState().getPlayers().get(0),model.getGameState()));
            for (int i = 0; i < 9; i++) {
                Moves.reinforce(model.getGameState().getPlayers().get(0), model.getGameState(), model.getGameState().getBoard().getTerritories()[i].getId(), 1);
            }
            assertEquals(8,controller.calculateNumberOfArmies(model.getGameState().getPlayers().get(0),model.getGameState()));
        }


    }