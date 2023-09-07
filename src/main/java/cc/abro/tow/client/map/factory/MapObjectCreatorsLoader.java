package cc.abro.tow.client.map.factory;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.map.objects.box.BoxMapObjectCreator;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObjectCreator;
import cc.abro.tow.client.map.objects.road.RoadMapObjectCreator;
import cc.abro.tow.client.map.objects.scaled.RepeatedMapObjectCreator;
import cc.abro.tow.client.map.objects.textured.TexturedMapObjectCreator;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class MapObjectCreatorsLoader {

    private final ClientData clientData;

    public void load() { //TODO просто сканить аннотации и добавлять автоматически по аннотациям
        clientData.mapObjectFactory.registryNewCreator(new TexturedMapObjectCreator());
        clientData.mapObjectFactory.registryNewCreator(new DestroyedMapObjectCreator());
        clientData.mapObjectFactory.registryNewCreator(new BoxMapObjectCreator());
        clientData.mapObjectFactory.registryNewCreator(new RepeatedMapObjectCreator());
        clientData.mapObjectFactory.registryNewCreator(new RoadMapObjectCreator());
    }
}
