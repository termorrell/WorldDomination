package worlddomination.server.view;

import worlddomination.server.model.Move;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input implements IView {

	BufferedReader reader;

	public Input(BufferedReader reader) {
		this.reader = reader;
	}

	private String readNextLine() {
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (line == null) {
			reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				line = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return line;
	}

	public String getInput(String message) {
		System.out.println(message);

		String input = readNextLine();

		return input;
	}

	public int getNumber(String message) {
		System.out.println(message);
		int number;
		String input = readNextLine();
		try {
			number = Integer.parseInt(input);

		} catch (NumberFormatException e) {
			return getNumber("Please enter a number: ");
		}
		return number;
	}

	public boolean getBoolean(String message) {
		System.out.println(message);
		String input;
		boolean responseBoolean = false;
		boolean validInput = false;
		while (!validInput) {
			input = readNextLine();
			if (input.equalsIgnoreCase("yes")) {
				responseBoolean = true;
				validInput = true;
			} else if (input.equalsIgnoreCase("no")) {
				validInput = true;
			}
		}
		return responseBoolean;
	}

	public Move getMove(String message) {
		System.out.println(message);

		String input = readNextLine();

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
