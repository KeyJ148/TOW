package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.main.ClickCloseChildPanelGuiEvent;
import org.liquidengine.legui.component.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class PrintErrorGuiPanel extends MenuGuiPanel {

    public PrintErrorGuiPanel(String message, Component parent) {
        super();
        setSize(ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);

        int widthOfMessage = (LABEL_FONT_SIZE*5/12)*message.length();
        addLabel(message, (ERROR_ELEMENT_WIDTH - widthOfMessage)/2, INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

        Set<Component> unfocusedComponents = parent.getChildComponents().stream()
                .filter(component -> !component.isFocused())
                .peek(component -> component.setFocusable(false))
                .collect(Collectors.toSet());

        addButton("OK", (ERROR_ELEMENT_WIDTH - SMALL_BUTTON_WIDTH)/2, ERROR_ELEMENT_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, () -> new ClickCloseChildPanelGuiEvent(unfocusedComponents));
    }
}
