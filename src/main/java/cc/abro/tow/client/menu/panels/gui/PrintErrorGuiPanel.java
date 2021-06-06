package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.PrintErrorGuiEvent;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.InterfaceStyles.BUTTON_HEIGHT;
import static cc.abro.tow.client.menu.panels.events.PrintErrorGuiEvent.MainMenuGuiEventType.CLICK_BUTTON_OK;

public class PrintErrorGuiPanel extends MenuGuiPanel {

    public PrintErrorGuiPanel(String message, MenuGuiPanel parent) {
        init();
        setSize(ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
        //TODO new GuiPanelElementService().createPanelOnLocationCenter(this, getGameObject().getComponent(Position.class).location);

        int widthOfMessage = (LABEL_FONT_SIZE*5/12)*message.length();
        addLabel(message, (ERROR_ELEMENT_WIDTH - widthOfMessage)/2, INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

        parent.getChildComponents().iterator().forEachRemaining(c -> c.setFocusable(false));

        addButton("OK", (ERROR_ELEMENT_WIDTH - SMALL_BUTTON_WIDTH)/2, ERROR_ELEMENT_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, new PrintErrorGuiEvent(CLICK_BUTTON_OK));
    }
}
