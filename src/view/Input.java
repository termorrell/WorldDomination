package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
}
