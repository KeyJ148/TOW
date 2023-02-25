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
import org.liquidengine.legui.event.MouseDragEvent;

import java.util.function.Function;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;

public class SoundSettingsMenuGuiPanel extends MenuGuiPanel implements SaveBackLogicInterface {


    SettingsMenuGuiPanel parent;
    public Function<Panel, Boolean> canOut;
    private final Settings settings;
    private final Slider sliderMusicVolume;
    private final Slider sliderSoundVolume;

    private final Button saveButton;
    private final Button saveAndBackButton;


    public SoundSettingsMenuGuiPanel(SettingsMenuGuiPanel parent, TabPanel tabPanel) {
        this.parent = parent;
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

        add(parent.createBackToMenuButton(this));
        canOut = parent.createCanOut(this);

        saveAndBackButton = parent.createSaveAndBackButton(this);
        add(saveAndBackButton);

        saveButton = parent.createSaveButton(this);
        add(saveButton);

        sliderSoundVolume.getListenerMap().addListener(MouseDragEvent.class, event -> {
            changeSaveButtons();
        });

        sliderMusicVolume.getListenerMap().addListener(MouseDragEvent.class, event -> {
            changeSaveButtons();
        });

        changeSaveButtons();
    }

    public void saveChanges() {
            Context.getService(SettingsService.class).setVolumeSettings(sliderMusicVolume.getValue(), sliderSoundVolume.getValue());
            changeSaveButtons();
    }

    public void clearChanges() {
        sliderSoundVolume.setValue((float) Context.getService(SettingsService.class).getSettings().getVolume().getSoundVolume());
        sliderMusicVolume.setValue((float) Context.getService(SettingsService.class).getSettings().getVolume().getMusicVolume());
    }

    public void changeSaveButtons() {
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

    public boolean isChanged() {
        return (sliderSoundVolume.getValue() != settings.getVolume().getSoundVolume()) ||
                (sliderMusicVolume.getValue() != settings.getVolume().getMusicVolume());
    }
}
