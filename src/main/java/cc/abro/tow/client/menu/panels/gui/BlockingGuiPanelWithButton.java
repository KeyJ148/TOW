package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.main.ClickCloseChildPanelGuiEvent;
import org.liquidengine.legui.component.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class BlockingGuiPanelWithButton extends BlockingGuiPanel {

    public BlockingGuiPanelWithButton(String message, int sizeX, int sizeY, Component parent) {
        super(message, sizeX, sizeY, parent);

        addButton("OK", (sizeX - SMALL_BUTTON_WIDTH)/2, sizeY - BUTTON_HEIGHT - BLOCKING_BUTTON_INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickCloseChildPanelGuiEvent(getUnfocusedComponents()));
    }
}
