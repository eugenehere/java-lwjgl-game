package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House10 extends MapBlock {
    public House10() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house10.png");

        type = Type.House;
    }
}
