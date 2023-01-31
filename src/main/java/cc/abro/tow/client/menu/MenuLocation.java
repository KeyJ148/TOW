package cc.abro.tow.client.menu;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.location.objects.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.FirstEntryGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.ZeroRuleGuiPanel;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";

    public MenuLocation(boolean createFirstEntryPanel) {
        super(Context.getService(Render.class).getWidth(), Context.getService(Render.class).getHeight());

        GuiService guiService = Context.getService(GuiService.class);

        setBackground(new Background(Context.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).texture()));

        Texture logoTexture = Context.getService(SpriteStorage.class).getSprite("logo").texture();
        MainMenuLogoGuiPanel mainMenuLogoGuiPanel = new MainMenuLogoGuiPanel(logoTexture);
        mainMenuLogoGuiPanel.setPosition((Context.getService(Render.class).getWidth() - logoTexture.getWidth())/2,
                INDENT_Y);
        getGuiLocationFrame().getGuiFrame().getContainer().add(mainMenuLogoGuiPanel);

        ZeroRuleGuiPanel zeroRuleGuiPanel = new ZeroRuleGuiPanel();
        zeroRuleGuiPanel.setPosition(Context.getService(Render.class).getWidth() - LABEL_LENGTH_ZERO_RULE - INDENT_X,
                Context.getService(Render.class).getHeight() - LABEL_HEIGHT_ZERO_RULE - INDENT_Y/2);
        getGuiLocationFrame().getGuiFrame().getContainer().add(zeroRuleGuiPanel);


        if (!createFirstEntryPanel) {
            MainMenuGuiPanel menuGuiPanel = Context.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
            guiService.moveComponentToWindowCenter(menuGuiPanel);
            getGuiLocationFrame().getGuiFrame().getContainer().add(menuGuiPanel);
        } else {
            FirstEntryGuiPanel firstEntryGuiPanel = new FirstEntryGuiPanel();
            guiService.moveComponentToWindowCenter(firstEntryGuiPanel);
            getGuiLocationFrame().getGuiFrame().getContainer().add(firstEntryGuiPanel);
        }

        addDebugPanel(4);
        MenuEventController controller = new MenuEventController(this);
        controller.addComponent(new Position(0, 0, 0));
    }
}
