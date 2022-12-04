package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.SwitchToLevelEditorSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.util.Color;
import org.lwjgl.glfw.GLFW;

public class SettingsScene extends Scene {
    private Window window;
    private final Texture background;
    private Navigation nav;

    public SettingsScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Settings.");

        window.setBackColor(Color.white());

        Point itemMargin = new Point(0, -0.3f);
        Point btnDrawerCursor = new Point(0, 0.7f);
        float itemWidth = 1f;
        float itemHeight = 0.2f;

        background = new Background();
        nav = new Navigation();

        Button skin = new Button("change skin")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(() -> {
                    window.changeScene(5);
                });

        Button player = new Button("change player")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(() -> {
                    window.changeScene(6);
                });

        Button back = new Button("back")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(new SwitchToLevelEditorSceneLambda(window));

        nav
                .addItem(skin)
                .addItem(player)
                .addItem(back)
                .selectItem(0);
    }

    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
//            window.changeScene(0);
        }

        background.build();
        nav.build();
    }
}
