package model;

import java.net.InetAddress;
import java.util.Map;

public class Player {
	int id;
	InetAddress ipAddress;
	int port;
	String name;
	String publicKey;
	Map<Army, Territory> armies; // mapping armies to the territories they are occupying
}
