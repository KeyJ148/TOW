package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.events.main.ClickCloseChildPanelGuiEvent;
import com.google.common.collect.ImmutableSet;
import org.liquidengine.legui.component.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class BlockingGuiPanel extends MenuGuiPanel{

    private final Set<Component> unfocusedComponents;

    public BlockingGuiPanel(String message, int sizeX, int sizeY, Component parent) {
            super();
            setSize(sizeX, sizeY);

            int widthOfMessage = (LABEL_FONT_SIZE*5/12)*message.length();
            addLabel(message, (sizeX - widthOfMessage)/2, INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

            unfocusedComponents = parent.getChildComponents().stream()
                    .filter(Component::isFocusable)
                    .peek(component -> component.setFocusable(false))
                    .collect(Collectors.toUnmodifiableSet());
    }

    public Set<Component> getUnfocusedComponents() {
        return unfocusedComponents;
    }
}
