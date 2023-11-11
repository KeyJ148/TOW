package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import cc.abro.orchengine.events.input.KeyReleaseEvent;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.tanks.equipment.BoxService;
import com.google.common.eventbus.Subscribe;
import lombok.RequiredArgsConstructor;

import static org.lwjgl.glfw.GLFW.*;

@GameService
@RequiredArgsConstructor
public class SingleplayerControllerService { //TODO Перенести в компонент танка, т.к. это не общий контроллер на всю игру

    private final ClientData clientData;
    private final BoxService boxService;

    @Subscribe
    public void onKeyReleaseEvent(KeyReleaseEvent keyReleaseEvent) {
        if (!isSingleplayerGame()) {
            return;
        }

        switch (keyReleaseEvent.getKey()) {
            case GLFW_KEY_T -> boxService.takeArmorBox(clientData.player);
            case GLFW_KEY_G -> boxService.takeGunBox(clientData.player);
            case GLFW_KEY_B -> boxService.takeBulletBox(clientData.player);
            case GLFW_KEY_H -> boxService.takeHealthBox(clientData.player);
            case GLFW_KEY_F -> boxService.takeHealthFullBox(clientData.player);
        }
    }

    private boolean isSingleplayerGame() {
        return clientData.peopleMax == 1;
    }
}
