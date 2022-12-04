package lwjgl.gradle.engine;

import lwjgl.gradle.lambda.KeyListenerLambda;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class KeyListener {
    private static KeyListener instance;
    private final boolean[] keyPressed = new boolean[350];
    private final Map<String, KeyListenerLambda> listeners = new HashMap<>();

    private KeyListener() {

    }

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }

        return KeyListener.instance;
    }

    public static void keyCallback(long window, int key, int scanCode, int action, int mods) {
        if (action == GLFW.GLFW_PRESS) {
            get().listeners.forEach((_key, listener) -> listener.execute(key));

            get().keyPressed[key] = true;
        } else if (action == GLFW.GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int key) {
        if (key >= get().keyPressed.length) {
            return false;
        }

        return get().keyPressed[key];
    }

    public static void addListener(String name, KeyListenerLambda callback) {
        get().listeners.put(name, callback);
    }

    public static void removeListener(String name) {
        get().listeners.remove(name);
    }

    public static Direction getArrowKeysDirection() {
        if (KeyListener.isKeyPressed(GLFW_KEY_UP) && KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            return Direction.UP_LEFT;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_UP) && KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            return Direction.UP_RIGHT;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) && KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            return Direction.DOWN_LEFT;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN) && KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            return Direction.DOWN_RIGHT;
        }

        if (KeyListener.isKeyPressed(GLFW_KEY_UP)) {
            return Direction.UP;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_DOWN)) {
            return Direction.DOWN;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT)) {
            return Direction.LEFT;
        } else if (KeyListener.isKeyPressed(GLFW_KEY_RIGHT)) {
            return Direction.RIGHT;
        }

        return null;
    }
}
