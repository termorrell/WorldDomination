package worlddomination.server.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;

public class MockNetworkView implements INetworkView{
	
	BufferedReader reader;
	
	public MockNetworkView() {
		reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public String getLocalPlayerName() {
		System.out.println("Please enter name:");
		return readString();
	}

	@Override
	public void displayRejection(String message) {
		System.out.println(message);
	}

	@Override
	public void displayJoinedPlayers(Map<Integer, String[]> players) {
		for(Entry<Integer, String[]> entry : players.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue()[0] + "\t\t" + entry.getValue()[1]);
		}
		
	}

	@Override
	public boolean getPingReadyConfirmation() {
		System.out.println("Are you ready?");
		
		return readBoolean();
	}
	
	private String readString() {
		try {
			String line = reader.readLine();
			return line;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	private boolean readBoolean() {
		String line = readString();
		if(line.equalsIgnoreCase("y") || line.equals("yes")) {
			return true;
		} else {
			return false;
		}
	}

}
