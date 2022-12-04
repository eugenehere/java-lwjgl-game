package lwjgl.gradle.lambda;

import lwjgl.gradle.scene.LevelScene;

public class IncreaseScoreLambda implements Lambda {
    LevelScene levelScene;

    public IncreaseScoreLambda(LevelScene levelScene) {
        this.levelScene = levelScene;
    }

    @Override
    public void execute() {
        levelScene.increaseScore();
    }
}
