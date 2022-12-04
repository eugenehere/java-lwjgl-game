package lwjgl.gradle.engine;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Navigation {
    private final List<INavigationItem> items = new ArrayList<>();
    private int selectedItem = 0;
    private int keyPressed = 0;

    public Navigation() {}

    public Navigation setPosition(Point point) {
        Point position = items.get(0).getPosition();
        Point difference = position.getDifference(point);

        items.forEach((item) -> item.setPosition(item.getPosition().plus(difference)));

        return this;
    }

    public Navigation addItem(INavigationItem object) {
        items.add(object);

        return this;
    }

    public Navigation selectItem(int itemIndex) {
        items.forEach((item) -> item.setIsActive(false));
        items.get(itemIndex).setIsActive(true);

        return this;
    }

    public Navigation selectNextItem() {
        if (selectedItem + 1 >= items.size()) return this;

        selectItem(++selectedItem);

        return this;
    }

    public Navigation selectPreviousItem() {
        if (selectedItem - 1 < 0) return this;

        selectItem(--selectedItem);

        return this;
    }

    public Navigation submitSelected() {
        items.get(selectedItem).submit();

        return this;
    }

    private void handleControl() {
        boolean isUpPressed = KeyListener.isKeyPressed(GLFW_KEY_UP);
        boolean isDownPressed = KeyListener.isKeyPressed(GLFW_KEY_DOWN);
        boolean isEnterPressed = KeyListener.isKeyPressed(GLFW_KEY_ENTER);
        boolean isAnyKeyPressed = isUpPressed || isDownPressed || isEnterPressed;

        if (!isAnyKeyPressed && keyPressed != 0) {
            keyPressed = 0;
        }

        if (isUpPressed && keyPressed != GLFW_KEY_UP) {
            keyPressed = GLFW_KEY_UP;
            selectPreviousItem();
        }

        if (isDownPressed && keyPressed != GLFW_KEY_DOWN) {
            keyPressed = GLFW_KEY_DOWN;
            selectNextItem();
        }

        if (isEnterPressed && keyPressed != GLFW_KEY_ENTER) {
            keyPressed = GLFW_KEY_ENTER;
            submitSelected();
        }
    }

    public Navigation build() {
        handleControl();

        items.forEach(IElement::build);

        return this;
    }
}
