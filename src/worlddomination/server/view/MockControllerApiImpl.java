package worlddomination.server.view;

import java.io.*;

import worlddomination.shared.updates.ClaimTerritory;
import worlddomination.shared.updates.DistributeArmy;
import worlddomination.shared.updates.LocalPlayerName;
import worlddomination.shared.updates.PingReady;
import worlddomination.shared.updates.Update;

public class MockControllerApiImpl implements ControllerApiInterface{
	
	BufferedReader reader;

    boolean manual = false;
	
	public MockControllerApiImpl() {
        try {
            this.reader = new BufferedReader(new FileReader("testscripts/newsetup.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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
		} else if(update instanceof ClaimTerritory) {
			return claim((ClaimTerritory) update);
		} else if(update instanceof DistributeArmy) {
			return distribute((DistributeArmy) update);
		}
		return null;
	}
	
	LocalPlayerName getLocalPlayerName(LocalPlayerName localPlayerName) {
		localPlayerName.setName("Bob");
		return localPlayerName;
	}
	
	PingReady pingReady(PingReady pingReady) {
		pingReady.setIsReady(true);
		return pingReady;
	}
	
	ClaimTerritory claim (ClaimTerritory claim) {
		System.out.println("Choose a territory to claim:");
		claim.setTerritoryID(getInt());
		return claim;
	}
	
	DistributeArmy distribute (DistributeArmy distribute) {
		System.out.println("Choose a territory to distribute armies to:");
		distribute.setTerritoryID(getInt());
		return distribute;
	}
	
	private int getInt() {
		String line;
		try {
			line = reader.readLine();

            if(line == null) {
                reader = new BufferedReader(new InputStreamReader(System.in));
                manual = true;
            }

            if(!manual) {
                System.out.print(line);
            }
			return Integer.parseInt(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return -1;
		
	}
	

}
