package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.menu.panels.controllers.main.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickHostController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickSettingsController;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import java.util.List;
import java.util.Set;

public class MenuLocation extends Location {

    public MenuLocation() {
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new Background(new Color(0, 150, 14), Color.GREEN);

        MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Set.of(new ClickConnectController(), new ClickHostController(), new ClickSettingsController(),
                        new ClickExitController()));
        new GuiElementService().addGuiElementOnLocationCenter(guiElement, this);
    }
}
