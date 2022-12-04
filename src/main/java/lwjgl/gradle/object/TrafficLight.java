package lwjgl.gradle.object;

import lwjgl.gradle.element.Rectangle;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.engine.Object;
import lwjgl.gradle.util.Color;

public class TrafficLight extends Object {
    private TrafficLightHandler trafficLightHandler;

    public TrafficLight() {
        super();
        System.out.println("Creating TrafficLight");

        children.put("base", new Element()
                .setArea(Area.getBlank())
                .setPosition(Point.getBlank()));

        trafficLightHandler = new TrafficLightHandler();
        trafficLightHandler.getStateByIndex(0).forEach((direction, value) -> {
            Texture green = createTrafficLightTexture(getBase().getPosition(), direction, true);
            Texture red = createTrafficLightTexture(getBase().getPosition(), direction, false);

            children.put("side-" + direction + "-green", green);
            children.put("side-" + direction + "-red", red);
        });

        trafficLightHandler.onStateChange(() -> {
            trafficLightHandler.getCurrentState().forEach((direction, value) -> {
                children.get("side-" + direction + "-" + (value ? "green" : "red")).show();
                children.get("side-" + direction + "-" + (value ? "red" : "green")).hide();
            });
        });

        trafficLightHandler.setStateDuration(5000);

        trafficLightHandler.run();
    }

    public boolean getValueByDirection(Direction direction) {
        return trafficLightHandler.getValueByDirection(direction);
    }

    @Override
    public TrafficLight setPosition(Point destination) {
        super.setPosition(destination);

        for (String key : children.keySet()) {
//            float angle = children.get(key).getRotation();
//            children.get(key).setRotation(0);
            children.get(key).setPosition(destination);
//            children.get(key).setRotation(angle);

        }

        return this;
    }

    private Texture createTrafficLightTexture(Point base, Direction direction, boolean value) {
        String textureName;
        if (value) {
            textureName = "trafficlight-green.png";
        } else {
            textureName = "trafficlight-red.png";
        }

        String texturePath = "./textures/" + Constants.TEXTURE_PACK + "/" + textureName;
        Texture texture = new Texture(texturePath);
        texture.setPosition(base);
        Direction textureDirection = Direction.getOppositeDirection(direction);
        texture.setSize(0.125f, 0.1875f);
        texture.setTextureRotation(textureDirection);
//        texture.setRotation(-textureDirection.getAngle()); // opengl angle direction is different (-90 goes right)
        texture.moveOrigin(direction, 0.5f);

        Direction rightDirection = Direction.getByAngle(textureDirection.getAngle() + 90);
        texture.moveOrigin(rightDirection, 0.22f);

        texture.moveOrigin(textureDirection, 0.22f);

        return texture;
    }

    public Object move(Direction direction, float step) {
        for (String key : children.keySet()) {
            children.get(key).moveOrigin(direction, step);
        }

        return this;
    }

    @Override
    public TrafficLight build() {
        super.build();

        return this;
    }
}
