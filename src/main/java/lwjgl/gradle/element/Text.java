package lwjgl.gradle.element;

import de.matthiasmann.twl.utils.PNGDecoder;

import lwjgl.gradle.engine.Area;
import lwjgl.gradle.engine.Constants;
import lwjgl.gradle.engine.Element;
import lwjgl.gradle.engine.Point;
import lwjgl.gradle.util.Color;
import org.lwjgl.BufferUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;


public class Text extends Element {
    /** The string that is rendered on-screen. */
    private StringBuilder string;
    /** The texture object for the bitmap font. */
    private int fontTexture;
    private float scale;
    private boolean enableBackground = false;

    public Text(String string, float scale) {
        this.string = new StringBuilder(string);
        this.scale = scale;
        background = Color.white();

        area = new Area(new Point(0 ,0), new Point(0, 0));

        try {
            setUpTextures();
        } catch (Exception e) {
            cleanUp(true);
        }
    }

    public boolean isBackgroundEnabled() {
        return enableBackground;
    }

    public Text setBackgroundEnabled(boolean value) {
        enableBackground = value;

        return this;
    }

    public Text build() {
        if (isHidden()) return this;

        renderString();

        return this;
    }

    private void setUpTextures() throws IOException {
        fontTexture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        String filename = "./textures/" + Constants.TEXTURE_PACK + "/text.png";
        PNGDecoder decoder = new PNGDecoder(new FileInputStream(filename));
        ByteBuffer buffer = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
                buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private void renderString() {
        String string = this.string.toString();
        glPushAttrib(GL_TEXTURE_BIT | GL_ENABLE_BIT | GL_COLOR_BUFFER_BIT);
        glEnable(GL_CULL_FACE);
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        if (!enableBackground) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_ONE, GL_ONE);
        }
        glPushMatrix();
//        glTranslatef(x, y, 0);
        glColor3f(background.getRed(), background.getGreen(), background.getBlue());
        glBegin(GL_QUADS);

        float mySizeX = 0.5f;
        float mySizeY = 1f;
        float cursorPos = 0f;
        float offsetX = area.getCenter().x;
        float offsetY = area.getCenter().y;

        for (int i = 0; i < string.length(); i++) {
            Area letter = getLetterArea(string.charAt(i));
            Point topLeft = letter.getPosition(Area.Mode.topLeft);
            Point topRight = letter.getPosition(Area.Mode.topRight);
            Point bottomRight = letter.getPosition(Area.Mode.bottomRight);
            Point bottomLeft = letter.getPosition(Area.Mode.bottomLeft);


            glTexCoord2f(topLeft.x, topLeft.y);
            glVertex2f(cursorPos * scale + offsetX, offsetY);

            glTexCoord2f(topRight.x, topRight.y);
            glVertex2f((cursorPos + mySizeX) * scale + offsetX, offsetY);

            glTexCoord2f(bottomRight.x, bottomRight.y);
            glVertex2f((cursorPos + mySizeX) * scale + offsetX, mySizeY * scale + offsetY);

            glTexCoord2f(bottomLeft.x, bottomLeft.y);
            glVertex2f(cursorPos * scale + offsetX, mySizeY * scale + offsetY);

            cursorPos += mySizeX;
        }
        glEnd();
        glPopMatrix();
        glPopAttrib();
    }

    private void cleanUp(boolean asCrash) {
        glDeleteTextures(fontTexture);
        System.exit(asCrash ? 1 : 0);
    }

    public String getText() {
        return string.toString();
    }

    public Text setText(String string) {
        this.string = new StringBuilder(string);

        return this;
    }

    public Area getLetterArea(char letter) {
        float sizeX = 0.0625f;
        float sizeY = 0.125f;
        float offsetX = 0;
        float offsetY = 0;
        float numberStepX = 0.07f;

        switch (letter) {
            case 'a':
                offsetX = 0.07f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'b':
                offsetX = 0.14f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'c':
                offsetX = 0.21f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'd':
                offsetX = 0.28f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'e':
                offsetX = 0.355f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'f':
                offsetX = 0.42f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'g':
                offsetX = 0.49f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'h':
                offsetX = 0.56f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'i':
                offsetX = 0.63f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'j':
                offsetX = 0.69f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'k':
                offsetX = 0.7f + 0.05f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'l':
                offsetX = 0.7f + 0.12f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'm':
                offsetX = 0.7f + 0.20f;
                offsetY = 0.2f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'n':
                offsetX = -0.01f;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'o':
                offsetX = -0.01f + 0.07f;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'p':
                offsetX = -0.01f + 0.07f * 2;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'q':
                offsetX = 0.07f * 3;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'r':
                offsetX = 0.07f * 4;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 's':
                offsetX = 0.07f * 5;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 't':
                offsetX = 0.07f * 6;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'u':
                offsetX = 0.071f * 7;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'v':
                offsetX = 0.072f * 8;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'w':
                offsetX = 0.073f * 9;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'x':
                offsetX = 0.073f * 10;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'y':
                offsetX = 0.073f * 11;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case 'z':
                offsetX = 0.073f * 12;
                offsetY = 0.305f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));

            case '0':
                offsetX = 0.91f;
                offsetY = -0.008f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '1':
                offsetX = 0;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '2':
                offsetX = numberStepX * 1;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '3':
                offsetX = numberStepX * 2;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '4':
                offsetX = numberStepX * 3;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '5':
                offsetX = numberStepX * 4;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '6':
                offsetX = numberStepX * 5;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '7':
                offsetX = numberStepX * 6;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '8':
                offsetX = numberStepX * 7;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));
            case '9':
                offsetX = numberStepX * 8;
                offsetY = 0.1f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));

            case '$':
                offsetX = 0.18f;
                offsetY = -0.01f;
                return new Area(new Point(offsetX, offsetY), new Point(offsetX + sizeX, offsetY + sizeY));

            case ' ':
                return new Area(new Point(0, 0), new Point(0, 0));

        }

        return new Area(new Point(0, 0), new Point(0, 0));
    }
}
