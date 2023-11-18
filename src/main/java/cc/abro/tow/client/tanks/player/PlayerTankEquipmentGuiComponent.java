package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.image.Color;
import com.spinyowl.legui.component.Button;

import java.util.ArrayList;
import java.util.List;

import static cc.abro.tow.client.gui.menu.MenuGuiComponents.createBoxButton;

public class PlayerTankEquipmentGuiComponent extends Component<GameObject> {

    private final List<Button> playerCanTakeBoxButtons = new ArrayList<>();

    public PlayerTankEquipmentGuiComponent() {
        //Создание кнопок для отключения подбора снаряжения
        List<Button> buttons = new ArrayList<>();
        buttons.add(createBoxButton(Color.GREEN));
        buttons.add(createBoxButton(Color.RED));
        buttons.add(createBoxButton(Color.BLUE));
        buttons.add(createBoxButton(Color.WHITE));

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setPosition(17 * i, getRender().getHeight() - 15);

            playerCanTakeBoxButtons.add(button);
        }
    }

    @Override
    public void initialize() {
        super.initialize();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer().addAll(playerCanTakeBoxButtons);
    }

    @Override
    public void destroy() {
        super.destroy();
        getGameObject().getLocation().getGuiLocationFrame().getGuiFrame().getContainer()
                .removeAll(playerCanTakeBoxButtons);
    }

    public void changeButtonState(boolean active, int index) {
        playerCanTakeBoxButtons.get(index).getTextState().setText(active ? "" : "x");
    }
}
