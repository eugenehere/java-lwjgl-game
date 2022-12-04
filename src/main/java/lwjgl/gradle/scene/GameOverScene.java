package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.SwitchToLevelEditorSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.util.Color;
import org.lwjgl.glfw.GLFW;

public class GameOverScene extends Scene {
    private Window window;
    private final Texture background;
    private Navigation nav;
    private Rectangle textBackground; // = new Rectangle().setSize(0, 0).setBackground(Color.black());
    private Text gameOverTitle;
    private Text score;

    public GameOverScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Game Over.");

        window.setBackColor(Color.white());

        Point itemMargin = new Point(0, -0.5f);
        float itemWidth = 1f;
        float itemHeight = 0.2f;

        background = new Background();

        textBackground = (Rectangle) new Rectangle()
                .setBackground(Color.blue())
                .setMode(Area.Mode.topLeft)
                .setPosition(new Point(-1, 0.62f))
                .setWidth(2)
                .setHeight(0.8f);

        gameOverTitle = (Text) new Text("game over", 0.1f)
                .setPosition(new Point(-0.8f, 0.15f));

        score = (Text) new Text(Integer.toString(window.currentScore), 1f)
                .setPosition(new Point(-0.2f, -0.35f));

        Button back = new Button("back")
                .setSize(itemWidth, itemHeight)
                .setPosition(Point.getBlank().plus(itemMargin))
                .setCallback(new SwitchToLevelEditorSceneLambda(window));

        nav = new Navigation()
                .addItem(back)
                .selectItem(0);

        if (window.currentScore > window.getPlayerMaxScore()) {
            LocalStore.updatePlayerScore(window.getPlayerName(), window.currentScore);
        }

        window.currentScore = 0;
    }

    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
//            window.changeScene(0);
        }

        background.build();
        textBackground.build();
        gameOverTitle.build();
        score.build();
        nav.build();
    }
}
