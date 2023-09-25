package tektonikal.customtotemparticles;

import java.util.Random;

public class MathHelper {
    public static Random rand = new Random();

    //i know Math.min() and .max() exist, but I don't care!!!!!
    public static int SafeRandom(int min, int max) {
        if (min > max) {
            return rand.nextInt(min - max) + max;
        }
        if (min == max) {
            return min;
        } else {
            return rand.nextInt(max - min) + min;
        }
    }

    public static float SafeRandom(float min, float max) {
        if (min > max) {
            float midpoint = min / 2 + max / 2;
            float half_range = min / 2 - max / 2;
            int plus_minus = rand.nextBoolean() ? 1 : -1;
            return midpoint + plus_minus * rand.nextFloat() * half_range;
        }
        if (min == max) {
            return min;
        } else {
            float midpoint = max / 2 + min / 2;
            float half_range = max / 2 - min / 2;
            int plus_minus = rand.nextBoolean() ? 1 : -1;
            return midpoint + plus_minus * rand.nextFloat() * half_range;
        }
    }
}
