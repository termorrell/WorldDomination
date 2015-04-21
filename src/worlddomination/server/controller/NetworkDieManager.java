package worlddomination.server.controller;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import worlddomination.server.exceptions.HashMismatchException;

public class NetworkDieManager {

    // TODO hash string vs hash bytes must be clarified with reps
	
	int[] dieRoll;
	
	
    int localPlayerId;
    int numberOfRolls;
    int numberOfFaces;
    
    byte[] localHash;
    BigInteger localNumber;
    
    MessageDigest messageDigest;
    
    int numberOfPlayers = 0;
 
    SeedGenerator gen;


    public NetworkDieManager(int localPlayerId, int numberOfRolls, int numberOfFaces) {
        this.localPlayerId = localPlayerId;
        this.numberOfRolls = numberOfRolls;
        this.numberOfFaces = numberOfFaces;
        gen = new SeedGenerator();
    }

    public String generateLocalHash() throws NoSuchAlgorithmException {
    	numberOfPlayers ++;
        messageDigest = MessageDigest.getInstance("SHA-256");
        localNumber = new BigInteger(256, new Random(Calendar.getInstance().getTimeInMillis()));
        localHash = messageDigest.digest(localNumber.toByteArray());
        try {
			gen.addHash(localPlayerId, convertByteToHex(localHash));
			gen.addNumber(localPlayerId, convertByteToHex(localNumber.toByteArray()));
		} catch (HashMismatchException e) {
			e.printStackTrace();
		}
        return convertByteToHex(localHash);
    }

    public boolean addHash(int playerId, String hash, int playersInGame) {
       try {
		gen.addHash(playerId, hash);
       } catch (HashMismatchException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
       }
       numberOfPlayers++;
        if (numberOfPlayers == playersInGame) {
        	numberOfPlayers = 1;
            return true;
        }
        return false;
    }

    public boolean addNumber(int playerId, String hexNumber,int playersInGame) {
    	try {
			gen.addNumber(playerId, hexNumber);
		} catch (HashMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(numberOfPlayers == playersInGame) {
        	try {
				gen.finalise();
				dieRoll = new int[numberOfFaces];
				for (int i = 0; i < dieRoll.length; i++) {
					long l = gen.nextInt() % numberOfFaces;
					dieRoll[i] = (int) l;
				}
			} catch (HashMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return true;
        } else {
        	return false;
        }
    }

    public int[] getDieRolls() {
    	return dieRoll;
    }

    public String convertByteToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }

    public byte[] convertHexToByte(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int firstHalf = Character.digit(hex.charAt(i * 2), 16) << 4;
            int secondHalf = Character.digit(hex.charAt(i * 2 + 1), 16);
            bytes[i] = (byte) (firstHalf + secondHalf);
        }
        return bytes;
    }

}
