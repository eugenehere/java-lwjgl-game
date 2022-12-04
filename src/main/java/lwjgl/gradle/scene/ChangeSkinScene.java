package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.SwitchToSettingsSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.util.Color;
import org.lwjgl.glfw.GLFW;

public class ChangeSkinScene extends Scene {
    private Window window;
    private final Texture background;
    private final Text title;
    private Navigation nav;

    public ChangeSkinScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Change Skin.");

        window.setBackColor(Color.white());

        Point itemMargin = new Point(0, -0.2f);
        Point btnDrawerCursor = new Point(0, 0.85f);
        float itemWidth = 1f;
        float itemHeight = 0.15f;

        background = new Background();
        title = (Text) new Text("change skin", 0.1f).setPosition(new Point(-0.25f, 0.8f));
        nav = new Navigation();

        enum Skin {
            LIGHTBLUE(1, "lightblue"),
            BLUE(2, "blue"),
            RED(3, "red"),
            ORANGE(4, "orange"),
            BLACK(5, "black"),
            LIGHTGREEN(6, "lightgreen"),
            POLICE(7, "police");

            int id;
            String name;
            Skin(int id, String name) {
                this.id = id;
                this.name = name;
            }

            int getId() {
                return id;
            }

            String getName() {
                return name;
            }

            static Skin getById(int id) {
                for (Skin skin : Skin.values()) {
                    if (skin.getId() == id) {
                        return skin;
                    }
                }
                return null;
            }
        }

        for (int i = 1; i <= Constants.SKINS_COUNT - 1; i++) {
            int finalI = i;
            Skin skin = Skin.getById(i);
            if (skin == null) continue;

            Button button = new Button(skin.getName() + " skin");
            button.setSize(itemWidth, itemHeight);
            button.setPosition(btnDrawerCursor.plus(itemMargin));
            button.setCallback(() -> {
                LocalStore.updatePlayerSkin(window.getPlayerName(), finalI);
                window.changeScene(2);
            });

            nav.addItem(button);
        }

        Button back = new Button("back")
                .setSize(itemWidth, itemHeight)
                .setPosition(btnDrawerCursor.plus(itemMargin))
                .setCallback(new SwitchToSettingsSceneLambda(window));

        nav
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
