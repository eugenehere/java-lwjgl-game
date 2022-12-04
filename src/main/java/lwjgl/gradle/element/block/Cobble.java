package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;

public class Cobble extends MapBlock {
    public Cobble() {
        super("./textures/" + Constants.TEXTURE_PACK + "/blank.png");
    }
}
