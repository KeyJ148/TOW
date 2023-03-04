package cc.abro.tow.client.map;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.gameobject.location.Border;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.gui.game.GameTabGuiPanel;
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

        Border.createAll(this, BORDER_SIZE);
        for (MapObjectSpecification mapObjectSpecification : mapSpecification.getMapObjectSpecifications()) {
            MapObject mapObject = Context.getService(ClientData.class).mapObjectFactory.createMapObject(this, mapObjectSpecification);
            Context.getService(ClientData.class).mapObjects.add(mapObjectSpecification.id(), mapObject);
        }

        addDebugPanel(70);
        addTabPanel();
    }

    protected void addTabPanel() {
        GameTabGuiPanel gameTabGuiPanel = new GameTabGuiPanel(Context.getService(ClientData.class).peopleMax);
        gameTabGuiPanel.changePosition();
        TabPanelComponent tabPanelComponent = new TabPanelComponent(gameTabGuiPanel);
        GameObjectFactory.create(this, tabPanelComponent);
    }

    //TODO в отдельный класс или упростить в новой системе компонент
    public static class TabPanelComponent extends Component implements Updatable {

        private final GameTabGuiPanel gameTabGuiPanel;

        public TabPanelComponent(GameTabGuiPanel gameTabGuiPanel){
            this.gameTabGuiPanel = gameTabGuiPanel;
        }

        @Override
        public void update(long delta) {
            com.spinyowl.legui.component.Component frameContainer =
                    getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer();
            if (Context.getService(ClientData.class).showGameTabMenu) {
                if (!frameContainer.contains(gameTabGuiPanel)) {
                    frameContainer.add(gameTabGuiPanel);
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
                frameContainer.remove(gameTabGuiPanel);
            }
        }
    }
}
