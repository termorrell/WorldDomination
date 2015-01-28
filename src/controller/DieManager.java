package controller;

import java.util.LinkedList;
import java.util.Random;

import model.Army;
import model.Territory;

public class DieManager {
	
	/** Random number generator for basic game*/
	static int generateRandomDiceRoll(){
		int max =6;
		int min =0;
		Random rand = new Random();
		int result = rand.nextInt((max-min)+1)+min;
		return result;
	}
	/** Calculates the amount of dice that need to be rolled when attacking*/
	static int calculateAttackerDieNumber(Territory attacking_territory){
		int die_number =0;
		LinkedList<Army> occupying_armies = attacking_territory.getOccupyingArmies(); //Counts armies in current territory
		int no_of_attacking = occupying_armies.size();
		die_number = no_of_attacking -1;
		if(die_number <0 ){ // Checks it doesn't return negative
			die_number= 0;
		}else if(die_number >3){ //Maximum of 3 dice can be rolled
			die_number =3;
		}
		return die_number;
	}
	/**Calculates the amount of dice rolled when defending*/
	static int calculateDefenderDieNUmber(Territory defending_territory){
		int die_number =0;
		LinkedList<Army> occupying_armies = defending_territory.getOccupyingArmies(); //Counts armies in defending territory
		int no_of_defending = occupying_armies.size();
		if(no_of_defending >= 2 ){
			die_number = 2;
		}else{
			die_number =1;
		}
		return die_number;
	}
	
	static int[] diceRoll ( int faces, int rolls){
		int[] result = new int[rolls];
		Random random = new Random();
		for (int i=0; i < rolls; i++) {
			result[i] = random.nextInt(faces);
			result[i]--;
		}
		return result;
	}
}
