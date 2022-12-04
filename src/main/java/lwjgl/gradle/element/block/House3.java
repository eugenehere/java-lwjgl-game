package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House3 extends MapBlock {
    public House3() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house3.png");

        type = Type.House;
    }
}
