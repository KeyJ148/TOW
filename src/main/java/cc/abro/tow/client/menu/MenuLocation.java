package cc.abro.tow.client.menu;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.location.map.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.orchengine.services.LeguiComponentService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ZeroRuleGuiPanel;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";

    public MenuLocation() {
        super(Manager.getService(Render.class).getWidth(), Manager.getService(Render.class).getHeight());
        getMap().background = new Background(Manager.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        Texture logoTexture = Manager.getService(SpriteStorage.class).getSprite("logo").getTexture();
        Manager.getService(LeguiComponentService.class).addComponentToLocation(new MainMenuLogoGuiPanel(logoTexture),
                (Manager.getService(Render.class).getWidth() - logoTexture.getWidth())/2, INDENT_Y, this);

        Manager.getService(LeguiComponentService.class).addComponentToLocation(new ZeroRuleGuiPanel(),
                Manager.getService(Render.class).getWidth() - LABEL_LENGTH_ZERO_RULE - INDENT_X,
                Manager.getService(Render.class).getHeight() - LABEL_HEIGHT_ZERO_RULE - INDENT_Y/2, this);

        MainMenuGuiPanel menuGuiPanel = Manager.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
        EventableGuiElement<MainMenuGuiPanel> menuGuiElement = new EventableGuiElement<>(menuGuiPanel);
        Manager.getService(GuiElementService.class).addGuiElementOnLocationCenter(menuGuiElement, this);

        createDebugPanel(4);
        MenuEventController controller = new MenuEventController();
        controller.setComponent(new Position(0, 0, 0));
        getMap().objAdd(controller);
    }
}
