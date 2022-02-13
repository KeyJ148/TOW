package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.gui.TabPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureLoader;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.SettingsStorage;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import cc.abro.tow.client.services.SettingsService;
import org.joml.Vector2f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class SettingsMenuGuiPanel extends MenuGuiPanel{
    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    protected HashMap<Component, Boolean> isEdited;

    public SettingsMenuGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                SETTINGS_PANEL_HEIGHT + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2);

        Panel playerSettingsMenuGuiPanel = new PlayerSettingsMenuGuiPanel(this);
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        TabPanel mainPanel = new TabPanel(0, 0, SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        mainPanel.addNewPanel("Player", playerSettingsMenuGuiPanel);
        mainPanel.addNewPanel("Testing", panel1);
        mainPanel.addNewPanel("Testing", panel2);
        mainPanel.setStyle(createInvisibleStyle());
        mainPanel.setButtonsStyle(createButtonStyle());
        add(mainPanel);
    }
}
