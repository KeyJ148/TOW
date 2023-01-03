package cc.abro.tow.client.menu.panels;

import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.font.FontRegistry;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class ZeroRuleGuiPanel extends MenuGuiPanel {

    public ZeroRuleGuiPanel() {
        setStyle(createInvisibleStyle());
        setSize(1000, LABEL_HEIGHT_ZERO_RULE*2);
        Label label = new Label("GameDev can be just a way to have fun", 0, 0, LABEL_LENGTH_ZERO_RULE, LABEL_HEIGHT_ZERO_RULE);
        label.getStyle().setFont(FontRegistry.ROBOTO_BOLD);
        label.getStyle().setFontSize(SLIGHTLY_BIG_LABEL_FONT_SIZE);
        label.getStyle().setTextColor(BLACK_COLOR);
        add(label);
    }
}
