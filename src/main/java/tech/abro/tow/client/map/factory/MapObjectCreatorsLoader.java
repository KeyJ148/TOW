package tech.abro.tow.client.map.factory;

import tech.abro.tow.client.ClientData;
import tech.abro.tow.client.map.objects.box.BoxMapObjectCreator;
import tech.abro.tow.client.map.objects.destroyed.DestroyedMapObjectCreator;
import tech.abro.tow.client.map.objects.road.RoadMapObjectCreator;
import tech.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import tech.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;

public class MapObjectCreatorsLoader {

    //TODO: переделать на считывание из конфигов JSON (несколько конфигов, конфиг может быть встроен в архив карту)
    public static void load(){
        ClientData.mapObjectFactory.registryNewCreator(new TexturedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new DestroyedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new BoxMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new RepeatedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new RoadMapObjectCreator());
    }
}
