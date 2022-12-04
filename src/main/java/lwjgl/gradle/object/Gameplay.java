package lwjgl.gradle.object;

import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Text;
import lwjgl.gradle.engine.Area;
import lwjgl.gradle.engine.Object;
import lwjgl.gradle.engine.Point;
import lwjgl.gradle.util.Color;

public class Gameplay extends Object {
    public Gameplay() {
        Rectangle topBar = (Rectangle) new Rectangle()
                .setBackground(Color.black())
                .setMode(Area.Mode.topLeft)
                .setPosition(new Point(-1, 1))
                .setWidth(2)
                .setHeight(0.1f);

        Text score = (Text) new Text("", 0.1f)
                .setPosition(new Point(-0.95f, 0.9f));

        Text money = (Text) new Text("", 0.1f)
                .setPosition(new Point(0.5f, 0.9f));

        children.put("base", topBar);
        children.put("score", score);
        children.put("money", money);
    }

    public Gameplay build(int score, int money) {
        children.get("base").build();
        ((Text) children.get("score")).setText("score " + score).build();
        ((Text) children.get("money")).setText("money " + money + "$").build();

        return this;
    }
}
