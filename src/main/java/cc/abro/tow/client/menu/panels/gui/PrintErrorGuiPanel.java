package cc.abro.tow.client.menu.panels.gui;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.InterfaceStyles.BUTTON_HEIGHT;
public class PrintErrorGuiPanel extends MenuGuiPanel {

    PrintErrorGuiPanel(String message, MenuGuiPanel parent) {
        init();
        setSize(ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
        //TODO new GuiPanelElementService().createPanelOnLocationCenter(this, getGameObject().getComponent(Position.class).location);

        int widthOfMessage = (LABEL_FONT_SIZE*5/12)*message.length();
        addLabel(message, (ERROR_ELEMENT_WIDTH - widthOfMessage)/2, INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

        parent.getChildComponents().iterator().forEachRemaining(c -> c.setFocusable(false));

        /*TODO addButton("OK", (ERROR_ELEMENT_WIDTH - SMALL_BUTTON_WIDTH)/2, ERROR_ELEMENT_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, event -> {
            destroy();
            parent.getChildComponents().iterator().forEachRemaining(c -> c.setFocusable(true));
        });*/
    }
}
