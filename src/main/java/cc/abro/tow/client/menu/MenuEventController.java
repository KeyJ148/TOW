package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.tow.client.ClientData;
import org.liquidengine.legui.event.KeyEvent;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class MenuEventController extends GameObject {

    @Override
    public void update(long delta) {
        if (Global.location.getKeyboard().isKeyDown(GLFW_KEY_ESCAPE)) Manager.getService(Engine.class).stop();

        List<KeyEvent> keyboardEvents = Global.location.getKeyboard().getEventHistory().getList();
        for (KeyEvent event : keyboardEvents) {
            if (event.getAction() == GLFW_PRESS) {// Клавиша нажата

                switch (event.getKey()) {
                    //Вывод дебаг инфы
                    case GLFW_KEY_F3:
                        ClientData.printAnalyzerInfo = !ClientData.printAnalyzerInfo;
                        break;
                }
            }
        }
    }
}
