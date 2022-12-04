package lwjgl.gradle.engine;

import java.util.HashMap;
import java.util.Map;

public class Object implements IObject { // todo add width height and position
    protected Map<String, Element> children = new HashMap<>();

    public Object move(Direction direction, float step) {
        for (String key : children.keySet()) {
            children.get(key).move(direction, step);
        }

        return this;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public IElement hide() {
        return null;
    }

    @Override
    public IElement show() {
        return null;
    }

    public Map<String, Element> getChildren() {
        return children;
    }

    public Element getBaseElement() {
        return children.get("base");
    }

    public Element getBase() {
        if (!hasBase()) return null;

        return children.get("base");
    }

    public boolean hasBase() {
        return getBaseElement() != null;
    }

    @Override
    public Area getArea() {
        return null;
    }

    @Override
    public IElement setArea(Area area) {
        return null;
    }

    public Point getPosition() {
        if (!hasBase()) return null;

        return children.get("base").getPosition();
    }

    public Object setPosition(Point destination) {
        if (!hasBase()) return this;
        children.get("base").setPosition(destination);

        return this;
    }

    public float getWidth() {
        if (!hasBase()) return 0;

        return children.get("base").getWidth();
    }

    public Object setWidth(float width) {
        if (!hasBase()) return this;

        getBaseElement().setWidth(width);

        return this;
    }

    public float getHeight() {
        if (!hasBase()) return 0;


        return children.get("base").getHeight();
    }

    public Object setHeight(float height) {
        if (!hasBase()) return this;

        getBaseElement().setHeight(height);

        return this;
    }

    @Override
    public Area.Mode getMode() {
        return null;
    }

    @Override
    public Object setMode(Area.Mode mode) {
        return null;
    }

    public Object setSize(float width, float height) {
        if (!hasBase()) return this;

        getBaseElement().setSize(width, height);

        return this;
    }

    public Object setChildrenMode(Area.Mode mode) {
        children.forEach((key, element) -> element.setMode(mode));

        return this;
    }

    public Object build() {
        for (String key : children.keySet()) {
            children.get(key).build();
        }

        return this;
    }
}
