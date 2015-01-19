package controller;

import java.util.Scanner;
import java.util.UUID;
import model.*;

public class Lobby {

    /** Generate the lobby asking player initialisation questions
     * human vs ai*/
    static void generateLobby(){
        Scanner input = new Scanner(System.in);
        Player user = new Player();
        UUID uniqueID;
        user.setId(uniqueID = UUID.randomUUID());
        System.out.println("Welcome to Risk!");
        System.out.println("What username would you like to play with?");
        user.setName(input.nextLine());
        System.out.println("What port would you like to play on?");
        user.setPort(input.nextInt());
        input.nextLine(); // Skips over new line char
        System.out.println("What is your public key?");
        user.setPublicKey(input.nextLine()); //THIS NEEDS ALTERED
        generateOpposition(input);
    }

    static void generateOpposition(Scanner input){
        System.out.println("Would you like to play against an AI or Human?(A/H)");
        String response = input.nextLine();
        if(response.toLowerCase().equals("a")){
            System.out.println("AI Opponent Selected");
            //Player has selected to use AI
            //TODO Method to call ai?
        }else if(response.toLowerCase().equals("h")){
            System.out.println("Human Opponent Selected");
            // Player has selected to play against other humans
            //TODO Continue with networking
        }else{
            //Player entered a wrong result, restart method
            System.out.println("ERROR:Invalid response");
            generateOpposition(input);
        }
        input.close();
    }
}
