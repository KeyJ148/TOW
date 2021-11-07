package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gui.BlockingGuiPanel;
import cc.abro.tow.client.menu.panels.events.main.ClickCloseChildPanelGuiEvent;
import org.liquidengine.legui.component.Component;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.createButton;

public class ButtonBlockingGuiPanel extends LabelBlockingGuiPanel {

    public ButtonBlockingGuiPanel(String message, int sizeX, int sizeY, Component parent) {
        super(message, sizeX, sizeY, parent);

        add(createButton("OK", (sizeX - SMALL_BUTTON_WIDTH)/2, sizeY - BUTTON_HEIGHT - BLOCKING_BUTTON_INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickCloseChildPanelGuiEvent(getUnfocusedComponents())));
    }
}