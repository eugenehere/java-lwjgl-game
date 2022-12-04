package lwjgl.gradle.object;

import lwjgl.gradle.element.Bot;
import lwjgl.gradle.element.MapBlock;
import lwjgl.gradle.element.Texture;
import lwjgl.gradle.element.block.*;
import lwjgl.gradle.engine.*;
import lwjgl.gradle.engine.Object;
import lwjgl.gradle.util.Number;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CityMap extends Object {
    protected final Texture boostTexture;
    protected final Texture orderTexture;
    private final Animator orderTextureAnimator = new Animator();
    private final Area mapArea = Area.getBlank();
    private Texture pointer;
    private List<Bot> bots = new ArrayList<>();

    public CityMap() {
        float objectSize = Constants.OBJECT_SIZE;
        String[][] mapScheme = Constants.MAP_SCHEME;

        boostTexture = new Texture("./textures/" + Constants.TEXTURE_PACK + "/boost.png")
                .setSize(Constants.OBJECT_SIZE / 2, Constants.OBJECT_SIZE / 2)
                .setPosition(Point.getBlank());

        orderTexture = new Texture("./textures/" + Constants.TEXTURE_PACK + "/pizza.png")
                .setSize(Constants.OBJECT_SIZE, Constants.OBJECT_SIZE)
                .setPosition(Point.getBlank());

        // traffic jams pick location
//        int baseX = 23;
//        int baseY = 7;

        int baseX = 13;
        int baseY = 13;

        for (int y = 0; y < mapScheme.length; y++) {
            String[] row = mapScheme[y];

            for (int x = 0; x < row.length; x++) {
                String blockCode = row[x];
                int id = Integer.parseInt(blockCode.substring(0, blockCode.length() - 1));
                char directionCode = blockCode.charAt(blockCode.length() - 1);

                Direction direction = switch (directionCode) {
                    case 'u' -> Direction.UP;
                    case 'd' -> Direction.DOWN;
                    case 'l' -> Direction.LEFT;
                    case 'r' -> Direction.RIGHT;
                    default -> Direction.UP;
                };

                MapBlock block = getBlock(id, direction)
                        .setSize(objectSize, objectSize)
                        .setPosition(new Point(x * objectSize, -y * objectSize));

                if (y == mapScheme.length - 1 && x == 0) { // bottom left point of the map
                    mapArea.setBase(block.getArea().getBase());
                }

                if (baseX == x && baseY == y) {
                    children.put("base", block);
                    continue;
                }

                String hashMapId = UUID.randomUUID().toString();
                children.put(hashMapId, block);
            }
        }

        mapArea.setWidth(Area.Mode.bottomLeft, mapScheme[0].length * objectSize);
        mapArea.setHeight(Area.Mode.bottomLeft, mapScheme.length * objectSize);
        setPosition(Point.getBlank());

        // init traffic lights
        children.forEach((hash, block) -> {
            if (block instanceof RoadX) {
                ((RoadX) block).setTrafficLight(new TrafficLight());
            }
        });

        setOrder();
        setBoost();
    }

    public List<Bot> getBots() {
        return bots;
    }

    public CityMap setBots(List<Bot> bots) {
        this.bots = bots;

        return this;
    }

    public CityMap setPointer(Texture pointer) {
        this.pointer = pointer;
        calculatePointerPosition();

        return this;
    }

    public CityMap setPosition(Point destination) { // todo move to the parent class
        Point current = super.getPosition();
        Point difference = current.getDifference(destination);
        mapArea.moveBy(difference);

        for (String key : children.keySet()) {
            MapBlock block = (MapBlock) children.get(key);
            block.setPosition(block.getPosition().plus(difference));
        }

        orderTexture.getOrigin().plus(difference);
        boostTexture.getOrigin().plus(difference);
        for (Bot bot : bots) {
            bot.getOrigin().plus(difference);
            bot.getDestination().plus(difference);
        }
        calculatePointerPosition();

        return this;
    }

    public boolean moveMap(Direction direction) {
        float moveStep = Constants.MOVE_STEP;
        Point step = switch (direction) {
            case UP -> new Point(0, moveStep);
            case DOWN -> new Point(0, -moveStep);
            case RIGHT -> new Point(moveStep, 0);
            case LEFT -> new Point(-moveStep, 0);
            case UP_LEFT -> new Point(-moveStep / 2, moveStep / 2);
            case UP_RIGHT -> new Point(moveStep / 2, moveStep / 2);
            case DOWN_LEFT -> new Point(-moveStep / 2, -moveStep / 2);
            case DOWN_RIGHT -> new Point(moveStep / 2, -moveStep / 2);
        };

        mapArea.moveBy(step);
        if (!mapArea.isAreaInside(Area.getWindowArea())) {
            mapArea.moveBy(step.revert());

            return false;
        }

        super.move(direction, moveStep);
        orderTexture.getOrigin().moveBy(direction, moveStep);
        boostTexture.getOrigin().moveBy(direction, moveStep);
        for (Bot bot : bots) {
            bot.moveOrigin(direction, moveStep);
            bot.moveDestinationBy(direction, moveStep);
        }
        calculatePointerPosition();

        return true;
    }

    private MapBlock getBlock(int id, Direction direction) {
        if (id == 8) {
            id = Number.random(6, 15);
            if (Constants.ENABLE_HOUSE_RANDOM_ROTATION) {
                int randomDirection = Number.random(1, 4);
                switch (randomDirection) {
                    case 1 -> direction = Direction.UP;
                    case 2 -> direction = Direction.RIGHT;
                    case 3 -> direction = Direction.DOWN;
                    case 4 -> direction = Direction.LEFT;
                }
            }
        }

        MapBlock block = switch (id) {
            case 1 -> new RoadDirect(); // roads
            case 2 -> new RoadLock();
            case 3 -> new RoadT();
            case 4 -> new RoadX();
            case 5 -> new RoadTurn();
            case 6 -> new House1(); // houses
            case 7 -> new House2();
            case 8 -> new House3();
            case 9 -> new House4();
            case 10 -> new House5();
            case 11 -> new House6();
            case 12 -> new House7();
            case 13 -> new House8();
            case 14 -> new House9();
            case 15 -> new House10();
            case 17 -> new Cobble(); // other
            case 21 -> new Element1();
            default -> null;
        };

        if (block == null) {
            System.out.println("Error. Unknown map block id " + id);

            return null;
        }

        block.setTextureRotation(direction);

        return block;
    }

    public List<MapBlock> getBlocksByType(MapBlock.Type type) {
        List<MapBlock> houses = new ArrayList<>();

        for (String key : children.keySet()) { // collect houses
            MapBlock block = (MapBlock) children.get(key);
            if (!block.typeOf(type)) continue;
            houses.add(block);
        }

        return houses;
    }

    public CityMap setOrder() {
        List<MapBlock> houses = getBlocksByType(MapBlock.Type.House);

        int index = Number.random(0, houses.size() - 1); // random index
        houses.get(index).setHasOrdered(true);
        orderTexture.getOrigin().set(houses.get(index).getPosition());
        calculatePointerPosition();

        return this;
    }

    public CityMap setBoost() {
        List<MapBlock> roads = getBlocksByType(MapBlock.Type.Road);

        int index = Number.random(0, roads.size() - 1); // random index
        roads.get(index).setHasBoost(true);
        boostTexture.show();
        boostTexture.setOrigin(roads.get(index).getPosition());
        System.out.println("Boost pos " + boostTexture.getOrigin());

        return this;
    }

    public CityMap hideBoost() {
        boostTexture.hide();

        return this;
    }

    private void calculatePointerPosition() {
        if (pointer == null) return;

        if (Area.getWindowArea().isPointInside(orderTexture.getOrigin())) {
            pointer.hide();

            return;
        }

        if (pointer.isHidden()) {
            pointer.show();
        }

        Point point = orderTexture.getOrigin();
        int xSide = (int) (point.x / Math.abs(point.x));
        int ySide = (int) (point.y / Math.abs(point.y));
        float bounds = 0.85f;
        float floating;

        if (Math.abs(point.x) > Math.abs(point.y)) {
            floating = Math.abs(point.y) / Math.abs(point.x);

            pointer.setPosition(new Point(xSide, floating * ySide).multiply(bounds));

        } else if (Math.abs(point.x) < Math.abs(point.y)) {
            floating = Math.abs(point.x) / Math.abs(point.y);

            pointer.setPosition(new Point(floating * xSide, ySide).multiply(bounds));

        } else { // equals
            pointer.setPosition(new Point(xSide, ySide).multiply(bounds));
        }
    }

    public CityMap build() {
        super.build();

        getBlocksByType(MapBlock.Type.Road).forEach((road) -> {
            if (road instanceof RoadX) {
                ((RoadX) road).buildTrafficLight();
            }
        });

        if (!orderTexture.isHidden()) {
            Point pizzaPos = orderTexture.getPosition();
            Point newPos = new Point(pizzaPos.x, orderTextureAnimator.getNextValue(pizzaPos.y, 0.001f, 0.01f));
            orderTexture.setPosition(newPos);
//            float pizzaAngle = orderTexture.getRotation();
//            orderTexture.setRotation(orderTextureAnimator.getNextValue(pizzaAngle, 0.1f, 1));
        }

        orderTexture.build();
        boostTexture.build();

        return this;
    }
}
