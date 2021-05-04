package tow.game.client.map.factory;

import tow.game.client.ClientData;
import tow.game.client.map.objects.box.BoxMapObjectCreator;
import tow.game.client.map.objects.destroyed.DestroyedMapObjectCreator;
import tow.game.client.map.objects.road.RoadMapObjectCreator;
import tow.game.client.map.objects.scaled.RepeatedMapObjectCreator;
import tow.game.client.map.objects.textured.TexturedMapObjectCreator;

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
