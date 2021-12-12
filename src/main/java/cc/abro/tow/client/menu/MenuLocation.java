package cc.abro.tow.client.menu;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.location.map.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.FirstEntryGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.ZeroRuleGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.ZeroRuleGuiPanel;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";

    public MenuLocation(boolean settingsLoadSuccess) {
        super(Manager.getService(Render.class).getWidth(), Manager.getService(Render.class).getHeight());
        GuiService guiService = Manager.getService(GuiService.class);

        getMap().background = new Background(Manager.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        Texture logoTexture = Manager.getService(SpriteStorage.class).getSprite("logo").getTexture();
        MainMenuLogoGuiPanel mainMenuLogoGuiPanel = new MainMenuLogoGuiPanel(logoTexture);
        mainMenuLogoGuiPanel.setPosition((Manager.getService(Render.class).getWidth() - logoTexture.getWidth())/2,
                INDENT_Y);
        getGuiLocationFrame().getGuiFrame().getContainer().add(mainMenuLogoGuiPanel);

        ZeroRuleGuiPanel zeroRuleGuiPanel = new ZeroRuleGuiPanel();
        zeroRuleGuiPanel.setPosition(Manager.getService(Render.class).getWidth() - LABEL_LENGTH_ZERO_RULE - INDENT_X,
                Manager.getService(Render.class).getHeight() - LABEL_HEIGHT_ZERO_RULE - INDENT_Y/2);
        getGuiLocationFrame().getGuiFrame().getContainer().add(zeroRuleGuiPanel);


        if(settingsLoadSuccess) {
            MainMenuGuiPanel menuGuiPanel = Manager.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
            guiService.moveComponentToWindowCenter(menuGuiPanel);
            getGuiLocationFrame().getGuiFrame().getContainer().add(menuGuiPanel);
        } else {
            FirstEntryGuiPanel firstEntryGuiPanel = new FirstEntryGuiPanel();
            guiService.moveComponentToWindowCenter(firstEntryGuiPanel);
            getGuiLocationFrame().getGuiFrame().getContainer().add(firstEntryGuiPanel);
        }

        addDebugPanel(4);
        MenuEventController controller = new MenuEventController();
        controller.setComponent(new Position(0, 0, 0));
        getMap().add(controller);
    }
}
