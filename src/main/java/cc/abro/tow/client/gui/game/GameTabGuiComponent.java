package cc.abro.tow.client.gui.game;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.net.client.PingChecker;
import cc.abro.tow.client.ClientData;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class GameTabGuiComponent extends Component<GameObject> implements Updatable {

    private final GameTabGuiPanel gameTabGuiPanel;

    @Override
    public void update(long delta) {
        com.spinyowl.legui.component.Component frameContainer = getGameObject().getLocation()
                .getGuiLocationFrame().getGuiFrame().getContainer();

        if (Context.getService(ClientData.class).showGameTabMenu) {
            if (!frameContainer.contains(gameTabGuiPanel)) {
                frameContainer.add(gameTabGuiPanel);
                gameTabGuiPanel.changeSize();
            }
            int ping = Context.getService(PingChecker.class).getPing();

            List<GameTabGuiPanel.TabDataLine> data = Stream.concat(Stream.of(Context.getService(ClientData.class).player), Context.getService(ClientData.class).enemy.values().stream())
                    .filter(Objects::nonNull)
                    .filter(tank -> tank.getTankNicknameComponent().getNickname() != null)
                    //TODO сделать отдельное хранилище, где будут данные об игроках (не танках): ник, цвет, статистика. Мб несколько хранилищ по нику/id. Должна быть опция получать общий список (с игроком и врагами)
                    .map(tank -> new GameTabGuiPanel.TabDataLine(!tank.isAlive(),
                            tank.getTankNicknameComponent().getNickname(), tank.getColor(), 0, 0, 0, ping))//TODO получать настоящее кол-во убийств, смертей, побед
                    .sorted(Comparator.comparingInt(t -> -t.wins))
                    .collect(Collectors.toList());
            gameTabGuiPanel.fillInTable(data);
        } else {
            frameContainer.remove(gameTabGuiPanel);
        }
    }
}
