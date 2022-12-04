package lwjgl.gradle.engine;

import lwjgl.gradle.util.Color;

public class Element implements IElement {
    protected Point origin = Point.getBlank();
    protected Area area;
    protected Area.Mode mode = Area.Mode.center;
    protected Color background;
    protected float angle = 0;
    protected boolean isHidden = false;

    public Point getOrigin() {
        return origin;
    }

    public Element setOrigin(Point point) {
        this.origin = point;

        return this;
    }

    public Area getArea() {
        return area;
    }

    public Element setArea(Area area) {
        this.area = area;

        return this;
    }

    public Element moveOrigin(Direction direction, float step) {
        this.origin.moveBy(direction, step);

        return this;
    }

    public Point getPosition() {
        return area.getPosition(mode);
    }

    public Element setPosition(Point destination) {
        area.setPosition(mode, destination);

        return this;
    }

    public Element setSize(float width, float height) {
        area.setWidth(mode, width).setHeight(mode, height);

        return this;
    }

    public float getWidth() {
        return area.getWidth();
    }

    public IElement setWidth(float width) {
        area.setWidth(mode, width);

        return this;
    }

    public float getHeight() {
        return area.getHeight();
    }

    public Element setHeight(float height) {
        area.setHeight(mode, height);

        return this;
    }

    public Area.Mode getMode() {
        return mode;
    }

    public Element setMode(Area.Mode mode) {
        this.mode = mode;

        return this;
    }

    public Color getBackground() {
        return background;
    }

    public Element setBackground(Color color) {
        background = color;

        return this;
    }

    public float getRotation() {
        return angle;
    }

    public Element setRotation(float angle) {
        this.angle = angle;

        return this;
    }

    public Element move(Direction direction, float step) {
        area.moveBy(Point.getBlank().moveBy(direction, step));

        return this;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public IElement hide() {
        isHidden = true;

        return this;
    }

    public IElement show() {
        isHidden = false;

        return this;    }

    public Element build() {
        return null;
    }
}
