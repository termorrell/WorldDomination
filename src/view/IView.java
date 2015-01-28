package view;

import java.io.BufferedReader;

public interface IView {
	public String getInput(String message, BufferedReader reader);
	public int getPort(String message, BufferedReader reader);
}
