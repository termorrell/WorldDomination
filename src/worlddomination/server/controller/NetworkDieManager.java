package controller;



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class NetworkDieManager {

    // TODO hash string vs hash bytes must be clarified with reps

    int localPlayerId;
    int numberOfRolls;
    int numberOfFaces;
    Map<Integer, String> hashes = new HashMap<>();
    Map<Integer, BigInteger> numbers = new HashMap<>();
    MessageDigest messageDigest;
    byte[] seed;


    public NetworkDieManager(int localPlayerId, int numberOfRolls, int numberOfFaces) {
        this.localPlayerId = localPlayerId;
        this.numberOfRolls = numberOfRolls;
        this.numberOfFaces = numberOfFaces;
    }

    public String generateLocalHash() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("SHA-256");
        numbers.put(localPlayerId,new BigInteger(256, new Random(Calendar.getInstance().getTimeInMillis())));
        hashes.put(localPlayerId, String.valueOf(messageDigest.digest(numbers.get(localPlayerId).toByteArray())));
       // TODO find out what exactly is hashed
        return hashes.get(localPlayerId);
    }

    public boolean addHash(int playerId, String hash, int numberOfPlayers) {
        hashes.put(playerId, hash);
        if(hashes.size() == numberOfPlayers) {
            return true;
        }
        return false;
    }

    public int addNumber(int playerId, String hexNumber) {
        byte[] numberInBytes = convertHexToByte(hexNumber);
        numbers.put(playerId, new BigInteger(numberInBytes));
// TODO verfication

//        byte[] numberInBytes = convertHexToByte(hexNumber);
//        byte[] hash = messageDigest.digest(numberInBytes);
//        String hashInHex = convertByteToHex(hash);
//        if(hashes.get(playerId).equalsIgnoreCase(hashInHex)) {
//            numbers.put(playerId, new BigInteger(numberInBytes));
//            return 0;
//        } else {
//            return -1;
//        }
        return 0;
    }

    public boolean isDieRollPossible(int numberOfPlayers) {
        if(numbers.size() == numberOfPlayers) {
            return true;
        }
        return false;
    }

    public void generateSeed() {
        // TODO once other things work
//        seed = new byte[32];
//        Collection<BigInteger> nums = numbers.values();
//        for(int i = 0;i< seed.length; i++ ) {
//            seed[i] = 0;
//            for(BigInteger num : nums) {
//                seed[i] = (byte) (seed[i] ^ num.toByteArray()[i]);
//            }
//        }
    }

    public int[] getDieRolls() {

        int[] rolls = new int[numberOfRolls];
        generateSeed();

        // TODO once the protocol is more stable
        Random random = new Random(100);
        for(int i = 0; i < rolls.length; i++) {
        	rolls[i] = random.nextInt(numberOfFaces);
        }

        return rolls;
    }

    public String convertByteToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for(byte b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }

    public byte[] convertHexToByte(String hex) {
        byte[] bytes = new byte[hex.length()/2];
        for(int i = 0; i< bytes.length; i++) {
            int firstHalf = Character.digit(hex.charAt(i * 2), 16) << 4;
            int secondHalf = Character.digit(hex.charAt(i * 2 + 1), 16);
            bytes[i] = (byte)(firstHalf + secondHalf);
        }
        return bytes;
    }

}
