package cc.abro.tow.client.map.factory;

import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.box.BoxMapObjectCreator;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObjectCreator;
import cc.abro.tow.client.map.objects.road.RoadMapObjectCreator;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;

public class MapObjectCreatorsLoader {

    public static void load() {
        ClientData.mapObjectFactory.registryNewCreator(new TexturedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new DestroyedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new BoxMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new RepeatedMapObjectCreator());
        ClientData.mapObjectFactory.registryNewCreator(new RoadMapObjectCreator());
    }
}
