package view;

import updates.LocalPlayerName;
import updates.Update;

public class MockControllerApiImpl implements ControllerApiInterface{

	@Override
	public void addUpdate(Update update) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Update addUpdateAndWaitForResponse(Update update) {
		if(update instanceof LocalPlayerName) {
			return getLocalPlayerName((LocalPlayerName) update);
		}
		return null;
	}
	
	LocalPlayerName getLocalPlayerName(LocalPlayerName localPlayerName) {
		localPlayerName.setName("Bob");
		return localPlayerName;
	}
	
	

}
