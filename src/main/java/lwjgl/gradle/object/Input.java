package lwjgl.gradle.object;

import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.engine.Object;
import lwjgl.gradle.lambda.Lambda;
import lwjgl.gradle.lambda.KeyListenerLambda;
import lwjgl.gradle.util.Color;

public class Input extends Object implements INavigationItem {
    private String value = "";
    private String title;
    private String placeholder;
    private int maxLength;
    private boolean isActive = false;
    private Color backgroundColor;
    private Color shadowColor;
    private Lambda callback;
    private float shadowOffset = 0.03f;

    public Input() {
        title = "enter text";
        placeholder = "type here";
        maxLength = 10;
        isActive = false;
        backgroundColor = Color.black().setBlue(0.8f);
        shadowColor = Color.black();

        Rectangle shadow = new Rectangle();
        Rectangle overlay = new Rectangle();
        Text title = new Text(getTitle(), 0.1f);
        Text placeholder = new Text(getPlaceholder(), 0.1f);

        // config
        shadow.setPosition(Point.getBlank()).setBackground(shadowColor);
        overlay
                .setPosition(Point.getBlank().plus(new Point(shadowOffset, shadowOffset - 0.07f)))
                .setBackground(backgroundColor);
        title.setPosition(Point.getBlank().plus(new Point(-0.45f, 0.05f)));
        placeholder.setPosition(Point.getBlank().plus(new Point(-0.45f, -0.09f)));

        children.put("base", shadow);
        children.put("overlay", overlay);
        children.put("title", title);
        children.put("placeholder", placeholder);

        updateChildren();
    }

    public String getValue() {
        return value;
    }

    public Input setValue(String value) {
        if (value.length() > maxLength) return this;

        this.value = value;
        updateChildren();

        return this;
    }

    public Input setPosition(Point destination) {
        Point difference = children.get("base").getPosition().getDifference(destination);

        children.get("overlay").setPosition(children.get("overlay").getPosition().plus(difference));
        children.get("title").setPosition(children.get("title").getPosition().plus(difference));
        children.get("placeholder").setPosition(children.get("placeholder").getPosition().plus(difference));

        super.setPosition(destination);

        return this;
    }

    private void updateChildren() {
        if (value.length() > 0) {
            ((Text) children.get("placeholder")).setText(value);
        } else {
            ((Text) children.get("placeholder")).setText(getPlaceholder());
        }

        if (isActive()) {
            children.get("placeholder")
                    .setPosition(children.get("base").getPosition().plus(new Point(-0.48f, -0.12f)));
            children.get("overlay")
                    .setPosition(children.get("base").getPosition().plus(new Point(0, shadowOffset - 0.09f)))
                    .setBackground(backgroundColor.setBlue(1f));

            return;
        }

        children.get("placeholder")
                .setPosition(children.get("base").getPosition().plus(new Point(-0.45f, -0.09f)));
        children.get("overlay")
                .setPosition(children.get("base").getPosition().plus(new Point(shadowOffset, shadowOffset - 0.07f)))
                .setBackground(backgroundColor.setBlue(0.8f));
    }

    public boolean isActive() {
        return isActive;
    }

    public Input setIsActive(boolean value) {
        isActive = value;

        updateChildren();

        if (value) {
            KeyListener.addListener("inputAlphabetKeys", new KeyListenerLambda(this));
        } else {
            KeyListener.removeListener("inputAlphabetKeys");
        }

        return this;
    }

    public Input submit() {
        if (callback == null) return this;

        callback.execute();

        return this;
    }

    public Input setCallback(Lambda callback) {
        this.callback = callback;

        return this;
    }

    public String getTitle() {
        return title;
    }

    public Input setTitle(String text) {
        ((Text) children.get("title")).setText(text);
        title = text;

        return this;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Input setPlaceholder(String text) {
        ((Text) children.get("placeholder")).setText(text);
        placeholder = text;

        return this;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public Input setMaxLength(int value) {
        maxLength = value;

        return this;
    }

    public Input setSize(float width, float height) {
        super.setSize(width, height);
        children.get("overlay").setSize(width, height - 0.15f);

        return this;
    }

    public Input build() {
        children.get("base").build();
        children.get("overlay").build();
        children.get("placeholder").build();
        children.get("title").build();

        return this;
    }
}
