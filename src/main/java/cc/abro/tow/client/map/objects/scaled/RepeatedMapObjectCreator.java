package cc.abro.tow.client.map.objects.scaled;

import cc.abro.tow.client.map.MapObject;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import cc.abro.tow.client.map.specification.MapObjectSpecification;

public class RepeatedMapObjectCreator extends TexturedMapObjectCreator {

    @Override
    public String getType() {
        return "repeated";
    }

    @Override
    public MapObject createMapObject(MapObjectSpecification mapObjectSpecification) {
        return new RepeatedMapObject(
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

    protected int getWidth(MapObjectSpecification mapObjectSpecification) {
        return (int) (double) mapObjectSpecification.getParameters().get("width");
    }

    protected int getHeight(MapObjectSpecification mapObjectSpecification) {
        return (int) (double) mapObjectSpecification.getParameters().get("height");
    }

}
