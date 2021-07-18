package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Engine;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;

public class ClickExitController extends GuiElementController<ClickExitGuiEvent> {

    @Override
    protected Class<ClickExitGuiEvent> getProcessedEventClass() {
        return ClickExitGuiEvent.class;
    }

    @Override
    public void processEvent(ClickExitGuiEvent event) {
        Manager.getService(Engine.class).stop();
    }
}
