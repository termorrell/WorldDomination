package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import updates.Claim;
import updates.Distribute;
import updates.LocalPlayerName;
import updates.PingReady;
import updates.Update;

public class MockControllerApiImpl implements ControllerApiInterface{
	
	BufferedReader reader;
	
	public MockControllerApiImpl() {
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void addUpdate(Update update) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Update addUpdateAndWaitForResponse(Update update) {
		if(update instanceof LocalPlayerName) {
			return getLocalPlayerName((LocalPlayerName) update);
		} else if(update instanceof PingReady) {
			return pingReady((PingReady) update);
		} else if(update instanceof Claim) {
			return claim((Claim) update);
		} else if(update instanceof Distribute) {
			return distribute((Distribute) update);
		}
		return null;
	}
	
	LocalPlayerName getLocalPlayerName(LocalPlayerName localPlayerName) {
		localPlayerName.setName("Bob");
		return localPlayerName;
	}
	
	PingReady pingReady(PingReady pingReady) {
		pingReady.setReady(true);
		return pingReady;
	}
	
	Claim claim (Claim claim) {
		System.out.println("Choose a territory to claim:");
		claim.setTerritoryId(getInt());
		return claim;
	}
	
	Distribute distribute (Distribute distribute) {
		System.out.println("Choose a territory to distribute armies to:");
		distribute.setTerritoryId(getInt());
		return distribute;
	}
	
	private int getInt() {
		String line;
		try {
			line = reader.readLine();
			return Integer.parseInt(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
		
	}
	

}
