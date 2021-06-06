package cc.abro.tow.client.menu.panels.events.creategame;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickConnectController;

public class ClickCreateGuiEvent implements GuiElementEvent {

    private final String port;
    private final int peopleMax;

    public ClickCreateGuiEvent(String port, int peopleMax) {
        this.port = port;
        this.peopleMax = peopleMax;
    }

    public String getPort() {
        return port;
    }

    public int getPeopleMax() {
        return peopleMax;
    }

    @Override
    public Class<? extends GuiElementController<ClickCreateGuiEvent>> getControllerClass() {
        return ClickCreateController.class;
    }
}
