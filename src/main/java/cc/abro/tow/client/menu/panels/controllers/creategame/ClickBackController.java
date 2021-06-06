package cc.abro.tow.client.menu.panels.controllers.creategame;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.main.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickCreateGameController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickSettingsController;
import cc.abro.tow.client.menu.panels.gui.ConnectMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import java.util.Set;

public class ClickBackController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new ClickConnectController(), new ClickCreateGameController(), new ClickSettingsController(),
                        new ClickExitController()));
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
