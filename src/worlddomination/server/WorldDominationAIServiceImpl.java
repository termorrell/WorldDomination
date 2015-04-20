package worlddomination.server;

import java.util.LinkedList;
import java.util.Queue;

import worlddomination.client.WorldDominationAIService;
import worlddomination.server.artificialintelligence.ArtificialIntelligence;
import worlddomination.server.controller.ClientController;
import worlddomination.server.controller.ControllerManager;
import worlddomination.server.controller.ServerController;
import worlddomination.server.model.Model;
import worlddomination.server.view.ControllerApiInterface;
import worlddomination.shared.updates.Update;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WorldDominationAIServiceImpl extends RemoteServiceServlet implements
		WorldDominationAIService, ControllerApiInterface {

	private Queue<Update> updates = new LinkedList<>();
	private ArtificialIntelligence artificialIntelligence;

	public void initialiseController(String ipAddress, int port) {

		ClientController controller = ControllerManager.sharedClientController(this, ipAddress, port);

		Thread thread = new Thread(controller);
		
		thread.start();

		Model model = controller.getGameStateManager().getModel();
		
		artificialIntelligence = ArtificialIntelligence.getInstance(model);
	}

	public void initialiseControllerAsHost(boolean shouldJoin) {

		ServerController serverController = ControllerManager.sharedServerController(this);
		
		Thread serverThread = new Thread(serverController);
		
		serverThread.start();
		
		if (shouldJoin) {

			// TODO: This needs changing to pass in the ip and port
			ClientController clientController = ControllerManager.sharedClientController(this, "", 0);

			Thread clientThread = new Thread(clientController);

			clientThread.start();

			Model model = clientController.getGameStateManager().getModel();
			
			artificialIntelligence = ArtificialIntelligence.getInstance(model);
		}
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
		Update currentResponse = artificialIntelligence.getUpdateResponse(update);
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

		// Should never get called
	}

	/**
	 * 
	 */
	public void leaveGame() {

		System.out.println("Ready leave game");
	}
}
