package model;

public class Model {
	
	private GameState gameState;
	private PlayerInfo playerInfo;
	
	public Model() {
		gameState = new GameState();
		playerInfo = new PlayerInfo();
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(PlayerInfo playerInfo) {
		this.playerInfo = playerInfo;
	}
}
