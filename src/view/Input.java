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

	public boolean getBoolean(String message, BufferedReader reader) {
		System.out.println(message);
		int number = 0;
		String input;
		boolean responseBoolean = false;
		boolean validInput = false;
		while (!validInput) {
			try {
				input = reader.readLine();
				if (input.equalsIgnoreCase("yes")) {
					responseBoolean = true;
					validInput = true;
				} else if (input.equalsIgnoreCase("no")) {
					validInput = true;
				}
			} catch (IOException e) {
				System.err.println("A problem occurred reading input from the console.");
			}
		}

		return responseBoolean;
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


		if (input.equalsIgnoreCase("attack")) {

			returnMove = Move.ATTACK;
		} else if (input.equalsIgnoreCase("fortify")) {

			returnMove = Move.FORTIFY;
		} else if (input.equalsIgnoreCase("defend")) {

			returnMove = Move.DEFEND;
		} else if (input.equalsIgnoreCase("reinforce")) {

			returnMove = Move.REINFORCE;
		} else {

			// TODO: Need to throw an exception for an invalid move type
		}

		return returnMove;
	}
}
