package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.Window;

public class SwitchToSettingsSceneLambda implements Lambda {
    Window window;

    public SwitchToSettingsSceneLambda(Window window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.changeScene(2);
    }
}
