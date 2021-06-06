package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.menu.panels.controllers.main.ClickConnectController;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

import java.util.List;

public class MenuLocation extends Location {

    public MenuLocation() {
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new Background(new Color(0, 150, 14), Color.GREEN);

        MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                List.of(new ClickConnectController()));
        new GuiElementService().addGuiElementOnLocationCenter(guiElement, this);
    }
}
