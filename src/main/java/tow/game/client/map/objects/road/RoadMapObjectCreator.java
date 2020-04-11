package tow.game.client.map.objects.road;

import tow.game.client.map.MapObject;
import tow.game.client.map.objects.scaled.ScaledMapObjectCreator;
import tow.game.client.map.specification.MapObjectSpecification;

public class RoadMapObjectCreator extends ScaledMapObjectCreator {

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
                0,
                0);
        //TODO: пока что не считывает размеры дорог, т.к. их нет в конфиге
    }

    //TODO: пока что полная копия scaled, потом добавить влияние на скорость
}
