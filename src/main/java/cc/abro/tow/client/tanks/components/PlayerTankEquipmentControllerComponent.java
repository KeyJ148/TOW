package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.player.PlayerTank;
import com.spinyowl.legui.event.KeyEvent;
import lombok.Getter;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerTankEquipmentControllerComponent extends Component<PlayerTank> implements Updatable {

    @Getter
    private boolean playerCanTakeArmor = true;
    @Getter
    private boolean playerCanTakeGun = true;
    @Getter
    private boolean playerCanTakeBullet = true;
    @Getter
    private boolean playerCanTakeHeal = true;

    @Override
    public void update(long delta) {
        PlayerTankEquipmentGuiComponent guiComponent = getGameObject().getPlayerTankEquipmentGuiComponent();

        List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame()
                .getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата
                switch (event.getKey()) {
                    //Клавиши запрета и разрешения на подбор ящиков
                    case GLFW_KEY_1 -> {
                        playerCanTakeArmor = !playerCanTakeArmor;
                        guiComponent.changeButtonState(playerCanTakeArmor, 0);
                    }
                    case GLFW_KEY_2 -> {
                        playerCanTakeGun = !playerCanTakeGun;
                        guiComponent.changeButtonState(playerCanTakeGun, 1);
                    }
                    case GLFW_KEY_3 -> {
                        playerCanTakeBullet = !playerCanTakeBullet;
                        guiComponent.changeButtonState(playerCanTakeBullet, 2);
                    }
                    case GLFW_KEY_4 -> {
                        playerCanTakeHeal = !playerCanTakeHeal;
                        guiComponent.changeButtonState(playerCanTakeHeal, 3);
                    }
                }
            }
        }
    }
}
