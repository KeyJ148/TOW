package cc.abro.tow.client.map.objects.road;

import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class RoadMapObjectCreator extends RepeatedMapObjectCreator {

    @Override
    public String getType() {
        return "road";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new RoadMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getWidth(mapObjectSpecification),
                getHeight(mapObjectSpecification));
    }
}
