package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House4 extends MapBlock {
    public House4() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house4.png");

        type = Type.House;
    }
}
