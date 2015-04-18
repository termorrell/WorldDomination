package worlddomination.server.view;

import worlddomination.server.model.Move;

import java.io.BufferedReader;

public interface IView {
	public String getInput(String message);
	public int getNumber(String message);
	public boolean getBoolean(String message);
	public Move getMove(String message);
}
