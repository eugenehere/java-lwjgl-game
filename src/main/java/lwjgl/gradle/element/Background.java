package lwjgl.gradle.element;

import lwjgl.gradle.engine.Constants;
import lwjgl.gradle.engine.Direction;
import lwjgl.gradle.engine.Point;

public class Background extends Texture {
    private Direction backgroundAnimationDirection = Direction.LEFT;
    private final float backgroundAnimationRange = 0.43f;

    public Background() {
        super("./textures/" + Constants.TEXTURE_PACK + "/background2.png");

        this.setPosition(Point.getBlank()).setSize(2.875f, 2f);
    }

    public Background build() {
        this.move(backgroundAnimationDirection, 0.0005f);
        if (this.getPosition().x < -backgroundAnimationRange) {
            backgroundAnimationDirection = Direction.RIGHT;
        }
        if (this.getPosition().x > backgroundAnimationRange) {
            backgroundAnimationDirection = Direction.LEFT;
        }

        super.build();

        return this;
    }
}
