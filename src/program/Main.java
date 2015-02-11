package program;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import controller.Controller;
import view.IView;
import view.Input;
import model.Model;

public class Main {	

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
		} else {
			try {
				reader = new BufferedReader(new FileReader(args[0]));
			} catch (FileNotFoundException e) {
				System.err.println("The file containing the test input couldn't be opened.");
			}
		}
		return reader;
	}


}
