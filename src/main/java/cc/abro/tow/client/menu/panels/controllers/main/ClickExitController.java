package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Loader;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;

public class ClickExitController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        Loader.exit();
    }
}
