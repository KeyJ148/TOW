package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.connect.ClickBackController;
import cc.abro.tow.client.menu.panels.controllers.connect.ClickConnectByIpController;
import cc.abro.tow.client.menu.panels.controllers.connect.ClickListOfServersController;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;

import java.util.Set;

public class ClickConnectController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        ConnectMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectMenuGuiPanel.class);
        EventableGuiPanelElement<ConnectMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new ClickConnectByIpController(), new ClickListOfServersController(),
                        new ClickBackController()));
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
