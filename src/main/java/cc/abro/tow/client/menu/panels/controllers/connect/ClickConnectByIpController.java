package cc.abro.tow.client.menu.panels.controllers.connect;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickBackController;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickCreateGameController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickSettingsController;
import cc.abro.tow.client.menu.panels.events.connectbyip.ClickConnectGuiEvent;
import cc.abro.tow.client.menu.panels.gui.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import java.util.Set;

public class ClickConnectByIpController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        ConnectByIPMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectByIPMenuGuiPanel.class);
        EventableGuiPanelElement<ConnectByIPMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new ClickConnectController(), new ClickBackController()));
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
