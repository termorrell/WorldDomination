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

	public int getPort(String message, BufferedReader reader) {
		System.out.println(message);
		int port = 0;
		String input = null;
		try{
			input = reader.readLine();
			try {
				port = Integer.parseInt(input);
				if (port <= 0) {
					getPort("Please enter the port number: ", reader);
				}
			}catch(NumberFormatException e){
				getPort("Please enter a number: ", reader);
			}

		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}

		return port;
	}

	public Move getMove(String message, BufferedReader reader) {
		System.out.println(message);

		String input = null;
		try {
			input = reader.readLine();
		} catch (IOException e) {
			System.err.println("A problem occurred reading input from the console.");
		}

		Move returnMove;

		if (input.equalsIgnoreCase("attack")) {

			returnMove = Move.ATTACK;
		} else if (input.equalsIgnoreCase("fortify")) {

			returnMove = Move.FORTIFY;
		} else if (input.equalsIgnoreCase("defend")) {

			returnMove = Move.DEFEND;
		} else if (input.equalsIgnoreCase("reinforce")) {

			returnMove = Move.REINFORCE;
		}

		return returnMove;
	}
}
