package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House8 extends MapBlock {
    public House8() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house8.png");

        type = Type.House;
    }
}
