package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House11 extends MapBlock {
    public House11() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house11.png");

        type = Type.House;
    }
}
