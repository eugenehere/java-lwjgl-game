package lwjgl.gradle.engine;

import lwjgl.gradle.util.Color;
import lwjgl.gradle.util.Number;

import static org.lwjgl.opengl.GL11.*;

public class Point {
    public float x;
    public float y;
    public Color background = Color.white(); // debug purposes
    public float precision = 0.01f;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Point getBlank() {
        return new Point(0, 0);
    }

    public static Point duplicate(Point point) {
        return new Point(point.x, point.y);
    }

    public Point set(Point newPoint) {
        x = newPoint.x;
        y = newPoint.y;

        return this;
    }

    public Point multiply(float scale) {
        x *= scale;
        y *= scale;

        return this;
    }

    public Point plus(Point point) {
        this.x += point.x;
        this.y += point.y;

        return this;
    }

    public Point revert() {
        this.x = -x;
        this.y = -y;

        return this;
    }

    public Point moveBy(Direction direction, float step) {
        switch (direction) {
            case UP: y += step; break;
            case DOWN: y -= step; break;
            case LEFT: x -= step; break;
            case RIGHT: x += step; break;

            case UP_LEFT: x -= step / 2; y += step / 2; break;
            case UP_RIGHT: x += step / 2; y += step / 2; break;
            case DOWN_LEFT: x -= step / 2; y -= step / 2; break;
            case DOWN_RIGHT: x += step / 2; y -= step / 2; break;
        }

        return this;
    }

    public Point getDifference(Point point) {
        return new Point(point.x - this.x, point.y - this.y);
    }

    public float getDistanceTo(Point point) {
        return (float) Math.sqrt((point.y - y) * (point.y - y) + (point.x - x) * (point.x - x));
    }

    public Direction getDirection(float precision) {
        if (this.isEquals(Point.getBlank(), precision)) {
            return null;
        }

        if (Number.isBetween(x, -precision, precision) && y > precision) {
            return Direction.UP;
        } if (x > precision && y > precision) {
            return Direction.UP_RIGHT;
        } else if (x > precision && Number.isBetween(y, -precision, precision)) {
            return Direction.RIGHT;
        } else if (x > precision && y < -precision) {
            return Direction.DOWN_RIGHT;
        } if (Number.isBetween(x, -precision, precision) && y < -precision) {
            return Direction.DOWN;
        } if (x < -precision && y < -precision) {
            return Direction.DOWN_LEFT;
        } else if (x < -precision && Number.isBetween(y, -precision, precision)) {
            return Direction.LEFT;
        } else if (x < -precision && y > precision) {
            return Direction.UP_LEFT;
        }

        return null;
    }

    public Direction getDirectionTo(Point point) {
        Point difference = this.getDifference(point);

        return difference.getDirection(0);
    }

    public Direction getDirectionTo(Point point, float precision) {
        if (point == null) return null;

        Point difference = this.getDifference(point);

        return difference.getDirection(precision);
    }

    public boolean isBetween() {
        return false;
    }

    public boolean isEquals(Point point) {
        return this.x == point.x && this.y == point.y;
    }

    public boolean isEquals(Point point, float precision) {
        Area area = new Area(Point.getBlank(), new Point(precision, precision));
        area.setCenter(point);

        return area.isPointInside(this);
    }

    @Override
    public String toString() {
        return "Point(" + x + ":" + y + ")";
    }

    public Point debugBuild() { // debug purposes
        glColor3f(background.getRed(), background.getGreen(), background.getBlue());

        glBegin(GL_QUADS);
            glVertex2f(x - precision, y + precision);
            glVertex2f(x + precision, y + precision);
            glVertex2f(x + precision, y - precision);
            glVertex2f(x - precision, y - precision);
        glEnd();

        return this;
    }
}
