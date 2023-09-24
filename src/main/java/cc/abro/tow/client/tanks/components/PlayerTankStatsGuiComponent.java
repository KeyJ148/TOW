package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.Tank;
import com.spinyowl.legui.component.Label;
import com.spinyowl.legui.event.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.BLACK_COLOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class PlayerTankStatsGuiComponent extends Component<Tank> implements Updatable {

    private final List<Label> statsLabels = new ArrayList<>();
    private boolean printStats = false;

    public PlayerTankStatsGuiComponent() {
        TankStatsComponent statsComponent = getGameObject().getTankStatsComponent();

        for (int i = 0; i < statsComponent.toString().split("\n").length + 4; i++) {
            Label label = createStatsLabel();
            label.setPosition(1, 30 + i * 15);

            statsLabels.add(label);
            getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(label);
        }
    }

    @Override
    public void update(long delta) {
        List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame()
                .getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата
                if (event.getKey() == GLFW_KEY_F2) {
                    printStats = !printStats;
                }
            }
        }

        //Отрисовка статов
        if (printStats) {
            TankStatsComponent statsComponent = getGameObject().getTankStatsComponent();

            String[] array = statsComponent.toString().split("\n");
            for (int i = 0; i < array.length; i++) {
                statsLabels.get(i).getTextState().setText(array[i]);
            }
            statsLabels.get(array.length).getTextState().setText("Armor: " + getGameObject().getArmorComponent().getTitle());
            statsLabels.get(array.length + 1).getTextState().setText("Gun: " + getGameObject().getGunComponent().getTitle());
            statsLabels.get(array.length + 2).getTextState().setText("Bullet: " + ""); //TODO реализовать отрисовку названия патрона
            statsLabels.get(array.length + 3).getTextState().setText("Vampire: " +
                    Math.round(getGameObject().getTankVampireComponent().getVampire() * 100) + "%");
        } else {
            statsLabels.forEach(statsLabel -> statsLabel.getTextState().setText(""));
        }
    }

    @Override
    public void destroy() {
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().removeAll(statsLabels);
    }

    private Label createStatsLabel() { //TODO куда-нибудь в сервис интерфейса?
        Label label = new Label();
        label.setFocusable(false);
        label.getStyle().setFontSize(17f);
        label.getStyle().setTextColor(BLACK_COLOR);
        return label;
    }
}
