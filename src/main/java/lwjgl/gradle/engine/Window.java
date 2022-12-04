package lwjgl.gradle.engine;

import lwjgl.gradle.scene.*;
import lwjgl.gradle.util.Color;
import lwjgl.gradle.util.Time;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private static Window instance = null;
    private long window;
    private Scene currentScene = null;
    private Integer isChangingSceneTo = null;
    private float timeToChangeScene = Constants.TIME_TO_CHANGE_SCENE;
    private Color backColor = new Color(1, 1, 1);
    private JSONObject player;
    public int currentScore = 0;

    public static Window get() {
        if (Window.instance == null) {
            Window.instance = new Window();
        }

        return Window.instance;
    }

    public void changeScene(int newScene) {
        if (isChangingSceneTo == null) {
            isChangingSceneTo = newScene;
        }
    }

    public void changeSceneHandler(float dt) {
        if (isChangingSceneTo == null) {
            return;
        }

        if (timeToChangeScene > 0) {
            timeToChangeScene -= dt;

            return;
        }

        switch (isChangingSceneTo) {
            case 0:
                currentScene = new LevelEditorScene(instance);
                break;
            case 1:
                currentScene = new LevelScene(instance);
                break;
            case 2:
                currentScene = new SettingsScene(instance);
                break;
            case 3:
                currentScene = new CreatePlayerScene(instance);
                break;
            case 4:
                currentScene = new GameOverScene(instance);
                break;
            case 5:
                currentScene = new ChangeSkinScene(instance);
                break;
            case 6:
                currentScene = new ChangePlayerScene(instance);
                break;
            default:
                assert false : "Unknown scene " + isChangingSceneTo;
        }

        timeToChangeScene = Constants.TIME_TO_CHANGE_SCENE;
        isChangingSceneTo = null;
    }

    public void run() {
        System.out.println("Hello LWJGL " + org.lwjgl.Version.getVersion());

        init();
        loop();
        exit();
    }

    public void exit() {
        Callbacks.glfwFreeCallbacks(window);
        GLFW.glfwDestroyWindow(window);

        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();

    }

    private void init() {
        LocalStore.load();
        loadCurrentPlayer();

        GLFWErrorCallback.createPrint(System.err).set();

        if ( !GLFW.glfwInit() ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
//        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // resizable

        window = GLFW.glfwCreateWindow(Constants.WINDOW_SIZE, Constants.WINDOW_SIZE, "Pizza Boy", NULL, NULL);
        if ( window == NULL ) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        setWindowCallbacks(window);

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwSwapInterval(1); // v-sync
        GLFW.glfwShowWindow(window);
    }

    private void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        GLCapabilities caps = GL.createCapabilities();
        System.out.println("Capabilities");
        System.out.println(caps);
        System.out.println("OGL version");
        System.out.println(GL30.glGetString(GL30.GL_VERSION));

        GL30.glEnable(GL30.GL_TEXTURE_2D);
//        GL30.glDisable(GL30.GL_DITHER);
//        GL30.glDisable(GL30.GL_LIGHTING);
//        GL30.glDisable(GL30.GL_DEPTH_TEST);
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        clearColor();

        if (player == null) {
            changeScene(3);
        } else {
            changeScene(0);

        }

        while ( !GLFW.glfwWindowShouldClose(window) ) {
            GLFW.glfwPollEvents();

            GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

            changeSceneHandler(dt);

            if (currentScene != null && dt >= 0) {
                currentScene.update(dt);
            }

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }

    public void setBackColor(Color color) {
        backColor = color;

        clearColor();
    }

    private void clearColor() {
        GL30.glClearColor(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), backColor.getAlpha());
    }

    private void setWindowCallbacks(long window) {
        GLFW.glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        GLFW.glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        GLFW.glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        GLFW.glfwSetKeyCallback(window, KeyListener::keyCallback);
    }

    public void loadCurrentPlayer() {
        player = LocalStore.getCurrentPlayer();
        if (player == null) {
            JSONArray players = LocalStore.getPlayers();
            if (players.size() > 0) {
                player = LocalStore.getFirstPlayer();
                LocalStore.setCurrentPlayer(player);
            } else {
                player = null;
            }
        }
    }

    public void setCurrentPlayer(JSONObject player) {
        this.player = player;
        LocalStore.setCurrentPlayer(player);
    }

    public String getPlayerName() {
        return (String) player.get("name");
    }

    public int getPlayerMaxScore() {
        java.lang.Object score = player.get("score");
        if (score instanceof Long) {
            return Integer.parseInt(Long.toString((Long) player.get("score")));

        }

        return (Integer) player.get("score");
    }

    public int getPlayerSkin() {
        int defaultSkin = 1;

        java.lang.Object skin = player.get("skin");
        if (skin == null) {
            return defaultSkin;
        }

        if (skin instanceof Long) {
            return Integer.parseInt(Long.toString((Long) player.get("skin")));

        }

        return (Integer) player.get("skin");
    }
}
