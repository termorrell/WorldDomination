package controller;

import java.util.Scanner;
import model.*;

public class Lobby {

    /** Generate the lobby asking player initialisation questions
     * human vs ai*/
    static void generateLobby(){
        boolean ai_utilised = false;
        Scanner input = new Scanner(System.in);
        Player user = new Player();
        System.out.println("Welcome to Risk!");
        System.out.println("What username would you like to play with?");
        user.setName(input.nextLine());
        generateOpposition(input,ai_utilised);

    }
    static boolean generateOpposition(Scanner input, boolean ai_utilised){
        System.out.println("Would you like to play against an AI or Human?(A/H)");
        String response = input.nextLine();
        if(response.toLowerCase().equals("a")){
            //Player has selected to use AI
            ai_utilised = true;
            //TODO Method to call ai?
        }else if(response.toLowerCase().equals("h")){
            // Player has selected to play against other humans
            ai_utilised = false;
        }else{
            //Player entered a wrong result, restart method
            System.out.println("ERROR:Invalid response");
            generateOpposition(input,ai_utilised);
        }
        return ai_utilised;
    }
}
