package cc.abro.tow.client.menu.panels.controllers.connect;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ListOfServersMenuGuiPanel;

import java.util.Set;

public class ClickListOfServersController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        ListOfServersMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(ListOfServersMenuGuiPanel.class);
        EventableGuiPanelElement<ListOfServersMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of());
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
