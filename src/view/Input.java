package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input implements IView{
	
	public String getInput(String message) {
		System.out.println(message);
		
		String input = null;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = reader.readLine();
			reader.close();
		} catch (IOException e) {
			System.err.println("A problem occured reading input from the console.");
		}
		return input;
	}
	
}
