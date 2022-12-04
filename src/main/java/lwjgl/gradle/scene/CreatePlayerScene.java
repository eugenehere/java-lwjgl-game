package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.CreateInitialUserLambda;
import lwjgl.gradle.lambda.SwitchToLevelEditorSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.object.Input;
import lwjgl.gradle.util.Color;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class CreatePlayerScene extends Scene {
    private Window window;
    private final Texture background;
    private Navigation nav;

    public CreatePlayerScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Create Player.");

        window.setBackColor(Color.white());

        Point itemMargin = new Point(0, -0.3f);
        Point btnDrawerCursor = new Point(0, 0.4f);
        float itemWidth = 1f;
        float itemHeight = 0.2f;

        background = new Background();

        Input playerName = new Input()
                .setTitle("enter name")
                .setSize(itemWidth, itemHeight + 0.15f)
                .setPosition(btnDrawerCursor);

        Button create = new Button("create user")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin).plus(new Point(0, -0.1f)))
                .setCallback(new CreateInitialUserLambda(window, playerName));

        Button back = new Button("back")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(() -> {
                    window.changeScene(6);
                });

        nav = new Navigation()
                .addItem(playerName)
                .addItem(create)
                .addItem(back)
                .selectItem(0);
    }

    public void update(float dt) {
        background.build();
        nav.build();
    }
}
