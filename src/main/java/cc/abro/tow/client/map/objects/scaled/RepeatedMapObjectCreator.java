package cc.abro.tow.client.map.objects.scaled;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class RepeatedMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "repeated";
    }

    @Override
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new RepeatedMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getWidth(mapObjectSpecification),
                getHeight(mapObjectSpecification));
    }

    protected int getWidth(MapObjectSpecification mapObjectSpecification) {
        return ((Number) mapObjectSpecification.parameters().get("width")).intValue();
    }

    protected int getHeight(MapObjectSpecification mapObjectSpecification) {
        return ((Number) mapObjectSpecification.parameters().get("height")).intValue();
    }

}
