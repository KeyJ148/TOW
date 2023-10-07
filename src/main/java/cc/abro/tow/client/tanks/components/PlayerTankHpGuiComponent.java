package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.Tank;
import com.spinyowl.legui.component.Label;

import static cc.abro.tow.client.gui.menu.InterfaceStyles.BLACK_COLOR;

public class PlayerTankHpGuiComponent extends Component<Tank> implements Updatable {

    private final Label hpLabel;

    public PlayerTankHpGuiComponent() {
        hpLabel = createHpLabel();
    }

    @Override
    public void initialize() {
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
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().remove(hpLabel);
    }

    //TODO куда-нибудь в сервис интерфейса?
    private Label createHpLabel() {
        Label hpLabel = new Label();
        hpLabel.setFocusable(false);
        hpLabel.getStyle().setFontSize(30f);
        hpLabel.getStyle().setTextColor(BLACK_COLOR);
        hpLabel.setPosition(1, 10);
        return hpLabel;
    }
}
