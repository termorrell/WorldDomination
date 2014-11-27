package controller;
import java.util.*;
import java.*;
/**
 * Created by Maria on 27/11/14.
 */
public class ApiManager {


    /**
     * Sends a request to server
     * Takes in a string of Json?
     */
    public  void sendRequest(String json){

    }


    /**
     *
     * Receive information from the server and analyses it for each type of command
     *
     */
    public void receiveRequest(){
        String serverResponse = "";
        if(serverResponse.contains("attack")){

        }else if(serverResponse.contains("defend")){

        }else if(serverResponse.contains("join_game")){

        }else if(serverResponse.contains("roll")){

        }else if(serverResponse.contains("roll_hash")){

        }else if(serverResponse.contains("roll_number")){

        }else if(serverResponse.contains("setup")){

        }else if(serverResponse.contains("acknowledgement")){

        }else if(serverResponse.contains("trade_in_cards")){

        }else if(serverResponse.contains("deploy")){

        }else if(serverResponse.contains("attack_capture")){

        }else if(serverResponse.contains("fortify")){

        }else if(serverResponse.contains("win")){

        }else{

        }
    }
}
