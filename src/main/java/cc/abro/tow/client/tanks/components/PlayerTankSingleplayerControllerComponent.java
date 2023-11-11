package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.events.input.KeyReleaseEvent;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.equipment.BoxService;
import cc.abro.tow.client.tanks.player.PlayerTank;
import com.google.common.eventbus.Subscribe;

import static org.lwjgl.glfw.GLFW.*;

public class PlayerTankSingleplayerControllerComponent extends Component<PlayerTank> {

    private final ClientData clientData;
    private final BoxService boxService;

    public PlayerTankSingleplayerControllerComponent() {
        this.clientData = Context.getService(ClientData.class);
        this.boxService = Context.getService(BoxService.class);
    }

    @Subscribe
    public void onKeyReleaseEvent(KeyReleaseEvent keyReleaseEvent) {
        if (!isSingleplayerGame()) {
            return;
        }

        switch (keyReleaseEvent.getKey()) {
            case GLFW_KEY_T -> boxService.takeArmorBox(getGameObject());
            case GLFW_KEY_G -> boxService.takeGunBox(getGameObject());
            case GLFW_KEY_B -> boxService.takeBulletBox(getGameObject());
            case GLFW_KEY_H -> boxService.takeHealthBox(getGameObject());
            case GLFW_KEY_F -> boxService.takeHealthFullBox(getGameObject());
        }
    }

    private boolean isSingleplayerGame() {
        return clientData.peopleMax == 1;
    }
}
