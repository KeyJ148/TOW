package tech.abro.tow.client.map.objects.destroyed;

import tech.abro.tow.client.map.MapObject;
import tech.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import tech.abro.tow.client.map.specification.MapObjectSpecification;

public class DestroyedMapObjectCreator extends TexturedMapObjectCreator{

    @Override
    public String getType() {
        return "destroyed";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new DestroyedMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getStability(mapObjectSpecification));
    }

    protected double getStability(MapObjectSpecification mapObjectSpecification){
        return (double) mapObjectSpecification.getParameters().get("stability");
    }
}
