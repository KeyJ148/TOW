package tow.game.client.map;

import java.util.*;

public class MapSpecification {

    private int width, height;
    private Map<Integer, MapObject> mapObjectById = new HashMap<>();

    public Collection<MapObject> getMapObjects(){
        return mapObjectById.values();
    }
    
    public MapObject getMapObject(int id){
        return mapObjectById.get(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
