package cc.abro.tow.client.map;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.map.Border;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.orchengine.net.server.GameServer;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.DebugInfoGuiPanel;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.GameTabGuiPanel;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

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
        createTabPanel();
    }

    protected GuiElement<GameTabGuiPanel> createTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(ClientData.peopleMax);
        GuiElement<GameTabGuiPanel> gameTabGuiElement = new GuiElement<>(gameTabGuiPanel){
            @Override
            public void updateComponent(long delta) {
                super.updateComponent(delta);
                if (ClientData.showGameTabMenu) {
                    getComponent().setSize(TAB_SIZE_X, (TAB_LINE_SIZE_Y + 2) * (GameServer.peopleNow - GameServer.disconnect + 1) - 2);
                    int ping = Manager.getService(PingChecker.class).getPing();
                    List<GameTabGuiPanel.TabDataLine> data = Stream.concat(Stream.of(ClientData.player), ClientData.enemy.values().stream())
                            .filter(Objects::nonNull)
                            .filter(tank -> tank.getName() != null)
                            .map(tank -> new GameTabGuiPanel.TabDataLine(!tank.alive,
                                    tank.getName(), tank.color, tank.kill, tank.death, tank.win, ping))
                            .collect(Collectors.toList());
                    getComponent().fillInTable(data);
                } else {
                    getComponent().setSize(0, 0);
                }
            }
        };
        Manager.getService(GuiElementService.class).addGuiElementToLocation(gameTabGuiElement,
                (Manager.getService(Render.class).getWidth() - TAB_SIZE_X)/2, (Manager.getService(Render.class).getHeight() - TAB_LINE_SIZE_Y)/2, this);
        return gameTabGuiElement;
    }
}
