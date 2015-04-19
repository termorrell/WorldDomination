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

    MakeTurn makeTurn(MakeTurn makeTurn) {
        return makeTurn;
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
                System.out.print(line);
            }
            return Integer.parseInt(line);
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
