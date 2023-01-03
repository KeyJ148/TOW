package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.gui.tabpanel.modes.AlignAllTabPanelButtonMode;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class SettingsMenuGuiPanel extends MenuGuiPanel {

    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    //protected HashMap<Component, Boolean> isEdited;

    //TODO три кнопки, back to menu, save & back, apply (последняя загорается только если есть несохранённые изменения)

    public SettingsMenuGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                SETTINGS_PANEL_HEIGHT + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2 + TAB_BUTTON_HEIGHT);

        TabPanel mainPanel = new TabPanel(0, 0, SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2 + SETTINGS_PANEL_HEIGHT + TAB_BUTTON_HEIGHT);
        mainPanel.setMode(new AlignAllTabPanelButtonMode());
        PlayerSettingsMenuGuiPanel playerSettings = new PlayerSettingsMenuGuiPanel(this, mainPanel);
        SoundSettingsMenuGuiPanel soundSettings = new SoundSettingsMenuGuiPanel(this, mainPanel);
        VideoSettingsMenuGuiPanel videoSettings = new VideoSettingsMenuGuiPanel(this);
        List<Button> buttons = new ArrayList<>(Arrays.asList(new Button("Player"), new Button("Sound"), new Button("Video")));
        mainPanel.setYIndentVision(TAB_BUTTON_HEIGHT);
        for(Button button: buttons) {
            button.getHoveredStyle().setBackground(createHoveredButtonBackground());
            button.getSize().y = TAB_BUTTON_HEIGHT;
        }
        mainPanel.addNewTiedButtonPanel(buttons.get(0), playerSettings, playerSettings.canOut, u -> true);
        mainPanel.addNewTiedButtonPanel(buttons.get(1), soundSettings);
        mainPanel.addNewTiedButtonPanel(buttons.get(2), videoSettings);
        mainPanel.setButtonsStyle(createButtonStyle());

        for(Button button: buttons) {
            Panel crutch = new Panel();
            crutch.setSize(button.getSize().x, button.getSize().y - THICKNESS_OF_BUTTON_BORDER);
            crutch.setPosition(button.getPosition());
            crutch.setStyle(createInvisibleStyle());
            crutch.getStyle().setBorder(createButtonBorder());
            crutch.setFocusable(false);
            mainPanel.add(crutch);
            mainPanel.remove(button);
            mainPanel.add(button);
        }

        mainPanel.setStyle(createInvisibleStyle());
        mainPanel.setActiveButtonStyle(createActiveButtonStyle());
        add(mainPanel);
    }
}
