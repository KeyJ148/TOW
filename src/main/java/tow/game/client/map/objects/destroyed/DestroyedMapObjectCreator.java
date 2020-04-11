package tow.game.client.map.objects.destroyed;

import tow.game.client.map.MapObject;
import tow.game.client.map.objects.textured.TexturedMapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

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
