package cc.abro.tow.client.menu;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.analysis.Analyzer;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElement;
import cc.abro.orchengine.gui.GuiPanelStorage;
import cc.abro.orchengine.gui.PanelControllersStorage;
import cc.abro.orchengine.map.Background;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.DebugInfoGuiPanel;
import cc.abro.tow.client.GameLocation;
import cc.abro.tow.client.menu.panels.gui.ConnectByIPMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.gui.MainMenuLogoGuiPanel;
import cc.abro.tow.client.menu.panels.gui.ZeroRuleGuiPanel;

import java.util.stream.Collectors;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuLocation extends GameLocation {

    private static final String BACKGROUND_SPRITE_NAME = "main_menu_background";
    private static final int COUNT_LABEL = 2;

    public MenuLocation() {
        super(Manager.getService(Render.class).getWidth(), Manager.getService(Render.class).getHeight());
        background = new Background(Manager.getService(SpriteStorage.class).getSprite(BACKGROUND_SPRITE_NAME).getTexture());

        DebugInfoGuiPanel debugGuiPanel = new DebugInfoGuiPanel(COUNT_LABEL);
        GuiElement<DebugInfoGuiPanel> debugGuiElement = new GuiElement<>(debugGuiPanel) {
            @Override
            public void updateComponent(long delta) {
                super.updateComponent(delta);
                getComponent().setText(Manager.getService(Analyzer.class).getAnalysisResult());
            }
        };
        //Manager.getService(GuiElementService.class).addGuiElementToLocation(debugGuiElement,
        //        0, Manager.getService(Render.class).getHeight() - COUNT_LABEL*LABEL_HEIGHT_DEBUG, this);

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
                //(Manager.getService(Render.class).getWidth() + LABEL_LENGTH_ZERO_RULE)/2,
                //INDENT_Y + logoTexture.getHeight(), this);

        MainMenuGuiPanel menuGuiPanel = Manager.getService(GuiPanelStorage.class).getPanel(MainMenuGuiPanel.class);
        EventableGuiPanelElement<MainMenuGuiPanel> menuGuiElement = new EventableGuiPanelElement<>(menuGuiPanel,
                Manager.getService(PanelControllersStorage.class).getControllers((MainMenuGuiPanel.class)));
        Manager.getService(GuiElementService.class).addGuiElementOnLocationCenter(menuGuiElement, this);
    }
}
