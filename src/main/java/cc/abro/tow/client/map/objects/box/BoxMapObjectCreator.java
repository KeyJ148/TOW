package cc.abro.tow.client.map.objects.box;

import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

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

    protected int getBoxType(MapObjectSpecification mapObjectSpecification) {
        return (int) mapObjectSpecification.getParameters().get("boxType");
    }
}
