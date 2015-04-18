package worlddomination.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleDieManager {

    public static List<Integer> diceRoll(int faces, int rolls) {
        List<Integer> result = new ArrayList<Integer>();
        Random random = new Random();
        for (int i = 0; i < rolls; i++) {
            result.add(random.nextInt(faces - 1));
        }
        return result;
    }
}
