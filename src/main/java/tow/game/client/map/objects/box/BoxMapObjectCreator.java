package tow.game.client.map.objects.box;

import tow.game.client.map.MapObject;
import tow.game.client.map.objects.textured.TexturedMapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class BoxMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "box";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new BoxMapObject(
                mapObjectSpecification.getId(),
                mapObjectSpecification.getX(),
                mapObjectSpecification.getY(),
                mapObjectSpecification.getZ(),
                mapObjectSpecification.getType(),
                getTexture(mapObjectSpecification),
                getDirection(mapObjectSpecification),
                getBoxType(mapObjectSpecification));
    }

    protected int getBoxType(MapObjectSpecification mapObjectSpecification){
        return (int) mapObjectSpecification.getParameters().get("boxType");
    }
}
