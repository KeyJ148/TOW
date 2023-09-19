package cc.abro.tow.client.gui.menu;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.ClientData;
import com.spinyowl.legui.event.KeyEvent;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class MenuEventControllerComponent extends Component<GameObject> implements Updatable {

    @Override
    public void update(long delta) {
        if (getGameObject().getLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) getEngine().stop();

        List<KeyEvent<?>> keyboardEvents = getGameObject().getLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
        for (KeyEvent<?> event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата

                switch (event.getKey()) {
                    //Вывод дебаг инфы
                    case GLFW_KEY_F3:
                        Context.getService(ClientData.class).printAnalyzerInfo = !Context.getService(ClientData.class).printAnalyzerInfo;
                        break;
                }
            }
        }
    }
}
