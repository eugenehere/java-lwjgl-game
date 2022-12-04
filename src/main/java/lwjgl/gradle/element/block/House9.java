package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House9 extends MapBlock {
    public House9() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house9.png");

        type = Type.House;
    }
}
