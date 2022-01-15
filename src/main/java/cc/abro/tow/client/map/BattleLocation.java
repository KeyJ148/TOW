package cc.abro.tow.client.map;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.location.map.Border;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.GameTabGuiPanel;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;
import cc.abro.tow.client.tanks.enemy.Enemy;
import org.liquidengine.legui.component.Label;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
            getMap().add(mapObject);
            ClientData.mapObjects.add(mapObjectSpecification.getId(), mapObject);
        }

        addDebugPanel(70);
        addTabPanel();
    }

    protected void addTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(ClientData.peopleMax);
        gameTabGuiPanel.changePosition();
        TabPanelComponent tabPanelComponent = new TabPanelComponent(gameTabGuiPanel);
        getMap().add(GameObjectFactory.create(tabPanelComponent));
    }

    //TODO в отдельный класс или упростить в новой системе компонент
    public class TabPanelComponent extends Component {

        private final GameTabGuiPanel gameTabGuiPanel;

        public TabPanelComponent(GameTabGuiPanel gameTabGuiPanel){
            this.gameTabGuiPanel = gameTabGuiPanel;
        }

        @Override
        public void update(long delta) {
            if (ClientData.showGameTabMenu) {
                if (!getGuiLocationFrame().getGuiFrame().getContainer().contains(gameTabGuiPanel)) {
                    getGuiLocationFrame().getGuiFrame().getContainer().add(gameTabGuiPanel);
                    gameTabGuiPanel.changeSize();
                }
                int ping = Manager.getService(PingChecker.class).getPing();
                List<GameTabGuiPanel.TabDataLine> data = Stream.concat(Stream.of(ClientData.player), ClientData.enemy.values().stream())
                        .filter(Objects::nonNull)
                        .filter(tank -> tank.getName() != null)
                        .map(tank -> new GameTabGuiPanel.TabDataLine(!tank.alive,
                                tank.getName(), tank.color, tank.kill, tank.death, tank.win, ping))
                        .sorted(Comparator.comparingInt(t -> -t.wins))
                        .collect(Collectors.toList());
                gameTabGuiPanel.fillInTable(data);
            } else {
                getGuiLocationFrame().getGuiFrame().getContainer().remove(gameTabGuiPanel);
            }
        }

        @Override
        public void draw() {}

        @Override
        public Class getComponentClass() {
            return TabPanelComponent.class;
        }
    }
}
