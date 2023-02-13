package cc.abro.tow.client.map.objects.box;

import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class BoxMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "box";
    }

    @Override
    public MapObject createMapObject(Location location, MapObjectSpecification mapObjectSpecification) {
        return new BoxMapObject(
                location,
                mapObjectSpecification.id(),
                mapObjectSpecification.x(),
                mapObjectSpecification.y(),
                mapObjectSpecification.z(),
                mapObjectSpecification.type(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getUnsuitable(mapObjectSpecification),
                getBoxType(mapObjectSpecification));
    }

    protected int getBoxType(MapObjectSpecification mapObjectSpecification) {
        return (int) mapObjectSpecification.parameters().get("boxType");
    }
}
