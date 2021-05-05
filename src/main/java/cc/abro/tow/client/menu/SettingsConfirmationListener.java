package cc.abro.tow.client.menu;

import cc.abro.orchengine.image.Color;
import cc.abro.tow.client.ClientData;

import java.util.function.Consumer;

public class SettingsConfirmationListener {

    private Consumer<Error> errorConsumer;

    public SettingsConfirmationListener(Consumer<Error> errorConsumer) {
        this.errorConsumer = errorConsumer;
    }

    public void confirm(String nickname, Color tankColor) {
        if (!nickname.isEmpty()) {
            ClientData.name = nickname;
        } else {
            errorConsumer.accept(Error.NICKNAME_IS_EMPTY);
        }
        ClientData.color = tankColor;
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
