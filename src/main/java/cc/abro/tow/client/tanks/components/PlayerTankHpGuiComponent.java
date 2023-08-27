package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.Tank;
import com.spinyowl.legui.component.Label;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.LABEL_HP_FONT_SIZE;
import static cc.abro.tow.client.gui.menu.MenuGuiComponents.createLabel;

public class PlayerTankHpGuiComponent extends Component<Tank> implements Updatable {

    private final Label hpLabel;

    public PlayerTankHpGuiComponent() {
        hpLabel = createLabel("", 1, 1, 100, (int) LABEL_HP_FONT_SIZE);
        hpLabel.getStyle().setFontSize(LABEL_HP_FONT_SIZE);
    }

    @Override
    public void initialize() {
        super.initialize();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().add(hpLabel);
    }

    @Override
    public void update(long delta) {
        TankStatsComponent statsComponent = getGameObject().getTankStatsComponent();
        //Отрисовка HP
        hpLabel.getTextState().setText("HP: " + Math.round(statsComponent.getCurrentHp())
                + "/" + Math.round(statsComponent.getStats().getHpMax()));
    }

    @Override
    public void destroy() {
        super.destroy();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().remove(hpLabel);
    }

}
