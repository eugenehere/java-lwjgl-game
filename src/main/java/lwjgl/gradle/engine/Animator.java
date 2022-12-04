package lwjgl.gradle.engine;

public class Animator {
    private boolean direction = true;

    public Animator() {
        // todo lambda to get value
        // todo lambda to set value
    }

    public float getNextValue(float value, float step, float range) {
        if (value > range) {
            value = range;
            direction = false;
        } else if (value < -range) {
            value = -range;
            direction = true;
        }

        if (direction) {
            value += step;
        } else {
            value -= step;
        }

        return value;
    }
}
