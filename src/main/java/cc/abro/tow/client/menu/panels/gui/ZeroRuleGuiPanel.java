package cc.abro.tow.client.menu.panels.gui;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.font.FontRegistry;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ZeroRuleGuiPanel extends MenuGuiPanel {

    public ZeroRuleGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(1000, MENU_TEXT_FIELD_HEIGHT*2);
        Label label = new Label("GameDev can be just a way to have fun", 0, 0, LABEL_LENGTH_ZERO_RULE, LABEL_HEIGHT_ZERO_RULE);
        label.getTextState().setFont(FontRegistry.ROBOTO_BOLD);
        label.getTextState().setFontSize(SLIGHTLY_BIG_LABEL_FONT_SIZE);
        label.getTextState().setTextColor(BLACK_COLOR);
        add(label);
    }
}
