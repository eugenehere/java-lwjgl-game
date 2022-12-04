package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.KeyListener;
import lwjgl.gradle.object.Input;
import org.lwjgl.glfw.GLFW;

public class KeyListenerLambda implements Lambda {
    private Input input;

    public KeyListenerLambda(Input input) {
        this.input = input;
    }

    private static String getLetterByKey(int key) {
        return switch (key) {
            case 65 -> "a";
            case 66 -> "b";
            case 67 -> "c";
            case 68 -> "d";
            case 69 -> "e";
            case 70 -> "f";
            case 71 -> "g";
            case 72 -> "h";
            case 73 -> "i";
            case 74 -> "j";
            case 75 -> "k";
            case 76 -> "l";
            case 77 -> "m";
            case 78 -> "n";
            case 79 -> "o";
            case 80 -> "p";
            case 81 -> "q";
            case 82 -> "r";
            case 83 -> "s";
            case 84 -> "t";
            case 85 -> "u";
            case 86 -> "v";
            case 87 -> "w";
            case 88 -> "x";
            case 89 -> "y";
            case 90 -> "z";

            case 49 -> "1";
            case 50 -> "2";
            case 51 -> "3";
            case 52 -> "4";
            case 53 -> "5";
            case 54 -> "6";
            case 55 -> "7";
            case 56 -> "8";
            case 57 -> "9";
            case 48 -> "0";
            default -> "";
        };
    }

    @Override
    public void execute() {}
    public void execute(int key) {
        String value = input.getValue();
        input.setValue(value + getLetterByKey(key));

        if (key == GLFW.GLFW_KEY_BACKSPACE && value.length() > 0) {
            input.setValue(value.substring(0, value.length() - 1));
        }
    }
}
