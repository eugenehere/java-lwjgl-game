package lwjgl.gradle.scene;

import lwjgl.gradle.element.Background;
import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.ExitGameLambda;
import lwjgl.gradle.lambda.SwitchToLevelSceneLambda;
import lwjgl.gradle.lambda.SwitchToSettingsSceneLambda;
import lwjgl.gradle.object.Button;
import lwjgl.gradle.util.Color;
import org.lwjgl.glfw.GLFW;

public class LevelEditorScene extends Scene {
    private Window window;
    private final Navigation menu;
    private final Background background;
    private final Rectangle topBar;
    private final Text currentPlayer;
    private final Text maxScore;
    private final Text title;

    public LevelEditorScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Level Editor.");

        window.loadCurrentPlayer();
        window.setBackColor(Color.white());

        background = new Background();

        title = (Text) new Text("pizza boy", 0.2f)
                .setPosition(new Point(-0.45f, 0.65f));

        topBar = (Rectangle) new Rectangle()
                .setBackground(Color.black())
                .setMode(Area.Mode.topLeft)
                .setPosition(new Point(-1, 1))
                .setWidth(2)
                .setHeight(0.1f);

        currentPlayer = (Text) new Text("hello " + window.getPlayerName(), 0.1f)
                .setPosition(new Point(-0.95f, 0.9f));

        maxScore = (Text) new Text("top score " + window.getPlayerMaxScore(), 0.1f)
                .setPosition(new Point(0.3f, 0.9f));

        Point itemMargin = new Point(0, -0.3f);
        float itemWidth = 1f;
        float itemHeight = 0.2f;

        Button play = new Button("play")
                .setCallback(new SwitchToLevelSceneLambda(window))
//                .setChildrenMode(Area.Mode.bottomLeft)
                .setSize(itemWidth, itemHeight)
                .setPosition(Point.getBlank());
        Button settings = new Button("settings")
                .setCallback(new SwitchToSettingsSceneLambda(window))
//                .setChildrenMode(Area.Mode.bottomLeft)
                .setSize(itemWidth, itemHeight)
                .setPosition(Point.getBlank().plus(itemMargin));
        Button exit = new Button("exit")
                .setCallback(new ExitGameLambda())
//                .setChildrenMode(Area.Mode.bottomLeft)
                .setSize(itemWidth, itemHeight)
                .setPosition(Point.getBlank().plus(itemMargin.multiply(2)));

        menu = new Navigation()
                .addItem(play)
                .addItem(settings)
                .addItem(exit)
                .setPosition(new Point(-0.4f, 0.1f))
                .selectItem(0);
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
//            System.out.println("Exiting...");
//            System.exit(0);
        }

        background.build();
        title.build();
        topBar.build();
        currentPlayer.build();
        maxScore.build();
        menu.build();
    }
}
