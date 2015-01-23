package controller;

import java.util.Scanner;
import java.util.UUID;
import model.*;

public class Lobby {

    /**
     * Generate the lobby asking player initialisation questions
     * human vs ai
     */
    static void generateLobby() {
        Scanner input = new Scanner(System.in);
        Player user = new Player();
        UUID uniqueID;
        user.setId(uniqueID = UUID.randomUUID());
        System.out.println("Welcome to Risk!");
        System.out.println("What username would you like to play with?");
        user.setName(input.nextLine());
        obtainPortNo(input,user);
        System.out.println("What is your public key?");
        user.setPublicKey(input.nextLine()); //THIS NEEDS ALTERED
        generateOpposition(input);
    }

    static void generateOpposition(Scanner input) {
        System.out.println("Would you like to play against an AI or Human?(A/H)");
        String response = input.nextLine();
        if (response.toLowerCase().equals("a")) {
            System.out.println("AI Opponent Selected");
            //Player has selected to use AI
            //TODO Method to call ai?
        } else if (response.toLowerCase().equals("h")) {
            System.out.println("Human Opponent Selected");
            // Player has selected to play against other humans
            //TODO Continue with networking
        } else {
            //Player entered a wrong result, restart method
            System.out.println("ERROR:Invalid response");
            generateOpposition(input);
        }
        input.close();
    }

    /** Ask for port number and sanitise input */
    static void obtainPortNo(Scanner input, Player user) {
        System.out.println("What port would you like to play on?");
        int port;
        int count =0;
        do {
            if(count >0){System.out.println("That's not a valid port, please enter a positive number");} //Prevents message displayed first time
            while (!input.hasNextInt()) {
                System.out.println("That's not a valid port, please enter a positive number");
                input.next();
            }
            port = input.nextInt();
            count++;
        } while (port <= 0);
        user.setPort(port); //Sets port
        input.nextLine(); // Skips over new line char
    }
}