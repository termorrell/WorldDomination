package view;

import model.Move;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.BufferedReader;
import java.io.IOException;

public class Input implements IView {

	public String getInput(String message, BufferedReader reader) {
		System.out.println(message);

		String input = null;
		try {
			input = reader.readLine();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}
		return input;
	}

	public int getNumber(String message, BufferedReader reader) {
		System.out.println(message);
		int number = 0;
		String input;
		try{
			input = reader.readLine();
			try {
				number = Integer.parseInt(input);

			}catch(NumberFormatException e){
				getNumber("Please enter a number: ", reader);
			}

		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}

		return number;
	}

	public Move getMove(String message, BufferedReader reader) {
		System.out.println(message);

		String input = null;
		try {
			input = reader.readLine();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}

		Move returnMove = Move.ATTACK;

		if (input.equalsIgnoreCase("fortify")) {

			returnMove = Move.FORTIFY;
		} else if (input.equalsIgnoreCase("defend")) {

			returnMove = Move.DEFEND;
		} else if (input.equalsIgnoreCase("reinforce")) {

			returnMove = Move.REINFORCE;
		}

		return returnMove;
	}
}
