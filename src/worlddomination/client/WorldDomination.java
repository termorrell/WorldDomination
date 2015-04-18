package worlddomination.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.*;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;

import worlddomination.shared.updates.*;

public class WorldDomination implements EntryPoint {
	
	private static final int REFRESH_INTERVAL = 500; //ms
	private static VerticalPanel mainPanel = new VerticalPanel();
	private static FlexTable namesFlexTable = new FlexTable();;
	private static HorizontalPanel addNamePanel = new HorizontalPanel();
	private static HorizontalPanel continueButtonPanel = new HorizontalPanel();
	private static TextBox addNameTextBox = new TextBox();
	private static Button addNameButton = new Button("Add");
	private static Button continueButton = new Button("Continue");
	private static Label gameStateLabel = new Label();
	private static ArrayList<LobbyPlayer> allPlayers = new ArrayList<LobbyPlayer>();
	private static ArrayList<String> playerColours = new ArrayList<String>(Arrays.asList("green","blue","yellow","orange","purple","red"));
	private static Timer refreshTimer;
	private static WorldDominationServiceAsync worldDominationSvc = GWT.create(WorldDominationService.class);
    private static Update currentUpdate;
    private static String ipAddress = new String();
    private static int port;
    private static boolean isHost;
    private static boolean shouldJoin;
    private static Logger logger = Logger.getLogger("NameOfYourLogger");
    
	/**
	 * Entry point method.
	 */
	public void onModuleLoad() {
		
		exportHostGameFunction();
		exportJoinGameFunction();
		exportLeaveGameFunction();
	}
	
	private static void setUpSideBarView(boolean namePrompt) {

		// Create table for stock data
		setUpNameTable();

		// Assemble Add Stock panel.
		setUpAddPanel();
		
		// Assemble Select Territories panel.
		setUpContinuePanel();

		// Assemble Main panel.
		mainPanel.add(namesFlexTable);
		if (namePrompt) {
			mainPanel.add(addNamePanel);
			mainPanel.add(continueButtonPanel);
		}
		mainPanel.addStyleName("borderless");

		// Associate the Main panel with the HTML host page.
		RootPanel.get("controller").add(mainPanel);
		RootPanel.get("hint").add(gameStateLabel);

		initialiseGame();
	}
	
	public static void setUpNameTable() {

		namesFlexTable.setText(0, 0, "Name");
		namesFlexTable.addStyleName("u-full-width names");
	}
	
	public static void setUpAddPanel() {

		addNameButton.setEnabled(false);
		addNameTextBox.setEnabled(false);
		addNamePanel.setVisible(false);
		addNamePanel.add(addNameTextBox);
		addNameButton.addStyleName("u-pull-right");
		addNamePanel.add(addNameButton);
		addNamePanel.addStyleName("borderless");
	}
	
	public static void setUpContinuePanel() {

		continueButton.setEnabled(false);
		continueButton.setVisible(false);
		continueButtonPanel.add(continueButton);
		continueButton.addStyleName("u-full-width");
		continueButtonPanel.addStyleName("borderless");
		
		// Listen for mouse events on the Select Territories button.
		continueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				configureGameForPlayState();
			}
		});
	}

	private static void configureGameForPlayState() {

		// Return the recieved response
		readyEventResponse();
		
		// Prevent more players being added
		mainPanel.remove(addNamePanel);
		mainPanel.remove(continueButtonPanel);
		
		//Setup the map to be displayed in play state
		showMap();
	}
	
	public static native void showMap() /*-{
	  $wnd.RiskGame.showMap();
	}-*/;
	
	public static native void showNoConnection() /*-{
		$wnd.RiskGame.showNoConnection();
	}-*/;

	public static native void showAutoDismissError(String text) /*-{
		$wnd.RiskGame.showAutoDismissError(text);
	}-*/;
	
	public static native void showTurnControls() /*-{
		$wnd.RiskGame.showTurnControls();
	}-*/;
	
	public static native void hideTurnControls() /*-{
		$wnd.RiskGame.hideTurnControls();
	}-*/;
	
	public static native void waitForReadyState() /*-{
		$wnd.RiskGame.waitForReadyState();
	}-*/;

	public static native void waitForContinue() /*-{
		$wnd.RiskGame.ready();
	}-*/;
	
	public static native void addLogEntry(String msg) /*-{
		$wnd.RiskLog.addEntry(msg);
	}-*/;
	
	public static native void clearPlayers() /*-{
		$wnd.RiskGame.clearPlayers();
	}-*/;
	
	public static native void addPlayer(int id, String name, String publicKey) /*-{
		$wnd.RiskGame.addPlayer(id, name, publicKey);
	}-*/;
	
	private static native void currentPlayerEvent(int id, boolean isMe) /*-{
		$wnd.RiskGame.currentPlayerEvent(id, isMe);
	}-*/;
	
	private static void localPlayerNameEvent() {
		
		logger.log(Level.SEVERE, "ASKING FOR A FUCKING NAME");

		addNameButton.setEnabled(true);
		addNameTextBox.setEnabled(true);
		addNamePanel.setVisible(true);

		// Move cursor focus to the input box.
		addNameTextBox.setFocus(true);

		// Listen for mouse events on the Add button.
		addNameButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				final String playerName = addNameTextBox.getText();
				localPlayerNameEventResponse(playerName);
			}
		});
		
		// Listen for keyboard events in the input box.
		addNameTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					
					final String playerName = addNameTextBox.getText();
					localPlayerNameEventResponse(playerName);
				}
			}
		});
	}
	
	private static void localPlayerNameEventResponse(String name) {

		mainPanel.remove(addNamePanel);

		// Add the name to the table.
		int row = namesFlexTable.getRowCount();
		namesFlexTable.setText(row, 0, name);
		
		LocalPlayerName localPlayerName = new LocalPlayerName();
		localPlayerName.setName(name);
		
		sendUpdateResponse(localPlayerName);
	}
	
	private static native void rejectionEvent(String errorMessage) /*-{
		$wnd.RiskGame.rejectionEvent(errorMessage);
	}-*/;
	
	private static void lobbyEvent(ArrayList<LobbyPlayer> listOfPlayers) {

		waitForReadyState();
		
		int idx = 1;
		namesFlexTable.removeAllRows();
		setUpNameTable();
		clearPlayers();
		for (LobbyPlayer player : listOfPlayers) {

			addPlayer(player.getId(), player.getName(), player.getPublicKey());
			namesFlexTable.setHTML(idx, 0, player.getName() + "<span class=\"publicKey\">" + player.getPublicKey() + "</span>");
			idx ++;
		}
	}
	
	private static void readyEvent() {

		if (isHost && !shouldJoin) {

			configureGameForPlayState();
		} else {
			waitForContinue();
			continueButton.setEnabled(true);
			continueButton.setVisible(true);
		}
	}
	
	private static void readyEventResponse() {
		
		PingReady ready = (PingReady)currentUpdate;
		ready.setIsReady(true);
		
		sendUpdateResponse(ready);
	}
	
	private static native void claimTerritoryEvent() /*-{
	 	var callback = $entry(function(val) {
	    	@worlddomination.client.WorldDomination::claimTerritoryEventResponse(I)(val);
	 	});
		$wnd.RiskGame.claimTerritoryEvent(callback);
	}-*/;
	
	private static void claimTerritoryEventResponse(int result) {
		
		ClaimTerritory claimTerritory = (ClaimTerritory)currentUpdate;
		claimTerritory.setTerritoryID(result);
		
		sendUpdateResponse(claimTerritory);
	}
	
	private static native void distributeArmyEvent() /*-{
	 	var callback = $entry(function(val) {
	    	@worlddomination.client.WorldDomination::distributeArmyEventResponse(I)(val);
	 	});
		$wnd.RiskGame.distributeArmyEvent(callback);
	}-*/;
	

	private static void distributeArmyEventResponse(int result) {
		
		DistributeArmy distributeArmy = (DistributeArmy)currentUpdate;
		distributeArmy.setTerritoryID(result);
		
		sendUpdateResponse(distributeArmy);
	}
	
	private static native void mapUpdateEvent(String data) /*-{
		$wnd.RiskGame.mapUpdateEvent(data);
	}-*/;
	
	private static native void reinforceEvent(int armies) /*-{
	 	var callback = $entry(function(val) {
	    	@worlddomination.client.WorldDomination::reinforceEventResponse(Lcom/google/gwt/core/client/JsArrayNumber;)(val);
	 	});
		$wnd.RiskGame.reinforceEvent(armies, callback);
	}-*/;
	
	private static void reinforceEventResponse(JsArrayNumber result) {
		
		Reinforce reinforce = (Reinforce)currentUpdate;
		int[] arrayOfTerritories = new int[result.length()];
		for (int i=0; i < result.length(); i++) {
			arrayOfTerritories[i] = (int)result.get(i);
		}
		reinforce.setAllocationOfArmies(arrayOfTerritories);
		
		sendUpdateResponse(reinforce);
	}
	
	private static native void makeTurnEvent(String timeOut) /*-{
	 	var callback = $entry(function(type, sourceTerritory, destinationTerritory, numberOfArmies) {
	    	@worlddomination.client.WorldDomination::makeTurnEventResponse(Ljava/lang/String;III)(type, sourceTerritory, destinationTerritory, numberOfArmies);
	 	});
		$wnd.RiskGame.makeTurnEvent(timeOut, callback);
	}-*/;
	
	private static native void allocateCardEvent(int territoryID, String type) /*-{
		$wnd.RiskGame.allocateCardEvent(territoryID, type);
	}-*/;

	private static void makeTurnEventResponse(String result, int sourceTerritory, int destinationTerritory, int numberOfArmies) {
    	
		MakeTurn makeTurn = (MakeTurn)currentUpdate;
		makeTurn.setType(result);
		
		if (makeTurn.getType().equals("Attack") || makeTurn.getType().equals("Fortify")) {
			makeTurn.setSourceTerritory(sourceTerritory);
			makeTurn.setDestinationTerritory(destinationTerritory);
			makeTurn.setNumberOfArmies(numberOfArmies);
		}
		
		sendUpdateResponse(makeTurn);
	}
	

	private static native void allocateArmiesEvent(int sourceTerritory, int destinationTerritory, int maximumNumberOfArmies) /*-{
	 	var callback = $entry(function(val) {
	    	@worlddomination.client.WorldDomination::allocateArmiesEventResponse(I)(val);
	 	});
		$wnd.RiskGame.allocateArmiesEvent(sourceTerritory, destinationTerritory, maximumNumberOfArmies, callback);
	}-*/;
	
	private static void allocateArmiesEventResponse(int result) {
		
		AllocateArmies allocateArmies = (AllocateArmies)currentUpdate;
		allocateArmies.setArmiesMoved(result);
		
		sendUpdateResponse(allocateArmies);
	}
	
	public static native void winnerEvent(Object[] players) /*-{
		$wnd.RiskGame.winnerEvent(players);
	}-*/;
	
	private static void sendUpdateResponse(Update update) {
		
		// Initialise the service proxy.
	    if (worldDominationSvc == null) {
	      worldDominationSvc = GWT.create(WorldDominationService.class);
	    }
	    refreshTimer.cancel();
	    // Set up the callback object.
	    AsyncCallback<Void> callback = new AsyncCallback<Void>() {
	      public void onFailure(Throwable caught) {

	    	  logger.log(Level.SEVERE, "error");
	    	  showNoConnection();
	    	  // Response received restart refresh timer
	    	  restartRefreshTimer();
	      }

	      public void onSuccess(Void result) {

	    	  logger.log(Level.SEVERE, "recieved");
	    	  // Response received restart refresh timer
	    	  restartRefreshTimer();
	      }
	    };

	     // Make the call to the stock price service.
	    worldDominationSvc.sendUpdateResponse(update, callback);
	    currentUpdate = null;
	}
	
	private static void initialiseGame() {

		// Initialise the service proxy.
	    if (worldDominationSvc == null) {
	      worldDominationSvc = GWT.create(WorldDominationService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<Void> callback = new AsyncCallback<Void>() {
	      public void onFailure(Throwable caught) {

	    	  if (caught instanceof InvocationException) {
	    		  showNoConnection();
	    	  } else {
	    		  showAutoDismissError(caught.getMessage());
	    	  }
	      }

	      public void onSuccess(Void result) {
	    	setUpRefreshTimer();
	      }
	    };

	    if (isHost) {
		     // Make the call to the initialise controller service.
		    worldDominationSvc.initialiseControllerAsHost(shouldJoin, callback);
	    } else {
		     // Make the call to the initialise controller service.
		    worldDominationSvc.initialiseController(ipAddress, port, callback);
	    }
		
	}

	private static void refreshGameState() {
		
		// Initialise the service proxy.
	    if (worldDominationSvc == null) {
	      worldDominationSvc = GWT.create(WorldDominationService.class);
	    }
	    refreshTimer.cancel();
	    // Set up the callback object.
	    AsyncCallback<Update> callback = new AsyncCallback<Update>() {
	      public void onFailure(Throwable caught) {

	    	  logger.log(Level.SEVERE, "received error");

	    	  if (caught instanceof InvocationException) {
	    		  showNoConnection();
	    	  } else {
	    		  showAutoDismissError(caught.getMessage());
	    	  }
	    	  // Response received restart refresh timer
	    	  restartRefreshTimer();
	      }

	      public void onSuccess(Update update) {

	    	  logger.log(Level.SEVERE, "received response");
	    	  respondToUpdate(update);
	      }
	    };

  	  	logger.log(Level.SEVERE, "ABOUT TO GET UPDATE");
	     // Make the call to the stock price service.
	    worldDominationSvc.getNextUpdate(callback);
	}
	
	public static void leaveGame() {
		
		// Initialise the service proxy.
	    if (worldDominationSvc == null) {
	      worldDominationSvc = GWT.create(WorldDominationService.class);
	    }
	    if (refreshTimer != null) {
	    	refreshTimer.cancel();
	    }
	    // Set up the callback object.
	    AsyncCallback<Void> callback = new AsyncCallback<Void>() {
	      public void onFailure(Throwable caught) {
	    	  
	      }

	      public void onSuccess(Void result) {
	    	  Window.Location.reload();
	      }
	    };

	     // Make the call to the stock price service.
	    worldDominationSvc.leaveGame(callback);
	}
	
	public static void respondToUpdate(Update update) {
		
		currentUpdate = update;
		
  	  	logger.log(Level.SEVERE, "entered");

		if (update instanceof LocalPlayerName) {
			
			LocalPlayerName logUpdate = (LocalPlayerName)update;
			addLogEntry(logUpdate.getLogUpdate());
			
			localPlayerNameEvent();
		} else if (update instanceof Rejection) {
			
			Rejection rejection = (Rejection)update;
			
			rejectionEvent(rejection.getErrorMessage());
		} else if (update instanceof Lobby) {
			
			Lobby lobby = (Lobby)update;
			
			lobbyEvent(lobby.getListOfPlayers());
			
			// No response necessary restart refresh timer
			restartRefreshTimer();
		} else if (update instanceof PingReady) {
			
			readyEvent();
		} else if (update instanceof CurrentPlayer) {
			
			CurrentPlayer player = (CurrentPlayer)update;
			addLogEntry(player.getLogUpdate());
			
			currentPlayerEvent(player.getID(), player.getIsMe());
			
			// No response necessary restart refresh timer
			restartRefreshTimer();
		} else if (update instanceof ClaimTerritory) {
			
			ClaimTerritory claimTerritory = (ClaimTerritory)update;
			addLogEntry(claimTerritory.getLogUpdate());

			claimTerritoryEvent();
		} else if (update instanceof DistributeArmy) {

			DistributeArmy distributeArmy = (DistributeArmy)update;
			addLogEntry(distributeArmy.getLogUpdate());

			distributeArmyEvent();
		} else if (update instanceof LogUpdate) {
			
			LogUpdate logUpdate = (LogUpdate)update;
			addLogEntry(logUpdate.getLogUpdate());
			
			// No response necessary restart refresh timer
			restartRefreshTimer();
		} else if (update instanceof MapUpdate) {
			
			MapUpdate mapUpdate = (MapUpdate)update;
			addLogEntry(mapUpdate.getLogUpdate());
			
			mapUpdateEvent(mapUpdate.getMapState());

			// No response necessary restart refresh timer
			restartRefreshTimer();
		} else if (update instanceof Reinforce) {

			Reinforce reinforce = (Reinforce)update;
			addLogEntry(reinforce.getLogUpdate());
			
			reinforceEvent(reinforce.getNumberOfArmies());
		} else if (update instanceof MakeTurn) {
			
			MakeTurn makeTurn = (MakeTurn)update;
			addLogEntry(makeTurn.getLogUpdate());

			makeTurnEvent(makeTurn.getTimeOut());
		} else if (update instanceof AllocateCard) {
			
			AllocateCard allocateCard = (AllocateCard)update;
			addLogEntry(allocateCard.getLogUpdate());

			allocateCardEvent(allocateCard.getTerritoryID(), allocateCard.getCardType());
			
			// No response necessary restart refresh timer
			restartRefreshTimer();
		} else if (update instanceof AllocateArmies) {
			
			AllocateArmies allocateArmies = (AllocateArmies)update;
			addLogEntry(allocateArmies.getLogUpdate());
			
			allocateArmiesEvent(allocateArmies.getSourceTerritory(), allocateArmies.getDestinationTerritory(), allocateArmies.getMaximumNumberOfArmies());
		} else if (update instanceof Winner) {
			
			Winner winner = (Winner)update;
			addLogEntry(winner.getLogUpdate());
			
			winnerEvent(winner.getPositions());
		} else {
			
			// Assume the response was null and keep polling the server
			logger.log(Level.SEVERE, "this message should get logged");
			
			// Restart refresh timer
			restartRefreshTimer();
		}
	}

	public native void exportHostGameFunction()/*-{
	  $wnd.hostGame = @worlddomination.client.WorldDomination::hostGame(Z);
	}-*/;

	public native void exportJoinGameFunction()/*-{
	  $wnd.joinGame = @worlddomination.client.WorldDomination::joinGame(Ljava/lang/String;I);
	}-*/;
	
	public native void exportLeaveGameFunction()/*-{
	  $wnd.leaveGame = @worlddomination.client.WorldDomination::leaveGame(*);
	}-*/;
	
	public static void joinGame(String ip, int portNumber) {
		
		ipAddress = ip;
		port = portNumber;

		setUpSideBarView(true);
	}
	
	public static void hostGame(boolean join) {
		
		isHost = true;
		shouldJoin = join;
		
		if (!shouldJoin) {

			setUpSideBarView(false);
		} else {
			
			ipAddress = null;
			port = 0;
			setUpSideBarView(true);
		}
	}
	
	public static void setUpRefreshTimer() {

		// Setup timer to refresh list automatically.
		refreshTimer = new Timer() {
			@Override
			public void run() {
				refreshGameState();
			}
		};
		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	}
	
	public static void restartRefreshTimer() {

		logger.log(Level.SEVERE, "restarting timer");
		refreshTimer.scheduleRepeating(REFRESH_INTERVAL);
	}
}