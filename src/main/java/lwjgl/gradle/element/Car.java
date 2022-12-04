package lwjgl.gradle.element;

import lwjgl.gradle.engine.*;
import lwjgl.gradle.object.CityMap;

public class Car extends Texture {
    protected CityMap map;
    protected int type;
    protected float moveStep = Constants.MOVE_STEP;
    protected Direction direction;
    public Car(int type) {
        super("./textures/" + Constants.TEXTURE_PACK + "/car" + type + ".png");
        this.type = type;
        this.setPosition(Point.getBlank().plus(new Point(0, 0.03f)));
        this.setSize(0.15f, 0.3f);
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean hasMap() {
        return map != null;
    }

    public Car setMap(CityMap map) {
        this.map = map;

        return this;
    }

    public float getMoveStep() {
        return moveStep;
    }

    public Car setMoveStep(float step) {
        this.moveStep = step;

        return this;
    }

    public MapBlock getBlockByPoint(Point point) {
        if (!hasMap()) return null;

        for (String key : map.getChildren().keySet()) {
            Element b = map.getChildren().get(key);

            if (b.getArea().isPointInside(point)) {
                return (MapBlock) b;
            }
        }

        return null;
    }

    protected Point getNextPosition(Direction direction) {
        return switch (direction) {
            case UP -> new Point(getOrigin().x, getOrigin().y + moveStep);
            case DOWN -> new Point(getOrigin().x, getOrigin().y - moveStep);
            case LEFT -> new Point(getOrigin().x - moveStep, getOrigin().y);
            case RIGHT -> new Point(getOrigin().x + moveStep, getOrigin().y);
            case UP_LEFT -> new Point(getOrigin().x - moveStep / 2, getOrigin().y + moveStep / 2);
            case UP_RIGHT -> new Point(getOrigin().x + moveStep / 2, getOrigin().y + moveStep / 2);
            case DOWN_LEFT -> new Point(getOrigin().x - moveStep / 2, getOrigin().y - moveStep / 2);
            case DOWN_RIGHT -> new Point(getOrigin().x + moveStep / 2, getOrigin().y - moveStep / 2);
        };
    }

    protected Car handleRotation(Direction direction) {
        if (direction == null) return this;

        switch (direction) {
            case UP -> this.setRotation(0);
            case DOWN -> this.setRotation(180);
            case LEFT -> this.setRotation(90);
            case RIGHT -> this.setRotation(-90);
            case UP_LEFT -> this.setRotation(45);
            case UP_RIGHT -> this.setRotation(-45);
            case DOWN_LEFT -> this.setRotation(135);
            case DOWN_RIGHT -> this.setRotation(-135);
        }

        return this;
    }
}
