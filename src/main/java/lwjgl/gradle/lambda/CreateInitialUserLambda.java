package lwjgl.gradle.lambda;

import lwjgl.gradle.engine.LocalStore;
import lwjgl.gradle.engine.Window;
import lwjgl.gradle.object.Input;
import org.json.simple.JSONObject;

import java.util.Objects;

public class CreateInitialUserLambda implements Lambda {
    Window window;
    Input input;

    public CreateInitialUserLambda(Window window, Input input) {
        this.window = window;
        this.input = input;
    }

    @Override
    public void execute() {
        String inputValue = input.getValue();
        if (Objects.equals(inputValue, "")) {
            return;
        }

        JSONObject player = LocalStore.createPlayer(inputValue);
        window.setCurrentPlayer(player);
        window.changeScene(0);
    }
}
