package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.Window;

public class SwitchToLevelSceneLambda implements Lambda {
    Window window;

    public SwitchToLevelSceneLambda(Window window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.changeScene(1);
    }
}
