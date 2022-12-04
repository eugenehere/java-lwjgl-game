package lwjgl.gradle.engine;

public enum Direction {
    UP(0),
    UP_RIGHT(45),
    RIGHT(90),
    DOWN_RIGHT(135),
    DOWN(180),
    DOWN_LEFT(225),
    LEFT(270),
    UP_LEFT(315);

    final int angle;

    Direction(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public static Direction getByAngle(int angle) {
        Direction result = null;
        int diffPrev = 360;

        while (angle < 0) angle = angle + 360;
        while (angle >= 360) angle = angle - 360;

        for (Direction direction : Direction.values()) {
            int diff = Math.abs(direction.angle - angle);

            if (diff < diffPrev) {
                diffPrev = diff;
                result = direction;
            }
        }

        return result;
    }

    public static Direction getOppositeDirection(Direction direction) {
        return switch (direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            case RIGHT -> Direction.LEFT;

            case UP_LEFT -> Direction.DOWN_RIGHT;
            case UP_RIGHT -> Direction.DOWN_LEFT;
            case DOWN_LEFT -> Direction.UP_RIGHT;
            case DOWN_RIGHT -> Direction.UP_LEFT;
        };
    }
}
