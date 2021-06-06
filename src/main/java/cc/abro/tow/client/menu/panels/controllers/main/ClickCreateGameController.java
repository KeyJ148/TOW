package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickBackController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.gui.CreateGameMenuGuiPanel;

import java.util.List;
import java.util.Set;

public class ClickCreateGameController extends GuiElementController<GuiElementEvent> {

    @Override
    public void processEvent(GuiElementEvent event) {
        CreateGameMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(CreateGameMenuGuiPanel.class);
        EventableGuiPanelElement<CreateGameMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new ClickCreateController(), new ClickBackController()));
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
