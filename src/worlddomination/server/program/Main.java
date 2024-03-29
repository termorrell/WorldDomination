package worlddomination.server.program;


import worlddomination.server.controller.ClientController;
import worlddomination.server.controller.Controller;
import worlddomination.server.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import worlddomination.server.view.INetworkView;
import worlddomination.server.view.IView;
import worlddomination.server.view.Input;
import worlddomination.server.view.MockNetworkView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

public class Main {

    static Logger log = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {

        startServer();

        newController();

        // TODO call startServer if program is supposed to act as server
    }

    private static void newController() {
//        INetworkView view = new MockNetworkView();
//        ClientController controller = new ClientController(view);
//        controller.run();
    }

    private static void startServer() {
//        try {
//            //RiskServer server = new RiskServer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void oldController() {
        Model model = new Model();

        String[] args = {};
        BufferedReader reader = getReader(args);
        IView view = new Input(reader);
        Controller controller = new Controller(model, view);
        controller.run();
    }

    private static BufferedReader getReader(String[] args) {
        BufferedReader reader = null;
        if (args.length == 0) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            log.info("Using user input for initialisation");
        } else {
            try {
                reader = new BufferedReader(new FileReader(args[0]));
                log.info("Using game test input file");
            } catch (FileNotFoundException e) {
                System.err.println("The file containing the test input couldn't be opened.");
                log.error("File Not Located during setup", e);
            }
        }
        return reader;
    }


}
