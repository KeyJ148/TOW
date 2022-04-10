package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.gui.tabpanel.modes.AlignAllTabPanelButtonMode;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;

import java.util.HashMap;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class SettingsMenuGuiPanel extends MenuGuiPanel{
    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    protected HashMap<Component, Boolean> isEdited;

    public SettingsMenuGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                SETTINGS_PANEL_HEIGHT + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2 + 60);

        Panel playerSettingsMenuGuiPanel = new PlayerSettingsMenuGuiPanel(this);
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        TabPanel mainPanel = new TabPanel(0, 0, SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2 + SETTINGS_PANEL_HEIGHT + 60);
        mainPanel.setMode(new AlignAllTabPanelButtonMode());
        mainPanel.addNewTiedButtonPanel(new Button("Player"), playerSettingsMenuGuiPanel);
        mainPanel.addNewTiedButtonPanel(new Button("Testing"), panel1);
        mainPanel.addNewTiedButtonPanel(new Button("Testing"), playerSettingsMenuGuiPanel);
        mainPanel.setStyle(createInvisibleStyle());
        mainPanel.setButtonsStyle(createButtonStyle());
        add(mainPanel);
    }
}
