package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.Window;

public class SwitchToCreatePlayerSceneLambda implements Lambda {
    Window window;

    public SwitchToCreatePlayerSceneLambda(Window window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.changeScene(3);
    }
}
