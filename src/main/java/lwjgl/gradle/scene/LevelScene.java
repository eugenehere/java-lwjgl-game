package lwjgl.gradle.scene;

import lwjgl.gradle.element.*;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.object.CityMap;
import lwjgl.gradle.lambda.IncreaseScoreLambda;
import lwjgl.gradle.object.Gameplay;
import lwjgl.gradle.util.Color;
import lwjgl.gradle.util.Number;
import lwjgl.gradle.util.Util;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class LevelScene extends Scene {
    private final Window window;
    private final Player player;
    private List<Bot> bots;
    private final CityMap map;
    private Texture pointer;
    private Gameplay gameplay;
    private int money = 100;
    private float taxInterval = 1f;

    public LevelScene(Window window) {
        this.window = window;
        System.out.println("Changing scene to Level.");

        window.setBackColor(Color.white());

        pointer = new Texture("./textures/" + Constants.TEXTURE_PACK + "/pointer.png")
                .setSize(0.3f, 0.3f);

        map = new CityMap()
                .setPointer(pointer);

        player = new Player(window.getPlayerSkin())
                .setMap(map)
                .setIncreaseScoreCallback(new IncreaseScoreLambda(this))
                .setBoostCallback(this::boostPlayer)
                .setCrashCallback(() -> this.money -= 1);

        bots = initBots(map);

        map.setBots(bots);

        gameplay = new Gameplay();
    }

    private List<Bot> initBots(CityMap map) {
        List<Bot> bots = new ArrayList<>();
        List<MapBlock> roads = map.getBlocksByType(MapBlock.Type.Road);
        List<Integer> roadIndexes = new ArrayList<>();

        for (int i = 0; i < Constants.BOT_COUNT; i++) {
            int botType = Number.random(1, 7);

            if (roadIndexes.size() >= roads.size()) {
                System.out.println("Generated maximum amount of bots " + roadIndexes.size());
                break;
            }
            int roadIndex;
            do {
                roadIndex = Number.random(0, roads.size() - 1);
            } while (roadIndexes.contains(roadIndex));
            roadIndexes.add(roadIndex);

            Point startPosition = roads.get(roadIndex).getPosition();

            Bot bot = new Bot(botType).setMap(map).setOrigin(startPosition);
            bot.setPlayer(this.player);
            bot.setOnArrestedCallback(() -> {
                this.money -= 20;
            });
            bots.add(bot);
        }

        for (Bot bot : bots) {
            bot.setOtherBots(bots);
        }

        return bots;
    }

    public LevelScene increaseScore() {
        window.currentScore++;
        money += 50;
        map.setOrder();

        System.out.println("Score " + window.currentScore);
        return this;
    }

    public LevelScene boostPlayer() {
        float boostCapacity = 0.005f;
        int boostLength = 10 * 1000;
        map.hideBoost();
        Constants.MOVE_STEP += boostCapacity;
        System.out.println("Boosting player " + Constants.MOVE_STEP);
        Util.setTimeout(() -> { // todo use Runnable
            Constants.MOVE_STEP -= boostCapacity;
            map.setBoost();
        }, boostLength);

        return this;
    }

    private void handleTax(float dt) {
        taxInterval -= dt;

        if (taxInterval < 0) {
            taxInterval = 1f;
            money--;
        }

        if (money < 0) {
            window.changeScene(4);
        }
    }

    @Override
    public void update(float dt) {
        if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
            window.changeScene(0);
        }

        handleTax(dt);

        map.build();
        for (Bot bot : bots) {
            bot.build(dt);
        }
        player.build(dt);

        pointer.build();
        gameplay.build(window.currentScore, money);
    }
}
