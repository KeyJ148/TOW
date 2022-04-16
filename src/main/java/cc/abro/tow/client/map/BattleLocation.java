package cc.abro.tow.client.map;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.location.objects.Border;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.GameTabGuiPanel;
import cc.abro.tow.client.map.specification.MapObjectSpecification;
import cc.abro.tow.client.map.specification.MapSpecification;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BattleLocation extends GameLocation {

    private final static int BORDER_SIZE = 100;

    public BattleLocation(MapSpecification mapSpecification) {
        super(mapSpecification.getWidth(), mapSpecification.getHeight());
        getCamera().setVisibleLocationOnly(true);
        getCamera().setSoundOnFollowingObject(true);

        Border.createAndAddAll(this, BORDER_SIZE);
        for (MapObjectSpecification mapObjectSpecification : mapSpecification.getMapObjectSpecifications()) {
            MapObject mapObject = Context.getService(ClientData.class).mapObjectFactory.createMapObject(mapObjectSpecification);
            getObjectsContainer().add(mapObject);
            Context.getService(ClientData.class).mapObjects.add(mapObjectSpecification.getId(), mapObject);
        }

        addDebugPanel(70);
        addTabPanel();
    }

    protected void addTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(Context.getService(ClientData.class).peopleMax);
        gameTabGuiPanel.changePosition();
        TabPanelComponent tabPanelComponent = new TabPanelComponent(gameTabGuiPanel);
        getObjectsContainer().add(GameObjectFactory.create(tabPanelComponent));
    }

    //TODO в отдельный класс или упростить в новой системе компонент
    public class TabPanelComponent extends Component {

        private final GameTabGuiPanel gameTabGuiPanel;

        public TabPanelComponent(GameTabGuiPanel gameTabGuiPanel){
            this.gameTabGuiPanel = gameTabGuiPanel;
        }

        @Override
        public void update(long delta) {
            if (Context.getService(ClientData.class).showGameTabMenu) {
                if (!getGuiLocationFrame().getGuiFrame().getContainer().contains(gameTabGuiPanel)) {
                    getGuiLocationFrame().getGuiFrame().getContainer().add(gameTabGuiPanel);
                    gameTabGuiPanel.changeSize();
                }
                int ping = Context.getService(PingChecker.class).getPing();
                List<GameTabGuiPanel.TabDataLine> data = Stream.concat(Stream.of(Context.getService(ClientData.class).player), Context.getService(ClientData.class).enemy.values().stream())
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
