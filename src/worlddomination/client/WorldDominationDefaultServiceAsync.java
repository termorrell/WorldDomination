package worlddomination.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import worlddomination.shared.updates.Update;


public interface WorldDominationDefaultServiceAsync extends WorldDominationServiceAsync {

	public void initialiseController(String ipAddress, int port, AsyncCallback<Void> callback);
	public void initialiseControllerAsHost(boolean shouldJoin, AsyncCallback<Void> callback);
	public void getNextUpdate(AsyncCallback<Update> callback);
	public void sendUpdateResponse(Update update, AsyncCallback<Void> callback);
	public void leaveGame(AsyncCallback<Void> callback);
}
