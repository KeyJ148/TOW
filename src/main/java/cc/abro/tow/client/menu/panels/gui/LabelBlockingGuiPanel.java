package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gui.BlockingGuiPanel;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Label;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.createLabel;

public class LabelBlockingGuiPanel extends BlockingGuiPanel {
    private Label label;
    public LabelBlockingGuiPanel(String message, int sizeX, int sizeY, Component parent) {
        super(sizeX, sizeY, parent);

        int widthOfMessage = (int) (LABEL_FONT_SIZE*9/24) * message.length();
        label = createLabel(message, (sizeX - widthOfMessage)/2, BLOCKING_BUTTON_INDENT_Y, widthOfMessage, MENU_TEXT_FIELD_HEIGHT);
        add(label);
    }

    public Label getLabel() {return label;}

}
