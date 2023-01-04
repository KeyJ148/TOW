package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.Slider;

import java.util.function.Function;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;

public class SoundSettingsMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    public Function<Panel, Boolean> canOut;
    private final Settings settings;
    private final Slider sliderMusicVolume;
    private final Slider sliderSoundVolume;

    private final Button saveButton;
    private final Button saveAndBackButton;

    public SoundSettingsMenuGuiPanel(MenuGuiPanel parent, TabPanel tabPanel) {
        final int MUSIC_VOLUME_LABEL_WIDTH = 100;
        final int MUSIC_VOLUME_SLIDER_WIDTH = 150;

        settings = Context.getService(SettingsService.class).getSettings();
        setSize(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        setPosition(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_PANEL_BORDER);

        add(createLabel("Music volume:", INDENT_X + 10, INDENT_Y + 15, MUSIC_VOLUME_LABEL_WIDTH, MENU_TEXT_FIELD_HEIGHT));
        sliderMusicVolume = createSlider(INDENT_X + 10 + MUSIC_VOLUME_LABEL_WIDTH + 10, INDENT_Y + 15, MUSIC_VOLUME_SLIDER_WIDTH, MENU_TEXT_FIELD_HEIGHT);
        sliderMusicVolume.setMaxValue(100);
        sliderMusicVolume.setMinValue(0);
        sliderMusicVolume.setValue((float) settings.getVolume().getMusicVolume());
        add(sliderMusicVolume);

        add(createLabel("Sound volume:", INDENT_X + 10, INDENT_Y + 15 + MENU_TEXT_FIELD_HEIGHT + 15, MUSIC_VOLUME_LABEL_WIDTH, MENU_TEXT_FIELD_HEIGHT));
        sliderSoundVolume = createSlider(INDENT_X + 10 + MUSIC_VOLUME_LABEL_WIDTH + 10, INDENT_Y + 15 + MENU_TEXT_FIELD_HEIGHT + 15, MUSIC_VOLUME_SLIDER_WIDTH, MENU_TEXT_FIELD_HEIGHT);
        sliderSoundVolume.setMaxValue(100);
        sliderSoundVolume.setMinValue(0);
        sliderSoundVolume.setValue((float) settings.getVolume().getSoundVolume());
        add(sliderSoundVolume);


        final int BUTTON_INDENT_X = (SETTINGS_PANEL_WIDTH - BUTTON_WIDTH*3)/5;

        add(createButton("Back to menu", BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
                    if(isChanged()) {
                        addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                                new ButtonConfiguration("Back to menu", getMouseReleaseListener(buttonEvent -> {
                                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new ButtonConfiguration("Save & back", getMouseReleaseListener(buttonEvent -> {
                                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    saveChanges();
                                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new ButtonConfiguration("Return editing", getUnblockAndParentDestroyReleaseListener(guiBlock)));
                    } else {
                        parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    }
                })));
        saveAndBackButton = createButton("Save & back", SETTINGS_PANEL_WIDTH - (BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    saveChanges();
                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                }));
        add(saveAndBackButton);

        saveButton = createButton("Save", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> saveChanges()));
        add(saveButton);

        canOut = (to -> {
            if(isChanged()) {
                BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
                addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                        new ButtonConfiguration("Switch without saving", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            tabPanel.setActivePanelFromTiedPair(tabPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Save changes & switch", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            saveChanges();
                            tabPanel.setActivePanelFromTiedPair(tabPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Don't switch", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                        }));
                return false;
            } else {
                return true;
            }
        });
        changeSaveButtons();
    }

    private void saveChanges() {
        try {
            Context.getService(SettingsService.class).setVolumeSettings(50, 50);
            changeSaveButtons();
        } catch (SettingsService.EmptyNicknameException e) {
            addButtonGuiPanelWithUnblockAndBlockFrame(NICKNAME_IS_EMPTY.getText());
        } catch (SettingsService.CantSaveSettingException e) {
            addButtonGuiPanelWithUnblockAndBlockFrame(CANT_SAVE_SETTINGS.getText());
        }
    }

    private void changeSaveButtons() {
        boolean changed = isChanged();
        saveButton.setFocusable(changed);
        saveAndBackButton.setFocusable(changed);
        if(changed) {
            saveButton.setStyle(createButtonStyle());
            saveAndBackButton.setStyle(createButtonStyle());
        }
        else
        {
            saveButton.setStyle(createBlockedButtonStyle());
            saveAndBackButton.setStyle(createBlockedButtonStyle());
            saveButton.setHovered(false);
            saveAndBackButton.setHovered(false);
        }
    }

    private boolean isChanged() {
        return false;
    }

    private void addButtonGuiPanelWithUnblockAndBlockFrame(String text) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, ButtonConfiguration... buttonConfigurations) {
        Panel panel = createDialogPanel(labelText, buttonConfigurations).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }
}
