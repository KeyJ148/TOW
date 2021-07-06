package cc.abro.tow.client.menu;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.map.Location;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.TextureLoader;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.controllers.ClickChangePanelController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

public class MenuLocation extends Location {

    private static final String BACKGROUND_SPRITE_NAME = "b_mass";

    public MenuLocation() {
        super(Global.engine.render.getWidth(), Global.engine.render.getHeight());
        background = new Background(Global.spriteStorage.getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        MainMenuGuiPanel guiPanel = Global.guiPanelStorage.getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                ClientData.panelControllersStorage.getControllers((MainMenuGuiPanel.class)));
        new GuiElementService().addGuiElementOnLocationCenter(guiElement, this);
    }
}
