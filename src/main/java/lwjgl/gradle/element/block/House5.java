package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House5 extends MapBlock {
    public House5() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house5.png");

        type = Type.House;
    }
}
