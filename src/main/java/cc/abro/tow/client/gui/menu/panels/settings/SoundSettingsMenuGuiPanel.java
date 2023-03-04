package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.MenuGuiPanel;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.Slider;
import com.spinyowl.legui.event.MouseDragEvent;

import java.util.function.Function;

public class SoundSettingsMenuGuiPanel extends MenuGuiPanel implements SaveBackLogicInterface {


    SettingsMenuGuiPanel parent;
    private final Settings settings;
    private final Slider sliderMusicVolume;
    private final Slider sliderSoundVolume;

    public Function<Panel, Boolean> canOut;
    private final Button saveButton;
    private final Button saveAndBackButton;


    public SoundSettingsMenuGuiPanel(SettingsMenuGuiPanel parent, TabPanel tabPanel) {
        this.parent = parent;
        final int MUSIC_VOLUME_LABEL_WIDTH = 100;
        final int MUSIC_VOLUME_SLIDER_WIDTH = 150;

        settings = Context.getService(SettingsService.class).getSettings();
        setSize(InterfaceStyles.SETTINGS_PANEL_WIDTH, InterfaceStyles.SETTINGS_PANEL_HEIGHT);
        setPosition(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_PANEL_BORDER);

        add(MenuGuiComponents.createLabel("Music volume:", InterfaceStyles.INDENT_X + 10, InterfaceStyles.INDENT_Y + 15, MUSIC_VOLUME_LABEL_WIDTH, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        sliderMusicVolume = MenuGuiComponents.createSlider(InterfaceStyles.INDENT_X + 10 + MUSIC_VOLUME_LABEL_WIDTH + 10, InterfaceStyles.INDENT_Y + 15, MUSIC_VOLUME_SLIDER_WIDTH, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT);
        sliderMusicVolume.setMaxValue(100);
        sliderMusicVolume.setMinValue(0);
        sliderMusicVolume.setValue((float) settings.getVolume().getMusicVolume());
        add(sliderMusicVolume);

        add(MenuGuiComponents.createLabel("Sound volume:", InterfaceStyles.INDENT_X + 10, InterfaceStyles.INDENT_Y + 15 + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT + 15, MUSIC_VOLUME_LABEL_WIDTH, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        sliderSoundVolume = MenuGuiComponents.createSlider(InterfaceStyles.INDENT_X + 10 + MUSIC_VOLUME_LABEL_WIDTH + 10, InterfaceStyles.INDENT_Y + 15 + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT + 15, MUSIC_VOLUME_SLIDER_WIDTH, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT);
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
            Context.getService(SettingsService.class).setVolumeSettings(
                    Math.round(sliderMusicVolume.getValue()),
                    Math.round(sliderSoundVolume.getValue())
            );
            Context.getService(AudioService.class).setVolume(Math.round(sliderSoundVolume.getValue()));
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
            saveButton.setStyle(InterfaceStyles.createButtonStyle());
            saveAndBackButton.setStyle(InterfaceStyles.createButtonStyle());
        }
        else
        {
            saveButton.setStyle(InterfaceStyles.createBlockedButtonStyle());
            saveAndBackButton.setStyle(InterfaceStyles.createBlockedButtonStyle());
            saveButton.setHovered(false);
            saveAndBackButton.setHovered(false);
        }
    }

    public boolean isChanged() {
        return (sliderSoundVolume.getValue() != settings.getVolume().getSoundVolume()) ||
                (sliderMusicVolume.getValue() != settings.getVolume().getMusicVolume());
    }
}
