package cc.abro.tow.client.gui.menu;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.gui.menu.panels.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";

    public MenuLocation(boolean createFirstEntryPanel) {
        super(Context.getService(Render.class).getWidth(), Context.getService(Render.class).getHeight());

        GuiService guiService = getGuiService();

        TextureMenuGuiPanel backgroundGuiPanel = new TextureMenuGuiPanel(Context.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).texture(),
                Context.getService(Render.class).getWidth(), Context.getService(Render.class).getHeight());
        getGuiLocationFrame().getGuiFrame().getContainer().add(backgroundGuiPanel);

        Texture logoTexture = Context.getService(SpriteStorage.class).getSprite("logo").texture();
        TextureMenuGuiPanel logoMenuGuiPanel = new TextureMenuGuiPanel(logoTexture);
        logoMenuGuiPanel.setPosition((Context.getService(Render.class).getWidth() - logoTexture.getWidth())/2,
                InterfaceStyles.INDENT_Y);
        getGuiLocationFrame().getGuiFrame().getContainer().add(logoMenuGuiPanel);

        ZeroRuleGuiPanel zeroRuleGuiPanel = new ZeroRuleGuiPanel();
        zeroRuleGuiPanel.setPosition(Context.getService(Render.class).getWidth() - InterfaceStyles.LABEL_LENGTH_ZERO_RULE - InterfaceStyles.INDENT_X,
                Context.getService(Render.class).getHeight() - InterfaceStyles.LABEL_HEIGHT_ZERO_RULE - InterfaceStyles.INDENT_Y/2);
        getGuiLocationFrame().getGuiFrame().getContainer().add(zeroRuleGuiPanel);

        VersionGuiPanel versionGuiPanel = new VersionGuiPanel();
        versionGuiPanel.setPosition(InterfaceStyles.INDENT_X/2,
                Context.getService(Render.class).getHeight() - InterfaceStyles.LABEL_HEIGHT_VERSION - InterfaceStyles.INDENT_Y/4);
        getGuiLocationFrame().getGuiFrame().getContainer().add(versionGuiPanel);

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
        GameObject menuEventController = new GameObject(this);
        menuEventController.addComponent(new MenuEventControllerComponent());
    }
}
