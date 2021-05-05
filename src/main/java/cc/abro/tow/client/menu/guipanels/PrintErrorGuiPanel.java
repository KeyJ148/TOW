package cc.abro.tow.client.menu.guipanels;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gui.CachedGuiPanel;
import cc.abro.orchengine.services.CachedGuiElementService;
import cc.abro.orchengine.services.GuiElementService;
import cc.abro.tow.client.menu.MenuGuiPanel;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;

import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.InterfaceStyles.BUTTON_HEIGHT;

public class PrintErrorGuiPanel extends MenuGuiPanel {

    PrintErrorGuiPanel(String message, MenuGuiPanel parent) {
        init();
        setSize(ERROR_ELEMENT_WIDTH, ERROR_ELEMENT_HEIGHT);
        new CachedGuiElementService().addCachedComponentToLocationShiftedToCenter(this,
                Global.engine.render.getWidth() / 2,
                Global.engine.render.getHeight() / 2,
                parent.getCachedGuiElementOnActiveLocation().getGameObject().getComponent(Position.class).location);

        int widthOfMessage = (LABEL_FONT_SIZE*5/12)*message.length();
        addLabel(message, (ERROR_ELEMENT_WIDTH - widthOfMessage)/2, INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

        parent.getChildComponents().iterator().forEachRemaining(c -> c.setFocusable(false));

        addButton("OK", (ERROR_ELEMENT_WIDTH - SMALL_BUTTON_WIDTH)/2, ERROR_ELEMENT_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                SMALL_BUTTON_WIDTH, BUTTON_HEIGHT, event -> {destroy();parent.getChildComponents().iterator().forEachRemaining(c -> c.setFocusable(true));});
    }
}
