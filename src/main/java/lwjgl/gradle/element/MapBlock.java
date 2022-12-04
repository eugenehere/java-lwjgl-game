package lwjgl.gradle.element;

import lwjgl.gradle.engine.Direction;
import lwjgl.gradle.engine.Point;

public class MapBlock extends Texture {
    public static enum Type {
        House,
        Road,
        Element,
    }
    protected Type type;
    protected boolean collision = true;
    protected boolean hasOrdered = false;
    protected boolean hasBoost = false;

    public MapBlock(String path) {
        super(path);
    }

    public boolean typeOf(Type type) {
        if (this.type == null) return false;

        return this.type.equals(type);
    }

    public MapBlock setSize(float width, float height) {
        super.setSize(width, height);

        return this;
    }

    public MapBlock setPosition(Point point) {
        super.setPosition(point);

        return this;
    }

    public MapBlock setHasOrdered(boolean hasOrdered) {
        this.hasOrdered = hasOrdered;

        return this;
    }

    public boolean hasOrdered() {
        return hasOrdered;
    }

    public boolean hasBoost() {
        return hasBoost;
    }

    public MapBlock setHasBoost(boolean hasBoost) {
        this.hasBoost = hasBoost;

        return this;
    }

    public boolean hasCollision() {
        return collision;
    }

    public MapBlock move(Direction direction, float step) {
        super.move(direction, step);

        return this;
    }
}
