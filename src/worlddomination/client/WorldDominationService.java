package worlddomination.client;

import worlddomination.shared.updates.Update;

import com.google.gwt.user.client.rpc.RemoteService;

public interface WorldDominationService extends RemoteService {
	
	public void initialiseController(String ipAddress, int port);
	public void initialiseControllerAsHost(boolean shouldJoin);
	public Update getNextUpdate();
	public void sendUpdateResponse(Update update);
	public void leaveGame();
}
