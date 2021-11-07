package cc.abro.tow.client.menu.panels.events.settings;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.image.Color;


public class ClickConfirmGuiEvent implements GuiElementEvent {

    private final String nickname;
    private final Color tankColor;

    public ClickConfirmGuiEvent(String nickname, Color tankColor) {
        this.nickname = nickname;
        this.tankColor = tankColor;
    }

    public String getNickname() {
        return nickname;
    }

    public Color getTankColor() {
        return tankColor;
    }
}
