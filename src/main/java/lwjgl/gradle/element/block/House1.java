package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class House1 extends MapBlock {
    public House1() {
        super("./textures/" + Constants.TEXTURE_PACK + "/house1.png");

        type = Type.House;
    }
}
