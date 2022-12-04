package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House6 extends MapBlock {
    public House6() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house6.png");

        type = Type.House;
    }
}
