package lwjgl.gradle.engine;

import java.util.HashMap;
import java.util.Map;

public interface IObject extends IElement {
    Map<String, Element> children = new HashMap<>();

    public IObject move(Direction direction, float step);
}
