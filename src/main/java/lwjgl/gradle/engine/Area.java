package lwjgl.gradle.engine;

import lwjgl.gradle.util.Color;
import lwjgl.gradle.util.Number;

import static org.lwjgl.opengl.GL11.*;

// represents rectangle shape
public class Area {
    Point p1;
    Point p2;
    public enum Mode {
        center,
        bottomLeft,
        bottomRight,
        topLeft,
        topRight,
    };
    public Color background = Color.white(); // debug purposes

    public Area(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public static Area getBlank() {
        return new Area(Point.getBlank(), Point.getBlank());
    }

    public static Area getWindowArea() {
        return new Area(
                new Point(-1, -1),
                new Point(1, 1)
        );
    }

    public Point getRandomPoint() {
        // precision: 2 digits
        int x1 = (int) (p1.x * 100);
        int x2 = (int) (p2.x * 100);
        int y1 = (int) (p1.y * 100);
        int y2 = (int) (p2.y * 100);

        float randomX = Number.random(x1, x2) / 100f;
        float randomY = Number.random(y1, y2) / 100f;

        return new Point(randomX, randomY);
    }

    public Area rotate() {
        Point center = this.getCenter();

        Point p1Diff = center.getDifference(p1);
        Point p2Diff = center.getDifference(p2);

        Point p1DiffReverted = new Point(p1Diff.y, p1Diff.x);
        Point p2DiffReverted = new Point(p2Diff.y, p2Diff.x);

        p1.set(Point.duplicate(center).plus(p1DiffReverted));
        p2.set(Point.duplicate(center).plus(p2DiffReverted));

        return this;
    }

    private Area moveTo(Point point, Point destination) {
        Point difference = point.getDifference(destination);

        return moveBy(difference);
    }

    public Area moveBy(Point step) {
        p1.plus(step);
        p2.plus(step);

        return this;
    }

    public Point getPosition(Mode mode) {
        return switch (mode) {
            case center -> getCenter();
            case bottomLeft -> Point.duplicate(p1);
            case bottomRight -> new Point(p2.x, p1.y);
            case topLeft -> new Point(p1.x, p2.y);
            case topRight -> Point.duplicate(p2);
        };
    }

    public Area setPosition(Mode mode, Point destination) {
        switch (mode) {
            case center -> setCenter(destination);
            case bottomLeft -> setBase(destination);
            case bottomRight -> moveTo(getPosition(mode), destination);
            case topLeft -> moveTo(getPosition(mode), destination);
            case topRight -> moveTo(p2, destination);
        };

        return this;
    }

    public Point getCenter() {
        float x = (p2.x - p1.x) / 2 + p1.x;
        float y = (p2.y - p1.y) / 2 + p1.y;

        return new Point(x, y);
    }

    public Area setCenter(Point destination) {
        Point current = getCenter();

        return moveTo(current, destination);
    }

    public Point getBase() {
        return p1;
    }

    public Area setBase(Point destination) {
        Point current = getBase();

        return moveTo(current, destination);
    }

    public float getWidth() {
        return p2.x - p1.x;
    }

    public float getHalfWidth() {
        return getWidth() / 2;
    }

    public Area setWidth(Mode mode, float width) {
        switch (mode) {
            case center:
                Point center = getCenter();
                p1.x = center.x - width / 2;
                p2.x = center.x + width / 2;
                break;
            case bottomLeft:
            case topLeft:
                p2.x = p1.x + width;
            case topRight:
            case bottomRight:
                p1.x = p2.x - width;
        }

        return this;
    }

    public float getHeight() {
        return p2.y - p1.y;
    }

    public float getHalfHeight() {
        return getHeight() / 2;
    }

    public Area setHeight(Mode mode, float height) {
        switch (mode) {
            case center:
                Point center = getCenter();
                p1.y = center.y - height / 2;
                p2.y = center.y + height / 2;
                break;
            case bottomLeft:
            case bottomRight:
                p2.y = p1.y + height;
            case topRight:
            case topLeft:
                p1.y = p2.y - height;
        }

        return this;
    }

    public boolean isPointInside(Point point) {
        if (point.x < p1.x || point.x > p2.x) {
            return false;
        }

        if (point.y < p1.y || point.y > p2.y) {
            return false;
        }

        return true;
    }

    public boolean isAreaInside(Area area) {
        return isPointInside(area.p1) && isPointInside(area.p2);
    }

    @Override
    public String toString() {
        return "Area(" + p1.x + ":" + p1.y + " " + p2.x + ":" + p2.y + ")";
    }

    public Area debugBuild() { // debug purposes
        glColor3f(background.getRed(), background.getGreen(), background.getBlue());

        Point topLeft = this.getPosition(Area.Mode.topLeft);
        Point topRight = this.getPosition(Area.Mode.topRight);
        Point bottomRight = this.getPosition(Area.Mode.bottomRight);
        Point bottomLeft = this.getPosition(Area.Mode.bottomLeft);

        glBegin(GL_QUADS);
            glVertex2f(topLeft.x, topLeft.y);
            glVertex2f(topRight.x, topRight.y);
            glVertex2f(bottomRight.x, bottomRight.y);
            glVertex2f(bottomLeft.x, bottomLeft.y);
        glEnd();

        return this;
    }
}
