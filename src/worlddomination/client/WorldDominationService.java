package worlddomination.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import worlddomination.shared.updates.Update;


@RemoteServiceRelativePath("gameState")
public interface WorldDominationService extends RemoteService {
	
	public void initialiseController(String ipAddress, int port);
	public void initialiseControllerAsHost(boolean shouldJoin);
	public Update getNextUpdate();
	public void sendUpdateResponse(Update update);
	public void leaveGame();
}
