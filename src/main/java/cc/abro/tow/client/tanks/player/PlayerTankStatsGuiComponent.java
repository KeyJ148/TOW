package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.events.input.KeyPressEvent;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.stats.Stats;
import cc.abro.tow.client.tanks.tank.Tank;
import com.google.common.eventbus.Subscribe;
import com.spinyowl.legui.component.Label;

import java.util.ArrayList;
import java.util.List;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.LABEL_STATS_FONT_SIZE;
import static cc.abro.tow.client.gui.menu.MenuGuiComponents.createLabel;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;

public class PlayerTankStatsGuiComponent extends Component<Tank> implements Updatable {

    private final List<Label> statsLabels = new ArrayList<>();
    private boolean printStats = false;

    public PlayerTankStatsGuiComponent() {
        for (int i = 0; i < Stats.builder().build().toString().split("\n").length + 4; i++) {

            Label label = createLabel("", 1, 30 + i * 15, 200, (int) LABEL_STATS_FONT_SIZE);
            label.getStyle().setFontSize(LABEL_STATS_FONT_SIZE);

            statsLabels.add(label);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().addAll(statsLabels);
    }

    @Subscribe
    public void onKeyPressEvent(KeyPressEvent keyPressEvent) {
        if (keyPressEvent.getKey() == GLFW_KEY_F2) {
            printStats = !printStats;
        }
    }

    @Override
    public void update(long delta) {
        //Отрисовка статов
        if (printStats) {
            String[] stats = getGameObject().getTankStatsComponent().getStats().toString().split("\n");
            for (int i = 0; i < stats.length; i++) {
                statsLabels.get(i).getTextState().setText(stats[i]);
            }
            statsLabels.get(stats.length).getTextState().setText("Armor: " + getGameObject().getArmorComponent().getTitle());
            statsLabels.get(stats.length + 1).getTextState().setText("Gun: " + getGameObject().getGunComponent().getTitle());
            statsLabels.get(stats.length + 2).getTextState().setText("Bullet: " + getGameObject().getBulletModifierComponent().getTitle());
            statsLabels.get(stats.length + 3).getTextState().setText("Vampire: " +
                    Math.round(getGameObject().getTankVampireComponent().getVampire() * 100) + "%");
        } else {
            statsLabels.forEach(statsLabel -> statsLabel.getTextState().setText(""));
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().removeAll(statsLabels);
    }

}
