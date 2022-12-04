package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House7 extends MapBlock {
    public House7() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house7.png");

        type = Type.House;
    }
}
