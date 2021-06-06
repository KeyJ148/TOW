package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.HostingListener;
import cc.abro.tow.client.menu.panels.events.creategame.ClickCreateGuiEvent;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;

public class ClickCreateController extends GuiElementController<ClickCreateGuiEvent> {

    @Override
    public void processEvent(ClickCreateGuiEvent event) {
        new HostingListener(error -> new PrintErrorGuiPanel(error.getText(), getGuiElement().getComponent()))
                .host(event.getPort(), event.getPeopleMax());
    }
}
