package worlddomination.shared.updates;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Player implements Serializable {

	private int id;
	private String name;
	private String publicKey;
	
	public Player() {}
	
	public Player(int id, String name, String publicKey) {
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
