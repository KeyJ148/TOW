package tow.game.client.map.objects.scaled;

import tow.game.client.map.MapObject;
import tow.game.client.map.objects.textured.TexturedMapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class ScaledMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "scaled";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new ScaledMapObject(
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

    protected int getWidth(MapObjectSpecification mapObjectSpecification){
        return (int) (double) mapObjectSpecification.getParameters().get("width");
    }

    protected int getHeight(MapObjectSpecification mapObjectSpecification){
        return (int) (double) mapObjectSpecification.getParameters().get("height");
    }

}
