package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.gui.tabpanel.modes.AlignAllTabPanelButtonMode;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.menu.MenuGuiComponents;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class SettingsMenuGuiPanel extends MenuGuiPanel {

    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    final int BUTTON_INDENT_X = (SETTINGS_PANEL_WIDTH - BUTTON_WIDTH*3)/5;

    TabPanel mainPanel;

    //protected HashMap<Component, Boolean> isEdited;

    //TODO три кнопки, back to menu, save & back, apply (последняя загорается только если есть несохранённые изменения)

    public SettingsMenuGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
                SETTINGS_PANEL_HEIGHT + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2 + TAB_BUTTON_HEIGHT);

        mainPanel = new TabPanel(0, 0, SETTINGS_PANEL_WIDTH + Math.max(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_BUTTON_BORDER)*2,
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
        mainPanel.addNewTiedButtonPanel(buttons.get(1), soundSettings, soundSettings.canOut, u -> true);
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

    public Button createBackToMenuButton(SaveBackLogicInterface panel) {
        return createButton("Back to menu", BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
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
        return createButton("Save & back", SETTINGS_PANEL_WIDTH - (BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    panel.saveChanges();
                    getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                }));
    }

    public Button createSaveButton(SaveBackLogicInterface panel) {
        return createButton("Save", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> panel.saveChanges()));
    }


    public void addButtonGuiPanelWithUnblockAndBlockFrame(String text, MouseReleaseBlockingListeners panel) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel dialog = createButtonPanel(text, "OK", panel.getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(dialog);
        getFrame().getContainer().add(dialog);
    }

    public void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, MenuGuiComponents.ButtonConfiguration... buttonConfigurations) {
        Panel panel = createDialogPanel(labelText, buttonConfigurations).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    public Function<Panel, Boolean> createCanOut(SaveBackLogicInterface panel) {
        return (to -> {
            if(panel.isChanged()) {
                BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
                addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                        new ButtonConfiguration("Switch without saving", event -> {
                            panel.clearChanges();
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            mainPanel.setActivePanelFromTiedPair(mainPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Save changes & switch", event -> {
                            panel.saveChanges();
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            mainPanel.setActivePanelFromTiedPair(mainPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Don't switch", event -> {
                            panel.getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                        }));
                return false;
            } else {
                return true;
            }
        });
    }
}
