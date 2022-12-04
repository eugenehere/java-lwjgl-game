package lwjgl.gradle.engine;

public interface IElement {
    Area area = null;
    Area.Mode mode = Area.Mode.center;
    boolean isHidden = false;

    public Area getArea();
    public IElement setArea(Area area);

    public Point getPosition();
    public IElement setPosition(Point destination);

    public IElement setSize(float width, float height);

    public float getWidth();
    public IElement setWidth(float height);

    public float getHeight();
    public IElement setHeight(float height);

    public Area.Mode getMode();
    public IElement setMode(Area.Mode mode);

    public IElement move(Direction direction, float step);

    public boolean isHidden();
    public IElement hide();
    public IElement show();

    public IElement build();
}
