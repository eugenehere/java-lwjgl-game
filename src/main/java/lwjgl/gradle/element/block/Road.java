package lwjgl.gradle.element.block;

import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.engine.Constants;
import lwjgl.gradle.engine.Direction;
import lwjgl.gradle.object.TrafficLight;

public class Road extends MapBlock {
    private TrafficLight trafficLight;
    public Road(int index) {
        super("./textures/" + Constants.TEXTURE_PACK + "/road" + index + ".png");

        collision = false;
        type = Type.Road;
    }

    public boolean hasTrafficLight() {
        return trafficLight != null;
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public Road setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
        System.out.println("RoadX position " + this.area.getCenter());
        trafficLight.setPosition(this.area.getCenter());

        return this;
    }

    @Override
    public Road move(Direction direction, float step) {
        super.move(direction, step);
        if (hasTrafficLight()) {
            trafficLight.move(direction, step);
        }

        return this;
    }

    @Override
    public Road build() {
        super.build();

        return this;
    }

    public Road buildTrafficLight() {
        if (!hasTrafficLight()) return this;

        trafficLight.build();

        return this;
    }
}