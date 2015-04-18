package worlddomination.shared.updates;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LobbyPlayer implements Serializable {

	private int id;
	private String name;
	private String publicKey;
	
	public LobbyPlayer() {}
	
	public LobbyPlayer(int id, String name, String publicKey) {
		this.id = id;
		this.name = name;
		this.publicKey = publicKey;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getPublicKey() {
		return publicKey;
	}
}
