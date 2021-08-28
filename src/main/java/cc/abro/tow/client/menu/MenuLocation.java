package cc.abro.tow.client.menu;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.gui.PanelControllersStorage;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "b_mass";

    public MenuLocation() {
        super(Manager.getService(Render.class).getWidth(), Manager.getService(Render.class).getHeight());
        background = new Background(Manager.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        MainMenuGuiPanel guiPanel = Manager.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> guiElement = new EventableGuiPanelElement<>(guiPanel,
                Manager.getService(PanelControllersStorage.class).getControllers((MainMenuGuiPanel.class)));
        Manager.getService(GuiElementService.class).addGuiElementOnLocationCenter(guiElement, this);
    }
}
