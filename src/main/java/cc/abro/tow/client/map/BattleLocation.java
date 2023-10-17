package cc.abro.tow.client.map;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.location.Border;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.gui.game.GameTabGuiComponent;
import cc.abro.tow.client.gui.game.GameTabGuiPanel;
import cc.abro.tow.client.map.factory.MapObjectFactory;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;

import java.util.Set;

public class BattleLocation extends GameLocation {

    private final static int BORDER_SIZE = 100;

    public BattleLocation(MapSpecification mapSpecification) {
        super(mapSpecification.getWidth(), mapSpecification.getHeight());
        getCamera().setVisibleLocationOnly(true);
        getCamera().setSoundOnFollowingObject(true);

        Border.createAll(this, BORDER_SIZE);
        for (MapObjectSpecification mapObjectSpecification : mapSpecification.getMapObjectSpecifications()) {
            MapObject mapObject = Context.getService(MapObjectFactory.class).createMapObject(this, mapObjectSpecification);
            Context.getService(ClientData.class).mapObjects.add(mapObjectSpecification.id(), mapObject);
        }

        addDebugPanel(70);
        addTabPanel();
    }

    protected void addTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(Context.getService(ClientData.class).peopleMax);
        gameTabGuiPanel.changePosition();
        GameTabGuiComponent gameTabGuiComponent = new GameTabGuiComponent(gameTabGuiPanel);
        new GameObject(this, Set.of(gameTabGuiComponent));
    }
}
