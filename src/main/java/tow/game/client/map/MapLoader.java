package tow.game.client.map;

import java.util.Map;

public class MapLoader {

    public static MapSpecification getMap(String path){

    }

    public static class MapObjectContainer{
        public int id;
        public double x, y, direction;
        public String type;
        public Map<String, Object> parameters;
    }
}
