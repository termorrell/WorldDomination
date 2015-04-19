package worlddomination.client;

import worlddomination.shared.updates.Update;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("aiGameState")
public interface WorldDominationAIService extends RemoteService, WorldDominationService {

	public void initialiseController(String ipAddress, int port);
	public void initialiseControllerAsHost(boolean shouldJoin);
	public Update getNextUpdate();
	public void sendUpdateResponse(Update update);
	public void leaveGame();
}
