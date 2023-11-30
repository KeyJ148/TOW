package cc.abro.tow.client.tanks.player;

import cc.abro.orchengine.events.input.KeyPressEvent;
import cc.abro.orchengine.gameobject.Component;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerTankEquipmentControllerComponent extends Component<PlayerTank> {

    @Getter
    private boolean playerCanTakeArmor = true;
    @Getter
    private boolean playerCanTakeGun = true;
    @Getter
    private boolean playerCanTakeBullet = true;
    @Getter
    private boolean playerCanTakeHeal = true;

    @Subscribe
    public void onKeyPressEvent(KeyPressEvent keyPressEvent) {
        PlayerTankEquipmentGuiComponent guiComponent = getGameObject().getPlayerTankEquipmentGuiComponent();

        switch (keyPressEvent.getKey()) {
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
