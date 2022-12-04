package lwjgl.gradle.element;

import lwjgl.gradle.element.block.Road;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.object.CityMap;
import lwjgl.gradle.object.TrafficLight;
import lwjgl.gradle.util.Number;
import lwjgl.gradle.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Bot extends Car {
    private final String id = UUID.randomUUID().toString();
    private Point destination = null;
    private Direction directionDynamic = null;
    private Point destinationPrevious = null;
    private Point destinationDynamic = null;
    private final float destinationPrecision = 0.01f;
    private String isBlockedBy = null;
    private final List<Bot> otherBots = new ArrayList<>();
    private Player player;
    private boolean isGoingBack = false;
    private float goingBackDuration = 1f;
    private Runnable onArrestedCallback = null;

    public Bot(int type) {
        super(type);
        this.switchToDefaultMoveStep();
    }

    public Bot setPlayer(Player player) {
        this.player = player;

        return this;
    }

    public Bot setOnArrestedCallback(Runnable lambda) {
        onArrestedCallback = lambda;

        return this;
    }

    public Bot switchToChasingMoveStep() {
        float chasingStep = Constants.MOVE_STEP * 1.5f;
        if (moveStep != chasingStep) {
            moveStep = chasingStep;
        }

        return this;
    }

    public Bot switchToDefaultMoveStep() {
        if (moveStep != Constants.BOT_MOVE_STEP) {
            moveStep = Constants.BOT_MOVE_STEP;
        }

        return this;
    }

    public boolean hasDynamicDestination() {
        return destinationDynamic != null;
    }

    public boolean hasPlayer() {
        return player != null;
    }

    public Player getPlayer() {
        return player;
    }

    public String getId() {
        return id;
    }

    public String getIsBlockedBy() {
        return isBlockedBy;
    }

    public Bot setDynamicDestination(Point destination) {
        this.destinationDynamic = destination;

        return this;
    }

    public Bot setOtherBots(List<Bot> bots) {
        for (Bot bot : bots) {
            setOtherBot(bot);
        }

        return this;
    }

    public Bot setOtherBot(Bot bot) {
        if (this.equals(bot)) {
            return this;
        } // exclude itself

        this.otherBots.add(bot);

        return this;
    }

    public Bot setOrigin(Point point) {
        super.setOrigin(point);
        destination = Point.duplicate(point);

        return this;
    }

    public Point getDestination() {
        return destination;
    }

    public Bot moveDestinationBy(Direction direction, float moveStep) {
        if (destination == null) return this;

        destination.moveBy(direction, moveStep);
        if (destinationPrevious != null) {
            destinationPrevious.moveBy(direction, moveStep);
        }

        return this;
    }

    public Bot setMap(CityMap map) {
        super.setMap(map);

        return this;
    }

    public Bot moveOrigin(float dt) {
        if (hasDynamicDestination()) {
            switchToChasingMoveStep();
            return handleDynamicDestination(dt);
        }

        switchToDefaultMoveStep();

        Point currentPosition = getOrigin();
        Direction newDirection = currentPosition.getDirectionTo(destination, destinationPrecision);
        if (newDirection == null) {
            Point destinationPrev = destination;
            destination = electDestination(currentPosition);
            destinationPrevious = destinationPrev;
        }

        direction = currentPosition.getDirectionTo(destination, destinationPrecision);

        handleRotation(direction);

        if (!handleTrafficLights(currentPosition)) return this;

        if (!handleBlockage(currentPosition)) return this;

        Point nextPosition = getNextPosition(direction);
        MapBlock nextBlock = getBlockByPoint(nextPosition);
        if (nextBlock != null && nextBlock.hasCollision()) {
            direction = Direction.getOppositeDirection(direction);

            return this;
        }

        return (Bot) super.moveOrigin(direction, moveStep);
    }

    public Bot handleDynamicDestination(float dt) {
        if (isGoingBack) {
            goingBackDuration -= dt;
            if (goingBackDuration < 0) {
                isGoingBack = false;
                goingBackDuration = 1f;
                return this;
            }

            handleRotation(directionDynamic);
            return (Bot) super.moveOrigin(directionDynamic, moveStep);
        }

        Point currentPosition = Point.duplicate(this.getOrigin());
        directionDynamic = currentPosition.getDirectionTo(destinationDynamic, destinationPrecision);
        if (directionDynamic == null) {
            onArrestedCallback.run();
            directionDynamic = null;
            destinationDynamic = null;
            destination = null;

            return this;
        }

        handleRotation(directionDynamic);

        Point nextPosition = getNextPosition(directionDynamic);
        MapBlock nextBlock = getBlockByPoint(nextPosition);
        if (nextBlock != null && nextBlock.hasCollision()) {
            isGoingBack = true;
            directionDynamic = Direction.getOppositeDirection(directionDynamic);
        }

        return (Bot) super.moveOrigin(directionDynamic, moveStep);
    }

    private Point electDestination(Point currentPosition) {
        MapBlock currentBlock = getBlockByPoint(currentPosition);
        float bSize = Constants.OBJECT_SIZE;

        List<Point> stepsAround = new ArrayList<>();
        List<MapBlock> blocksAround = new ArrayList<>();
        stepsAround.add(new Point(-bSize, 0));
        stepsAround.add(new Point(bSize, 0));
        stepsAround.add(new Point(0, bSize));
        stepsAround.add(new Point(0, -bSize));

        stepsAround.forEach((step) -> {
            Point pointAround = Point.duplicate(currentPosition).plus(step);
            MapBlock blockAround = getBlockByPoint(pointAround);

            if (direction != null) {
                Direction oppositeBotDirection = Direction.getOppositeDirection(direction);
                Direction blockAroundDirection = currentBlock.getPosition().getDirectionTo(blockAround.getPosition(), destinationPrecision);
                if (oppositeBotDirection.equals(blockAroundDirection)) return;
            }

            if (!blockAround.typeOf(MapBlock.Type.Road)) return;

            blocksAround.add(blockAround);
        });

        if (blocksAround.isEmpty()) {
            MapBlock previousBlock = getBlockByPoint(destinationPrevious);
            Direction previousBlockDirection = currentBlock.getPosition().getDirectionTo(previousBlock.getPosition(), destinationPrecision);
            Direction rightDirection = Direction.getByAngle(previousBlockDirection.getAngle() + 90);

            return Point.duplicate(currentBlock.getPosition())
                    .moveBy(rightDirection, 0.08f)
                    .moveBy(previousBlockDirection, 0.15f);
        }

        MapBlock nextBlock = blocksAround.get(Number.random(0, blocksAround.size() - 1));

        Direction nextBlockDirection = currentBlock.getPosition().getDirectionTo(nextBlock.getPosition(), destinationPrecision);
        Direction rightDirection = Direction.getByAngle(nextBlockDirection.getAngle() + 90);
        Direction oppositeDirection = Direction.getByAngle(nextBlockDirection.getAngle() + 180);

        return Point.duplicate(nextBlock.getPosition())
                .moveBy(rightDirection, 0.08f)
                .moveBy(oppositeDirection, 0.15f);
    }

    private boolean handleBlockage(Point currentPosition) {
        if (isBlockedBy != null) return false;

        List<Point> nextPositions = new ArrayList<>();
        Point startPoint = Point.duplicate(currentPosition);
        float visibilityDistance = currentPosition.getDistanceTo(destination);

        float visibilityRange = 0.1f;
        for (float i = 0; i <= visibilityDistance; i += visibilityRange) {
            nextPositions.add(Point.duplicate(startPoint.moveBy(direction, visibilityRange)));
        }

        float radarRange = 1f;
        Area radarArea = new Area(Point.getBlank(), new Point(radarRange, radarRange)).setCenter(currentPosition);
        List<Bot> botsAround = new ArrayList<>();
        for (Bot bot : otherBots) {
            if (radarArea.isPointInside(bot.getOrigin())) {
                botsAround.add(bot);
            }
        }

        for (Bot bot : botsAround) {
            for (Point nextP : nextPositions) {
                boolean isOriginVisible = bot.getOrigin().isEquals(nextP, visibilityRange);
                boolean isFrontBumperVisible = false;
                boolean isBackBumperVisible = false;

                if (bot.getDirection() != null) {
                    Point front = Point.duplicate(bot.getOrigin())
                            .moveBy(bot.getDirection(), 0.05f);
                    Point back = Point.duplicate(bot.getOrigin())
                            .moveBy(Direction.getOppositeDirection(bot.getDirection()), 0.05f);

                    isFrontBumperVisible = front
                            .isEquals(nextP, visibilityRange);
                    isBackBumperVisible = back
                            .isEquals(nextP, visibilityRange);
                }

                if (!isOriginVisible && !isFrontBumperVisible && !isBackBumperVisible) continue;

                if (!Objects.equals(bot.getIsBlockedBy(), this.getId())) {
                    blockBy(bot.getId());

                    return false;
                }
            }
        }

        boolean isPlayerAround = radarArea.isPointInside(this.getPlayer().getOrigin());
        if (isPlayerAround) {
            for (Point nextP : nextPositions) {
                boolean isOriginVisible = this.getPlayer().getOrigin().isEquals(nextP, visibilityRange);
                if (isOriginVisible) {
                    blockBy("player");

                    return false;
                }
            }
        }

        return true;
    }

    public Bot blockBy(String reference) {
        isBlockedBy = reference;
        int blockDuration = 2000;

        Util.setTimeout(() -> {
            isBlockedBy = null;
        }, blockDuration);

        return this;
    }

    private boolean handleTrafficLights(Point currentPosition) {
        MapBlock currentBlock = getBlockByPoint(currentPosition);
        MapBlock nextBlock = getBlockByPoint(destination);
        Direction nextBlockDirection = currentBlock
                .getPosition()
                .getDirectionTo(nextBlock.getPosition(), destinationPrecision);

        if (nextBlockDirection == null) return true;

        if (!(nextBlock instanceof Road)) return true;

        if (!((Road) nextBlock).hasTrafficLight()) return true;

        TrafficLight tl = ((Road) nextBlock).getTrafficLight();
        Direction tlDirection = Direction.getOppositeDirection(nextBlockDirection);

        return !tl.getValueByDirection(tlDirection);
    }

    public Bot build(float dt) {
        this.moveOrigin(dt);

        super.build();

        return this;
    }
}
