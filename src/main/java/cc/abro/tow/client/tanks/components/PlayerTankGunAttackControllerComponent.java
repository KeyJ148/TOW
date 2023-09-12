package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.stats.Stats;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class PlayerTankGunAttackControllerComponent extends Component<Tank> implements Updatable {

    @Override
    public void update(long delta) {
        Stats stats = getGameObject().getTankStatsComponent().getStats();
        GunComponent gun = getGameObject().getGunComponent();

        //Если нажата мышь и перезарядилась пушка и игрок вообще может стрелять
        if (getGameObject().getLocation().getGuiLocationFrame().getMouse().isButtonDown(GLFW_MOUSE_BUTTON_1) &&
                gun.nanoSecFromAttack <= 0 &&
                stats.attackSpeed > 0) {
            gun.attack();
        }
    }
}
