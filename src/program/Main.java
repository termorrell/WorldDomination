package program;

import controller.Controller;
import view.IView;
import view.Input;
import model.Model;

public class Main {	

	public static void main(String[] args) {
		
		Model model = new Model();
		IView view = new Input();
		Controller controller = new Controller(model, view);
		
		controller.run();
		
	}


}
