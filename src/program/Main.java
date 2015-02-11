package program;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;
import view.IView;
import view.Input;
import model.Model;

public class Main {

	static Logger log = LogManager.getLogger(Main.class.getName());
	public static void main(String[] args) {
		
		Model model = new Model();
		
		BufferedReader reader = getReader(args);
		IView view = new Input(reader);
		Controller controller = new Controller(model, view);
		controller.run();
		
	}
	
	private static BufferedReader getReader(String[] args) {
		BufferedReader reader = null;
		if(args.length == 0) {
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
