package practice.premjit.patterns.kombatsim.common;

import java.util.Random;

public final class Randomizer {
    static Random random;
    static int id;
    
    static {
        random = new Random();
    }

    private Randomizer() {
        throw new UnsupportedOperationException();
    }
    
    public static int randomInteger(int end) {
        return randomIntegerInRange(0, end);
    }
    
    public static int randomIntegerInRange(int start, int end) {
        return start + random.nextInt(end+1 - start);
    }
    
    public static double randomDouble(double end) {
        return random.nextDouble() * end;
    }
    
    public static double randomDoubleInRange(double start, double end) {
        return start + randomDouble(end - start);
    }
    
    public static boolean hit(double chance) {
        return chance > randomDouble(100);
    }
    
    public static int generateId() {
        if (id == 0)
            id = randomInteger(100);
        id++;
        return id;
    }

}
