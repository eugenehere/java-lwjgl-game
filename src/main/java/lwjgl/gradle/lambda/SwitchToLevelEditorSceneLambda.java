package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.Window;

public class SwitchToLevelEditorSceneLambda implements Lambda {
    Window window;

    public SwitchToLevelEditorSceneLambda(Window window) {
        this.window = window;
    }

    @Override
    public void execute() {
        window.changeScene(0);
    }
}
