package worlddomination.server;

import java.util.LinkedList;
import java.util.Queue;

import worlddomination.client.WorldDominationAIService;
import worlddomination.server.controller.ClientController;
import worlddomination.server.controller.ControllerManager;
import worlddomination.server.view.ControllerApiInterface;
import worlddomination.shared.updates.Update;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WorldDominationAIServiceImpl extends RemoteServiceServlet implements
		WorldDominationAIService, ControllerApiInterface {

	private Queue<Update> updates = new LinkedList<>();
	private Update response = null;

	public void initialiseController(String ipAddress, int port) {

		ClientController controller = ControllerManager.sharedManager(this);

		Thread thread = new Thread(controller);

		thread.start();
	}

	public void initialiseControllerAsHost(boolean shouldJoin) {

		ClientController controller = ControllerManager.sharedManager(this);

		Thread thread = new Thread(controller);

		thread.start();
	}

	private synchronized boolean ready() {
		if (response != null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 */
	public void addUpdate(Update update) {
		updates.add(update);
	}

	/**
	 * 
	 */
	public Update addUpdateAndWaitForResponse(Update update) {
		updates.add(update);
		while (!ready()) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO
				e.printStackTrace();
			}
		}
		Update currentResponse = response;
		response = null;
		return currentResponse;
	}

	/**
	 * 
	 */
	@Override
	public synchronized Update getNextUpdate() {
		if (updates.isEmpty()) {
			return null;
		}
		return updates.poll();
	}
	
	/**
	 * 
	 */
	public void sendUpdateResponse(Update response) {
		if (response != null) {
			this.response = response;
		}
	}

	/**
	 * 
	 */
	public void leaveGame() {

		System.out.println("Ready leave game");
	}
}
