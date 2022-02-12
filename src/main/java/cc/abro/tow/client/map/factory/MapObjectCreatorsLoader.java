package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.context.Context;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.box.BoxMapObjectCreator;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObjectCreator;
import cc.abro.tow.client.map.objects.road.RoadMapObjectCreator;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;

public class MapObjectCreatorsLoader {

    public static void load() {
        Context.getService(ClientData.class).mapObjectFactory.registryNewCreator(new TexturedMapObjectCreator());
        Context.getService(ClientData.class).mapObjectFactory.registryNewCreator(new DestroyedMapObjectCreator());
        Context.getService(ClientData.class).mapObjectFactory.registryNewCreator(new BoxMapObjectCreator());
        Context.getService(ClientData.class).mapObjectFactory.registryNewCreator(new RepeatedMapObjectCreator());
        Context.getService(ClientData.class).mapObjectFactory.registryNewCreator(new RoadMapObjectCreator());
    }
}
