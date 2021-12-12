package cc.abro.tow.client.map;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.QueueComponent;
import cc.abro.orchengine.location.map.Border;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.GameTabGuiPanel;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cc.abro.tow.client.menu.InterfaceStyles.TAB_LINE_SIZE_Y;
import static cc.abro.tow.client.menu.InterfaceStyles.TAB_SIZE_X;

public class BattleLocation extends GameLocation {

    public BattleLocation(MapSpecification mapSpecification) {
        super(mapSpecification.getWidth(), mapSpecification.getHeight());

        Border.createAll(this);
        for (MapObjectSpecification mapObjectSpecification : mapSpecification.getMapObjectSpecifications()) {
            MapObject mapObject = ClientData.mapObjectFactory.createMapObject(mapObjectSpecification);
            getMap().objAdd(mapObject);
            ClientData.mapObjects.add(mapObjectSpecification.getId(), mapObject);
        }

        addDebugPanel(70);
        addTabPanel();
    }

    protected void addTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(ClientData.peopleMax);
        gameTabGuiPanel.setPosition((Manager.getService(Render.class).getWidth() - TAB_SIZE_X)/2,
                (Manager.getService(Render.class).getHeight() - (TAB_LINE_SIZE_Y + 2) * (ClientData.peopleMax + 1) - 2)/2);
        getGuiLocationFrame().getGuiFrame().getContainer().add(gameTabGuiPanel);
        TabPanelComponent tabPanelComponent = new TabPanelComponent(gameTabGuiPanel);
        getMap().objAdd(GameObjectFactory.create(tabPanelComponent));
    }

    //TODO в отдельный класс или упростить в новой системе компонент
    public class TabPanelComponent extends QueueComponent {

        private final GameTabGuiPanel gameTabGuiPanel;

        public TabPanelComponent(GameTabGuiPanel gameTabGuiPanel){
            this.gameTabGuiPanel = gameTabGuiPanel;
        }
        @Override
        protected void updateComponent(long delta) {
            if (ClientData.showGameTabMenu) {
                gameTabGuiPanel.setSize(TAB_SIZE_X, (TAB_LINE_SIZE_Y + 2) * (ClientData.peopleMax + 1) - 2);
                int ping = Manager.getService(PingChecker.class).getPing();
                List<GameTabGuiPanel.TabDataLine> data = Stream.concat(Stream.of(ClientData.player), ClientData.enemy.values().stream())
                        .filter(Objects::nonNull)
                        .filter(tank -> tank.getName() != null)
                        .map(tank -> new GameTabGuiPanel.TabDataLine(!tank.alive,
                                tank.getName(), tank.color, tank.kill, tank.death, tank.win, ping))
                        .sorted(Comparator.comparingInt(t -> t.wins))
                        .collect(Collectors.toList());
                gameTabGuiPanel.fillInTable(data);
            } else {
                gameTabGuiPanel.setSize(0, 0);
            }
        }

        @Override
        protected void drawComponent() {}

        @Override
        public Class getComponentClass() {
            return TabPanelComponent.class;
        }

        @Override
        public List<Class<? extends QueueComponent>> getPreliminaryUpdateComponents() {
            return Collections.emptyList();
        }

        @Override
        public List<Class<? extends QueueComponent>> getPreliminaryDrawComponents() {
            return Collections.emptyList();
        }
    }
}
