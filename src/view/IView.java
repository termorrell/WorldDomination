package view;

import model.Move;

import java.io.BufferedReader;

public interface IView {
	public String getInput(String message, BufferedReader reader);
	public int getNumber(String message, BufferedReader reader);
	public boolean getBoolean(String message, BufferedReader reader);
	public Move getMove(String message, BufferedReader reader);
}
