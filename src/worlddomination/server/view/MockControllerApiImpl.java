package worlddomination.server.view;

import java.io.*;

import worlddomination.server.actions.Defend;
import worlddomination.shared.updates.*;

public class MockControllerApiImpl implements ControllerApiInterface, GuiApiService {

    BufferedReader reader;

    boolean manual = false;


    MakeTurn[] turn = new MakeTurn[3];

    public MockControllerApiImpl() {
        try {
            this.reader = new BufferedReader(new FileReader("testscripts/newsetup.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initMakeTurn();
    }

    @Override
    public void addUpdate(Update update) {
        // TODO Auto-generated method stub

    }

    @Override
    public Update addUpdateAndWaitForResponse(Update update) {
        if (update instanceof LocalPlayerName) {
            return getLocalPlayerName((LocalPlayerName) update);
        } else if (update instanceof PingReady) {
            return pingReady((PingReady) update);
        } else if (update instanceof ClaimTerritory) {
            return claimTerritory((ClaimTerritory) update);
        } else if (update instanceof DistributeArmy) {
            return distributeArmy((DistributeArmy) update);
        } else if (update instanceof DefendTerritory) {
            return defendTerritory((DefendTerritory) update);
        } else if (update instanceof MakeTurn) {
            return makeTurn((MakeTurn) update);
        } else if (update instanceof Reinforce) {
        	return reinforce((Reinforce)update);
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

    ClaimTerritory claimTerritory(ClaimTerritory claim) {
        System.out.println("Choose a territory to claim:");
        claim.setTerritoryID(getInt());
        return claim;
    }

    DistributeArmy distributeArmy(DistributeArmy distribute) {
        System.out.println("Choose a territory to distribute armies to:");
        distribute.setTerritoryID(getInt());
        return distribute;
    }

    DefendTerritory defendTerritory(DefendTerritory defendTerritory){
        defendTerritory.setArmiesUsed(1);
        return defendTerritory;
    }

    MakeTurn[] turnArray;
    private void initMakeTurn() {
    	turnArray = new MakeTurn[4];
    	
        turnArray[0] = new MakeTurn("", "", false);
        turnArray[0].setType("Attack");
        turnArray[0].setSourceTerritory(9);
        turnArray[0].setDestinationTerritory(8);
        turnArray[0].setNumberOfArmies(1);
        
        turnArray[1] = new MakeTurn("", "", false);
        turnArray[1].setType("Attack");
        turnArray[1].setSourceTerritory(26);
        turnArray[1].setDestinationTerritory(15);
        turnArray[1].setNumberOfArmies(3);
        
        turnArray[2] = new MakeTurn("","",false);
        turnArray[2].setType("Fortify");
        turnArray[2].setSourceTerritory(40);
        turnArray[2].setDestinationTerritory(39);
        turnArray[2].setNumberOfArmies(2);
    }
    
    int i = -1;
    MakeTurn makeTurn(MakeTurn makeTurn) {
    	i++;
        return turnArray[i];
    }
    
    Reinforce reinforce(Reinforce reinforce) {
    	int[] allocationOfArmies =  {9,9,8};
    	reinforce.setAllocationOfArmies(allocationOfArmies);
    	return reinforce;
    }

    private int getInt() {
        String line;
        try {
            line = reader.readLine();

            if (line == null) {
                reader = new BufferedReader(new InputStreamReader(System.in));
                manual = true;
            }

            if (!manual) {
                System.out.print(line+"\n");
            }
            String s = "";
            for (int i = 0; i < line.length(); i++) {
				if(Character.isDigit(line.charAt(i))) {
					s += line.charAt(i);
				}
			}
            return Integer.parseInt(s);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;

    }

    @Override
    public Update getNextUpdate(Update response) {
        // TODO Auto-generated method stub
        return null;
    }


}
