package tech.abro.tow.client.map.objects.road;

import tech.abro.tow.client.map.MapObject;
import tech.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import tech.abro.tow.client.map.specification.MapObjectSpecification;

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
        //TODO: пока что не считывает размеры дорог, т.к. их нет в конфиге
    }

    //TODO: пока что полная копия scaled, потом добавить влияние на скорость
}
