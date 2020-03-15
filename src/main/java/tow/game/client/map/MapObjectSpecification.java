package tow.game.client.map;

import java.util.Map;

public class MapObjectSpecification {

    private final int id;
    private final double x, y, direction;
    private final String type;
    private final Map<String, Object> parameters;

    public MapObjectSpecification(int id, double x, double y, double direction, String type, Map<String, Object> parameters) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
        this.parameters = parameters;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
