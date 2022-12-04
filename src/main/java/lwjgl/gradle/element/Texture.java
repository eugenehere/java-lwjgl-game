package lwjgl.gradle.element;

import de.matthiasmann.twl.utils.PNGDecoder;
import lwjgl.gradle.engine.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class Texture extends Element {
    private Direction textureRotation = Direction.UP;
    private List<Point> tCoords = new ArrayList<>(); // texture coordinates
    private final int textureId;

    public Texture(String path) {
        int imageWidth = 0;
        int imageHeight = 0;
        ByteBuffer buffer = null;

        try {
            PNGDecoder decoder = new PNGDecoder(new FileInputStream(path));
            imageWidth = decoder.getWidth();
            imageHeight = decoder.getHeight();
            buffer = BufferUtils.createByteBuffer(4 * imageWidth * imageHeight);
            decoder.decode(buffer, imageWidth * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
        } catch (Exception e) {
            System.out.println(e);
        }

        textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA,
                imageWidth,
                imageHeight,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                buffer
        );

        updateTextureCoordinates();

        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        area = new Area(p1, p2);
    }

    private Texture updateTextureCoordinates() {
        tCoords = new ArrayList<>();

        switch (textureRotation) {
            case UP:
                tCoords.add(new Point(0, 0));
                tCoords.add(new Point(1, 0));
                tCoords.add(new Point(1, 1));
                tCoords.add(new Point(0, 1));
                break;
            case RIGHT:
                tCoords.add(new Point(0, 1));
                tCoords.add(new Point(0, 0));
                tCoords.add(new Point(1, 0));
                tCoords.add(new Point(1, 1));
                break;
            case DOWN:
                tCoords.add(new Point(1, 1));
                tCoords.add(new Point(0, 1));
                tCoords.add(new Point(0, 0));
                tCoords.add(new Point(1, 0));
                break;
            case LEFT:
                tCoords.add(new Point(1, 0));
                tCoords.add(new Point(1, 1));
                tCoords.add(new Point(0, 1));
                tCoords.add(new Point(0, 0));
                break;
        }

        return this;
    }

    public Direction getTextureRotation() {
        return textureRotation;
    }

    public Texture setTextureRotation(Direction direction) {
        switch (textureRotation) {
            case UP:
            case DOWN:
                if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) {
                    area.rotate();
                }
                break;
            case LEFT:
            case RIGHT:
                if (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) {
                    area.rotate();
                }
                break;
        }

        textureRotation = direction;
        updateTextureCoordinates();

        return this;
    }

    public Texture rotateTextureBy(float angleStep) {
        this.angle += angleStep;
        updateTextureCoordinates();

        return this;
    }

    public Texture setSize(float width, float height) {
        super.setSize(width, height);

        return this;
    }

    public Texture setPosition(Point point) {
        super.setPosition(point);

        return this;
    }

    public Texture build() {
        if (isHidden) return this;

        super.build();

        Point topLeft = area.getPosition(Area.Mode.topLeft);
        Point topRight = area.getPosition(Area.Mode.topRight);
        Point bottomRight = area.getPosition(Area.Mode.bottomRight);
        Point bottomLeft = area.getPosition(Area.Mode.bottomLeft);

        GL11.glPushMatrix();
        GL11.glLoadIdentity();

        GL11.glTranslatef(getOrigin().x, getOrigin().y, 0);

        GL11.glRotatef(angle, 0, 0, 1);

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glColor3f(1, 1, 1);
            GL11.glTexCoord2f(tCoords.get(0).x, tCoords.get(0).y);
            GL11.glVertex2f(topLeft.x, topLeft.y);
            GL11.glTexCoord2f(tCoords.get(1).x, tCoords.get(1).y);
            GL11.glVertex2f(topRight.x, topRight.y);
            GL11.glTexCoord2f(tCoords.get(2).x, tCoords.get(2).y);
            GL11.glVertex2f(bottomRight.x, bottomRight.y);
            GL11.glTexCoord2f(tCoords.get(3).x, tCoords.get(3).y);
            GL11.glVertex2f(bottomLeft.x, bottomLeft.y);
        GL11.glEnd();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

        GL11.glPopMatrix();

        return this;
    }
}
