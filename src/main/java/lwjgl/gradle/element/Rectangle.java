package lwjgl.gradle.element;

import lwjgl.gradle.engine.*;
import lwjgl.gradle.util.Color;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends Element {

    public Rectangle() {
        Point p1 = new Point(0 ,0);
        Point p2 = new Point(1, 1);
        area = new Area(p1 ,p2);
    }

    public Rectangle setSize(float width, float height) {
        super.setSize(width, height);

        return this;
    }

    public Rectangle setPosition(float posX, float posY) { // todo refactor args to Point
        super.setPosition(new Point(posX, posY));

        return this;
    }

    public Rectangle setBackground(Color color) {
        super.setBackground(color);

        return this;
    }

    public Rectangle build() {
        if (background != null ) {
            glColor3f(background.getRed(), background.getGreen(), background.getBlue());
        }

        Point topLeft = area.getPosition(Area.Mode.topLeft);
        Point topRight = area.getPosition(Area.Mode.topRight);
        Point bottomRight = area.getPosition(Area.Mode.bottomRight);
        Point bottomLeft = area.getPosition(Area.Mode.bottomLeft);

        glBegin(GL_QUADS);
            glVertex2f(topLeft.x, topLeft.y);
            glVertex2f(topRight.x, topRight.y);
            glVertex2f(bottomRight.x, bottomRight.y);
            glVertex2f(bottomLeft.x, bottomLeft.y);
        glEnd();

        return this;
    }
}
