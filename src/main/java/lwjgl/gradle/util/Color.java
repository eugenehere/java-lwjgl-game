package lwjgl.gradle.util;

public class Color {
    public float red = 0.0f;
    public float green = 0.0f;
    public float blue = 0.0f;
    public float alpha = 0.0f;

    public Color(float r, float g, float b) {
        red = r;
        green = g;
        blue = b;
    }

    public static Color duplicate(Color color) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color black() {
        return new Color(0, 0, 0);
    }

    public static Color white() {
        return new Color(1, 1, 1);
    }

    public static Color red() {
        return new Color(1, 0, 0);
    }

    public static Color green() {
        return new Color(0, 1, 0);
    }

    public static Color blue() {
        return new Color(0, 0, 1);
    }

    public float getAlpha() {
        return alpha;
    }

    public Color setAlpha(float a) {
        alpha = a;

        return this;
    }

    public float getRed() {
        return red;
    }

    public Color setRed(float value) {
        if (value > 1) {
            red = 1;

            return this;
        }

        if (value < 0) {
            red = 0;

            return this;
        }

        red = value;

        return this;
    }

    public float getGreen() {
        return green;
    }

    public Color setGreen(float value) {
        if (value > 1) {
            green = 1;

            return this;
        }

        if (value < 0) {
            green = 0;

            return this;
        }

        green = value;

        return this;
    }

    public float getBlue() {
        return blue;
    }

    public Color setBlue(float value) {
        if (value > 1) {
            blue = 1;

            return this;
        }

        if (value < 0) {
            blue = 0;

            return this;
        }

        blue = value;

        return this;
    }

    public Color setBrightnessBy(float brightness) {
        setRed(getRed() + brightness);
        setGreen(getGreen() + brightness);
        setBlue(getBlue() + brightness);

        return this;
    }
}
