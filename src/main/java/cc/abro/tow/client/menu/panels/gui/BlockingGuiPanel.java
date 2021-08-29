package cc.abro.tow.client.menu.panels.gui;

import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;

import java.util.Set;
import java.util.stream.Collectors;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class BlockingGuiPanel extends MenuGuiPanel{

    private final Set<Component> unfocusedComponents;

    private final Label label;

    public BlockingGuiPanel(String message, int sizeX, int sizeY, Component parent) {
            super();
            setSize(sizeX, sizeY);

            int widthOfMessage = (LABEL_FONT_SIZE*9/24)*message.length();
            label = addLabel(message, (sizeX - widthOfMessage)/2, BLOCKING_BUTTON_INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);

            unfocusedComponents = parent.getChildComponents().stream()
                    .filter(Component::isFocusable)
                    .peek(component -> component.setFocusable(false))
                    .collect(Collectors.toUnmodifiableSet());
    }

    public Label getLabel() {return label;}

    public Set<Component> getUnfocusedComponents() {
        return unfocusedComponents;
    }
}
