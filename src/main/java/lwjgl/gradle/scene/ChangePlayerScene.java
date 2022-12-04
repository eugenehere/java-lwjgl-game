package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.SwitchToCreatePlayerSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.util.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChangePlayerScene extends Scene {
    private Window window;
    private final Texture background;
    private final Text title;
    private Navigation nav;

    public ChangePlayerScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Change Player.");

        window.setBackColor(Color.white());

        Point itemMargin = new Point(0, -0.3f);
        Point btnDrawerCursor = new Point(0, 0.8f);
        float itemWidth = 1f;
        float itemHeight = 0.2f;

        background = new Background();
        title = (Text) new Text("change user", 0.1f).setPosition(new Point(-0.25f, 0.8f));
        nav = new Navigation();

        JSONArray players = LocalStore.getPlayers();
        List<Button> playersButtons = new ArrayList<>();
        AtomicInteger playerIndex = new AtomicInteger();
        players.forEach((player) -> {
            String buttonText = playerIndex.incrementAndGet() + (String) ((JSONObject) player).get("name");
            Button button = new Button(buttonText);
            button.setSize(itemWidth, itemHeight);
            button.setPosition(btnDrawerCursor.plus(itemMargin));
            button.setCallback(() -> {
                window.setCurrentPlayer((JSONObject) player);
                window.changeScene(0);
            });

            playersButtons.add(button);
            nav.addItem(button);
        });

        Button create = new Button("create new")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(new SwitchToCreatePlayerSceneLambda(window));

        Button back = new Button("back")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(() -> window.changeScene(2));

        nav
                .addItem(create)
                .addItem(back)
                .selectItem(0);
    }

    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
//            window.changeScene(0);
        }

        background.build();
        title.build();
        nav.build();
    }
}
