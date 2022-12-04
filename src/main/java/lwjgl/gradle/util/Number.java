package lwjgl.gradle.util;

import java.util.concurrent.ThreadLocalRandom;

public class Number {
    public static int random(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static boolean isBetween(float value, float min, float max) {
        return value >= min && value <= max;
    }
}
