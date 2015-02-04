package view;

import java.io.BufferedReader;

public interface IView {
	public String getInput(String message, BufferedReader reader);
	public int getNumber(String message, BufferedReader reader);
	public boolean getBoolean(String message, BufferedReader reader);
}
