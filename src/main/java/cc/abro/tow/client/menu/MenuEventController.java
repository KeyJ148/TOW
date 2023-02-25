package cc.abro.tow.client.menu;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.tow.client.ClientData;
import org.liquidengine.legui.event.KeyEvent;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class MenuEventController extends GameObject {

    public MenuEventController(Location location) {
        super(location);
    }

    @Override
    public void update(long delta) {
        if (getLocationManager().getActiveLocation().getGuiLocationFrame().getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) getEngine().stop();

        List<KeyEvent<?>> keyboardEvents = getLocationManager().getActiveLocation().getGuiLocationFrame().getKeyboard().getEventHistory().getList();
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
