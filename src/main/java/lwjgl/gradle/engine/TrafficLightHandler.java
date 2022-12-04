package lwjgl.gradle.engine;

import lwjgl.gradle.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficLightHandler {
    private int currentStateIndex = 0;
    private final List<Map<Direction, Boolean>> states = new ArrayList<>();
    private int stateDuration = 1000;
    private Runnable onStateChange;

    public TrafficLightHandler() {
        Map<Direction, Boolean> state1 = new HashMap<>();
        state1.put(Direction.UP, false);
        state1.put(Direction.RIGHT, true);
        state1.put(Direction.DOWN, false);
        state1.put(Direction.LEFT, true);

        Map<Direction, Boolean> state2 = new HashMap<>();
        state2.put(Direction.UP, true);
        state2.put(Direction.RIGHT, false);
        state2.put(Direction.DOWN, true);
        state2.put(Direction.LEFT, false);

        states.add(state1);
        states.add(state2); // todo use setters
    }

    public void onStateChange(Runnable lambda) {
        this.onStateChange = lambda;
    }

    public void run() {
        onStateChange.run();
        setTimeout();
    }

    public int getStateDuration() {
        return stateDuration;
    }

    public void setStateDuration(int duration) {
        stateDuration = duration;
    }

    public Map<Direction, Boolean> getStateByIndex(int index) {
        return states.get(index);
    }

    public void addState(Map<Direction, Boolean> state) {
        states.add(state);
    }

    public Map<Direction, Boolean> getCurrentState() {
        return getStateByIndex(currentStateIndex);
    }

    public boolean getValueByDirection(Direction direction) {
        return getCurrentState().get(direction);
    }

    private void setTimeout() {
        Util.setTimeout(getHandlerCallback(), stateDuration);
    }

    private Runnable getHandlerCallback() {
        return () -> {
            setTimeout();
            if (onStateChange != null) {
                onStateChange.run();
            }

            if (currentStateIndex + 1 == states.size()) {
                currentStateIndex = 0;

                return;
            }

            currentStateIndex++;
        };
    }
}
