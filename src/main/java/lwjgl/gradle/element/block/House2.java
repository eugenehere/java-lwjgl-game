package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House2 extends MapBlock {
    public House2() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house2.png");

        type = Type.House;
    }
}
