package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.gui.tabpanel.modes.AlignAllTabPanelButtonMode;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.gui.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SettingsMenuGuiPanel extends MenuGuiPanel {

    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    final int BUTTON_INDENT_X = (InterfaceStyles.SETTINGS_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH*3)/5;

    TabPanel mainPanel;

    //protected HashMap<Component, Boolean> isEdited;

    public SettingsMenuGuiPanel() {
        setStyle(InterfaceStyles.createInvisibleStyle());
        setSize(InterfaceStyles.SETTINGS_PANEL_WIDTH + Math.max(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_BUTTON_BORDER)*2,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT + Math.max(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_BUTTON_BORDER)*2 + InterfaceStyles.TAB_BUTTON_HEIGHT);

        mainPanel = new TabPanel(0, 0, InterfaceStyles.SETTINGS_PANEL_WIDTH + Math.max(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_BUTTON_BORDER)*2,
                + Math.max(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_BUTTON_BORDER)*2 + InterfaceStyles.SETTINGS_PANEL_HEIGHT + InterfaceStyles.TAB_BUTTON_HEIGHT);
        mainPanel.setMode(new AlignAllTabPanelButtonMode());
        PlayerSettingsMenuGuiPanel playerSettings = new PlayerSettingsMenuGuiPanel(this, mainPanel);
        SoundSettingsMenuGuiPanel soundSettings = new SoundSettingsMenuGuiPanel(this, mainPanel);
        VideoSettingsMenuGuiPanel videoSettings = new VideoSettingsMenuGuiPanel(this);
        List<Button> buttons = new ArrayList<>(Arrays.asList(new Button("Player"), new Button("Sound"), new Button("Video")));
        mainPanel.setYIndentVision(InterfaceStyles.TAB_BUTTON_HEIGHT);
        for(Button button: buttons) {
            button.getHoveredStyle().setBackground(InterfaceStyles.createHoveredButtonBackground());
            button.getSize().y = InterfaceStyles.TAB_BUTTON_HEIGHT;
        }
        mainPanel.addNewTiedButtonPanel(buttons.get(0), playerSettings, playerSettings.canOut, u -> true);
        mainPanel.addNewTiedButtonPanel(buttons.get(1), soundSettings, soundSettings.canOut, u -> true);
        mainPanel.addNewTiedButtonPanel(buttons.get(2), videoSettings);
        mainPanel.setButtonsStyle(InterfaceStyles.createButtonStyle());

        for(Button button: buttons) {
            Panel crutch = new Panel();
            crutch.setSize(button.getSize().x, button.getSize().y - InterfaceStyles.THICKNESS_OF_BUTTON_BORDER);
            crutch.setPosition(button.getPosition());
            crutch.setStyle(InterfaceStyles.createInvisibleStyle());
            crutch.getStyle().setBorder(InterfaceStyles.createButtonBorder());
            crutch.setFocusable(false);
            mainPanel.add(crutch);
            mainPanel.remove(button);
            mainPanel.add(button);
        }

        mainPanel.setStyle(InterfaceStyles.createInvisibleStyle());
        mainPanel.setActiveButtonStyle(InterfaceStyles.createActiveButtonStyle());
        add(mainPanel);
    }

    public Button createBackToMenuButton(SaveBackLogicInterface panel) {
        return MenuGuiComponents.createButton("Back to menu", BUTTON_INDENT_X,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
                    if(panel.isChanged()) {
                        addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                                new MenuGuiComponents.ButtonConfiguration("Back to menu", getMouseReleaseListener(buttonEvent -> {
                                    panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new MenuGuiComponents.ButtonConfiguration("Save & back", getMouseReleaseListener(buttonEvent -> {
                                    panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    panel.saveChanges();
                                    getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new MenuGuiComponents.ButtonConfiguration("Return editing", panel.getUnblockAndParentDestroyReleaseListener(guiBlock)));
                    } else {
                        getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    }
                }));
    }

    public Button createSaveAndBackButton(SaveBackLogicInterface panel) {
        return MenuGuiComponents.createButton("Save & back", InterfaceStyles.SETTINGS_PANEL_WIDTH - (InterfaceStyles.BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    panel.saveChanges();
                    getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                }));
    }

    public Button createSaveButton(SaveBackLogicInterface panel) {
        return MenuGuiComponents.createButton("Save", InterfaceStyles.SETTINGS_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH - BUTTON_INDENT_X,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(event -> panel.saveChanges()));
    }


    public void addButtonGuiPanelWithUnblockAndBlockFrame(String text, MouseReleaseBlockingListeners panel) {
        BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
        Panel dialog = MenuGuiComponents.createButtonPanel(text, "OK", panel.getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(dialog);
        getFrame().getContainer().add(dialog);
    }

    public void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, MenuGuiComponents.ButtonConfiguration... buttonConfigurations) {
        Panel panel = MenuGuiComponents.createDialogPanel(labelText, buttonConfigurations).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    public Function<Panel, Boolean> createCanOut(SaveBackLogicInterface panel) {
        return (to -> {
            if(panel.isChanged()) {
                BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
                addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                        new MenuGuiComponents.ButtonConfiguration("Switch without saving", event -> {
                            panel.clearChanges();
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            mainPanel.setActivePanelFromTiedPair(mainPanel.getTideButtonPanel(to));
                        }),
                        new MenuGuiComponents.ButtonConfiguration("Save changes & switch", event -> {
                            panel.saveChanges();
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            mainPanel.setActivePanelFromTiedPair(mainPanel.getTideButtonPanel(to));
                        }),
                        new MenuGuiComponents.ButtonConfiguration("Don't switch", event -> {
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                        }));
                return false;
            } else {
                return true;
            }
        });
    }
}
