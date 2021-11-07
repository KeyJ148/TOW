package cc.abro.tow.client.menu.panels.controllers.settings;

import cc.abro.orchengine.resources.settings.SettingsLoader;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.SettingsStorage;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static cc.abro.tow.client.menu.InterfaceStyles.BLOCKING_BUTTON_ELEMENT_HEIGHT;
import static cc.abro.tow.client.menu.InterfaceStyles.BLOCKING_BUTTON_ELEMENT_WIDTH;

@Log4j2
public class ClickConfirmController extends MenuClickController<ClickConfirmGuiEvent> {

    @Override
    protected Class<ClickConfirmGuiEvent> getProcessedEventClass() {
        return ClickConfirmGuiEvent.class;
    }

    @Override
    public void processEvent(ClickConfirmGuiEvent event) {
        if (event.getNickname().isEmpty()) {
            createButtonBlockingPanel(Error.NICKNAME_IS_EMPTY.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
            return;
        }

        SettingsStorage.PROFILE.NICKNAME = event.getNickname();
        SettingsStorage.PROFILE.COLOR = event.getTankColor().getRGBArray();
        try {
            SettingsLoader.saveExternalSettings(SettingsStorage.PROFILE);
        } catch (IOException e) {
            log.warn("Settings can't be saved", e);
            createButtonBlockingPanel(Error.CANT_SAVE_SETTINGS.getText(), BLOCKING_BUTTON_ELEMENT_WIDTH, BLOCKING_BUTTON_ELEMENT_HEIGHT);
            return;
        }

        ClientData.name = event.getNickname();
        ClientData.color = event.getTankColor();
    }

    public enum Error {
        NICKNAME_IS_EMPTY("ERROR: Nickname is empty"),
        WRONG_LETTERS_IN_NICKNAME("ERROR: Wrong letters in nickname"),
        CANT_SAVE_SETTINGS("ERROR: Settings can't be saved"),
        UNKNOWN("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
