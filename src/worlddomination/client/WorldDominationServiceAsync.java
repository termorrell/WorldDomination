package worlddomination.client;

import worlddomination.shared.updates.Update;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorldDominationServiceAsync {

	public void initialiseController(String ipAddress, int port, AsyncCallback<Void> callback);
	public void initialiseControllerAsHost(boolean shouldJoin, AsyncCallback<Void> callback);
	public void getNextUpdate(AsyncCallback<Update> callback);
	public void sendUpdateResponse(Update update, AsyncCallback<Void> callback);
	public void leaveGame(AsyncCallback<Void> callback);
}
