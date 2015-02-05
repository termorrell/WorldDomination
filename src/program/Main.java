package program;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import controller.Controller;
import view.IView;
import view.Input;
import model.Model;

public class Main {	

	public static void main(String[] args) {
		
		Model model = new Model();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		IView view = new Input(reader);
		
		Controller controller = new Controller(model, view);
		controller.run();
		
	}


}
