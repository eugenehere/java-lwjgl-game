package lwjgl.gradle.element;

import lwjgl.gradle.object.CityMap;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.lambda.Lambda;
import lwjgl.gradle.util.Util;

public class Player extends Car {
    private final Area bounds;
    private Lambda increaseScoreLambda;
    private Runnable boostLambda;
    private final Texture explosion;
    private float explosionUpdateTime = 0.1f;
    private Runnable crashCallback;

    public Player(int type) {
        super(type);

        float range = Constants.PLAYER_MOVEMENT_RANGE;
        bounds = new Area(Point.getBlank(), new Point(range * 2, range * 2)).setCenter(Point.getBlank());

        explosion = new Texture("./textures/" + Constants.TEXTURE_PACK + "/explosion.png");
        explosion.setSize(0.2f, 0.2f);
        explosion.hide();
    }

    public boolean hasCrashCallback() {
        return crashCallback != null;
    }

    public Player setCrashCallback(Runnable lambda) {
        this.crashCallback = lambda;

        return this;
    }

    public Player setMap(CityMap map) {
        super.setMap(map);

        return this;
    }

    public Player setIncreaseScoreCallback(Lambda lambda) {
        increaseScoreLambda = lambda;

        return this;
    }

    public Player setBoostCallback(Runnable lambda) {
        boostLambda = lambda;

        return this;

    }

    public Player moveOrigin(float dt) {
        direction = KeyListener.getArrowKeysDirection();
        if (direction == null) return this;

        handleRotation(direction);

        if (!handleBotsCollision(dt)) return this;

        if (!explosion.isHidden()) explosion.hide();

        if (!handleMapCollision()) return this;

        return (Player) super.moveOrigin(direction, moveStep);
    }

    private boolean handleMapCollision() {
        Point nextPosition = getNextPosition(direction);
        MapBlock nextBlock = getBlockByPoint(nextPosition);
        if (nextBlock != null) {
            if (nextBlock.hasOrdered()) {
                nextBlock.setHasOrdered(false);
                if (increaseScoreLambda != null) increaseScoreLambda.execute();
            }

            if (nextBlock.hasBoost()) {
                nextBlock.setHasBoost(false);
                if (boostLambda != null) boostLambda.run();
            }

            if (nextBlock.hasCollision()) {
                return false;
            }
        }

        if (hasMap() && !bounds.isPointInside(nextPosition)) {
            map.moveMap(Direction.getOppositeDirection(direction));

            return false;
        }

        return true;
    }

    public boolean handleBotsCollision(float dt) {
        float radarRange = 1f;
        Area radarArea = Area.getBlank()
                .setWidth(Area.Mode.center, radarRange)
                .setHeight(Area.Mode.center, radarRange)
                .setCenter(this.getOrigin());

        float playerOriginPrecision = 0.2f;
        this.getOrigin().precision = playerOriginPrecision;

        for (Bot bot : map.getBots()) {
            // get bots around
            if (!radarArea.isPointInside(bot.getOrigin())) continue;

            if (this.getOrigin().isEquals(bot.getOrigin(), playerOriginPrecision)) {
                return true;
            } // if inside the bot then let it go out

            Point nextPos = Point.duplicate(this.getOrigin()).moveBy(direction, moveStep);

            if (nextPos.isEquals(bot.getOrigin(), playerOriginPrecision)) {
                bot.blockBy("player");

                if (bot.type == 7) {
                    Util.setTimeout(() -> {
                        bot.setDynamicDestination(this.getOrigin());
                    }, 1000);
                }

                explosionUpdateTime -= dt;
                if (explosionUpdateTime < 0) {
                    explosionUpdateTime = 0.1f;
                    Area explosionArea = new Area(Point.getBlank(), new Point(moveStep, moveStep));
                    explosionArea.setCenter(Point.duplicate(nextPos).moveBy(direction, 0.1f));
                    explosion.setPosition(explosionArea.getRandomPoint());
                    if (explosion.isHidden()) explosion.show();
                    if (this.hasCrashCallback()) crashCallback.run();
                }

                return false;
            }
        }

        return true;
    }

    public Player build(float dt) {
        this.moveOrigin(dt);

        super.build();
        explosion.build();

        return this;
    }
}
