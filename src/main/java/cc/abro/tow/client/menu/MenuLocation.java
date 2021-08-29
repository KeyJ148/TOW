package cc.abro.tow.client.menu;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.gui.PanelControllersStorage;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ZeroRuleGuiPanel;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";

    public MenuLocation() {
        super(Manager.getService(Render.class).getWidth(), Manager.getService(Render.class).getHeight());
        background = new Background(Manager.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        Texture logoTexture = Manager.getService(SpriteStorage.class).getSprite("logo").getTexture();
        MainMenuLogoGuiPanel logoGuiPanel = new MainMenuLogoGuiPanel(logoTexture);
        GuiElement<MainMenuLogoGuiPanel> logoGuiElement = new GuiElement<>(logoGuiPanel);
        Manager.getService(GuiElementService.class).addGuiElementToLocation(logoGuiElement,
                (Manager.getService(Render.class).getWidth() - logoTexture.getWidth())/2, INDENT_Y, this);

        ZeroRuleGuiPanel zeroRuleGuiPanel = new ZeroRuleGuiPanel();
        GuiElement<ZeroRuleGuiPanel> zeroGuiElement = new GuiElement<>(zeroRuleGuiPanel);
        Manager.getService(GuiElementService.class).addGuiElementToLocation(zeroGuiElement,
                Manager.getService(Render.class).getWidth() - LABEL_LENGTH_ZERO_RULE - INDENT_X,
                Manager.getService(Render.class).getHeight() - LABEL_HEIGHT_ZERO_RULE - INDENT_Y/2, this);

        MainMenuGuiPanel menuGuiPanel = Manager.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> menuGuiElement = new EventableGuiPanelElement<>(menuGuiPanel,
                Manager.getService(PanelControllersStorage.class).getControllers((MainMenuGuiPanel.class)));
        Manager.getService(GuiElementService.class).addGuiElementOnLocationCenter(menuGuiElement, this);

        createDebugPanel(4);
        MenuEventController controller = new MenuEventController();
        controller.setComponent(new Position(0, 0, 0));
        objAdd(controller);
    }
}
