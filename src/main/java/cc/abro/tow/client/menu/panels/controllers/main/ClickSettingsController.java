package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.SettingsMenuGuiPanel;

import java.util.Set;

public class ClickSettingsController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        SettingsMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(SettingsMenuGuiPanel.class);
        EventableGuiPanelElement<SettingsMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel, Set.of());
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
