package lwjgl.gradle.object;

import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.engine.Area;
import lwjgl.gradle.engine.INavigationItem;
import lwjgl.gradle.engine.Object;
import lwjgl.gradle.engine.Point;
import lwjgl.gradle.lambda.Lambda;
import lwjgl.gradle.util.Color;

public class Button extends Object implements INavigationItem {
    private float shadowOffset = 0.03f;
    private Color backgroundColor = Color.black().setBlue(0.8f);
    private Color shadowColor = Color.black();
    private Color textColor = Color.white();
    private boolean isActive = false;
    private Lambda callback;

    public Button(String string) {
        Rectangle shadow = new Rectangle();
        Rectangle overlay = new Rectangle();
        Text text = new Text(string, 0.1f);

        children.put("base", shadow);
        children.put("overlay", overlay);
        children.put("text", text);

        updateChildren();
    }

    public void updateChildren() {
        Point position = getPosition();

        if (isActive) {
            children.get("base")
                    .setBackground(Color.duplicate(shadowColor).setBrightnessBy(0.1f));
            children.get("text")
                    .setBackground(textColor)
                    .setPosition(Point.duplicate(position).plus(new Point(-0.4f - 0.02f, -0.03f - 0.02f)));
            children.get("overlay")
                    .setBackground(backgroundColor.setBlue(1f))
                    .setPosition(Point.duplicate(position).plus(new Point(shadowOffset - 0.02f, shadowOffset - 0.02f)));
        } else {
            children.get("base")
                    .setBackground(shadowColor);
            children.get("text")
                    .setBackground(textColor)
                    .setPosition(Point.duplicate(position).plus(new Point(-0.4f, -0.03f)));
            children.get("overlay")
                    .setBackground(backgroundColor.setBlue(0.8f))
                    .setPosition(Point.duplicate(position).plus(new Point(shadowOffset, shadowOffset)));
        }
    }

    public Button setPosition(Point destination) {
        super.setPosition(destination);

        updateChildren();
        // todo refactor moveCenter of each element by difference between base.currentCenter and destination

        return this;
    }

    public Button setSize(float width, float height) {
        super.setSize(width, height);
        children.get("overlay").setSize(width, height);

        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Button setIsActive(boolean isActive) {
        this.isActive = isActive;

        updateChildren();

        return this;
    }

    public Button setCallback(Lambda lambda) {
        this.callback = lambda;

        return this;
    }

    public Button submit() {
        if (callback == null) return this;

        callback.execute();

        return this;
    }

    public Button setChildrenMode(Area.Mode mode) {
        super.setChildrenMode(mode);

        return this;
    }

    public Button build() {
        children.get("base").build();
        children.get("overlay").build();
        children.get("text").build();

        return this;
    }
}
