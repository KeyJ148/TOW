package tow.game.client.map;

import java.util.*;

public class MapSpecification {

    private final int width, height;
    private final Map<Integer, MapObjectSpecification> mapObjectById;

    public MapSpecification(int width, int height, Map<Integer, MapObjectSpecification> mapObjectById) {
        this.width = width;
        this.height = height;
        this.mapObjectById = mapObjectById;
    }

    public Collection<MapObjectSpecification> getMapObjectSpecifications(){
        return mapObjectById.values();
    }
    
    public MapObjectSpecification getMapObjectSpecification(int id){
        return mapObjectById.get(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
