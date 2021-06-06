package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import org.liquidengine.legui.component.Component;

import java.util.List;
import java.util.Set;

public class ClickConnectController extends GuiElementController {

    @Override
    public void processEvent(GuiElementEvent event) {
        ConnectMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ConnectMenuGuiPanel.class);
        EventableGuiPanelElement<ConnectMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of());
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
