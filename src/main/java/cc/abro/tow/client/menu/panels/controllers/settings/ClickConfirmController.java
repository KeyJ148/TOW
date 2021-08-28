package cc.abro.tow.client.menu.panels.controllers.settings;

import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;

import static cc.abro.tow.client.menu.InterfaceStyles.ERROR_ELEMENT_HEIGHT;
import static cc.abro.tow.client.menu.InterfaceStyles.ERROR_ELEMENT_WIDTH;

public class ClickConfirmController extends MenuClickController<ClickConfirmGuiEvent> {

    @Override
    protected Class<ClickConfirmGuiEvent> getProcessedEventClass() {
        return ClickConfirmGuiEvent.class;
    }

    @Override
    public void processEvent(ClickConfirmGuiEvent event) {
        if (!event.getNickname().isEmpty()) {
            ClientData.name = event.getNickname();
        } else {
            createBlockingPanelWithButton(Error.NICKNAME_IS_EMPTY.getText(), ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
        }
        ClientData.color = event.getTankColor();
    }

    public enum Error {
        NICKNAME_IS_EMPTY("ERROR: Nickname is empty"),
        WRONG_LETTERS_IN_NICKNAME("ERROR: Wrong letters in nickname"),
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
