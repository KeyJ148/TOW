package tech.abro.tow.client.map.specification;

import java.util.Map;

public class MapObjectSpecification {

    private final int id;
    private final int x, y, z;
    private final String type;
    private final Map<String, Object> parameters;

    public MapObjectSpecification(int id, int x, int y, int z, String type, Map<String, Object> parameters) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.parameters = parameters;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getType() {
        return type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
