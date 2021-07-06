package cc.abro.tow.client.menu.panels.controllers;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.settings.ClickConfirmController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.gui.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ClickChangePanelController extends GuiElementController<ClickChangePanelGuiEvent> {

    @Override
    protected Class<ClickChangePanelGuiEvent> getProcessedEventClass() {
        return ClickChangePanelGuiEvent.class;
    }

    @Override
    public void processEvent(ClickChangePanelGuiEvent event) {
        EventableGuiPanel guiPanel = Global.guiPanelStorage.getPanel(event.getNextPanelClass());
        EventableGuiPanelElement<EventableGuiPanel> guiElement = new EventableGuiPanelElement<>(
                guiPanel, ClientData.panelControllersStorage.getControllers(event.getNextPanelClass()));
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
