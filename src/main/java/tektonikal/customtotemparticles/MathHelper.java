package tektonikal.customtotemparticles;

import java.util.Random;

public class MathHelper {
    public static Random rand = new Random();

    public static int SafeRandom(int min, int max) {
        //I don't trust this thing
        return min == max ? min : rand.nextInt(Math.max(min, max) - Math.min(min, max) + 1) + Math.min(min, max);
    }

    //this "works" but isn't fully correct according to my testing.
    //if only it was easy to generate random floats within a set range, and it allowed negative numbers...
    public static float SafeRandom(float min, float max) {
        if (min == max) {
            return min;
        } else {
            float midpoint = Math.max(min, max) / 2 + Math.min(min, max) / 2;
            float half_range = Math.max(min, max) / 2 - Math.min(min, max) / 2;
            int plus_minus = rand.nextBoolean() ? 1 : -1;
            return midpoint + plus_minus * rand.nextFloat() * half_range;
        }
    }
}
