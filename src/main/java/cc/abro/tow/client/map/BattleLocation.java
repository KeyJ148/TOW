package cc.abro.tow.client.map;

import cc.abro.orchengine.map.Border;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;

public class BattleLocation extends GameLocation {

    public BattleLocation(MapSpecification mapSpecification) {
        super(mapSpecification.getWidth(), mapSpecification.getHeight());

        Border.createAll(this);
        for (MapObjectSpecification mapObjectSpecification : mapSpecification.getMapObjectSpecifications()) {
            MapObject mapObject = ClientData.mapObjectFactory.createMapObject(mapObjectSpecification);
            objAdd(mapObject);
            ClientData.mapObjects.add(mapObjectSpecification.getId(), mapObject);
        }

        createDebugPanel(70);
    }
}
