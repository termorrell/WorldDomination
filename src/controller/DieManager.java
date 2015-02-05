package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DieManager {
	
	public static List<Integer> diceRoll ( int faces, int rolls){
		List<Integer> result = new ArrayList<>();
		Random random = new Random();
		for (int i=0; i < rolls; i++) {
			result.add(random.nextInt(faces) - 1);
		}
		return result;
	}
}
