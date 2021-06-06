package cc.abro.tow.client.menu.panels.controllers.settings;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.image.Color;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;
import cc.abro.tow.client.menu.panels.gui.PrintErrorGuiPanel;
import cc.abro.tow.client.menu.panels.gui.SettingsMenuGuiPanel;

import java.util.Set;

public class ClickConfirmController extends GuiElementController<ClickConfirmGuiEvent> {

    @Override
    public void processEvent(ClickConfirmGuiEvent event) {
        if (!event.getNickname().isEmpty()) {
            ClientData.name = event.getNickname();
        } else {
            new PrintErrorGuiPanel(Error.NICKNAME_IS_EMPTY.getText(), getGuiElement().getComponent());
        }
        ClientData.color = event.getTankColor();
    }

    public enum Error {
        NICKNAME_IS_EMPTY {
            @Override
            public String getText() {
                return "ERROR: Nickname is empty!";
            }
        },
        WRONG_LETTERS_IN_NICKNAME {
            @Override
            public String getText() {
                return "ERROR: Wrong letters in nickname";
            }
        },
        UNKNOWN {
            @Override
            public String getText() {
                return "ERROR: Something went wrong";
            }
        };

        public abstract String getText();
    }
}
