package cc.abro.tow.client.gui.menu.panels;

import cc.abro.tow.client.gui.menu.InterfaceStyles;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.style.font.FontRegistry;

public class ZeroRuleGuiPanel extends MenuGuiPanel {

    public ZeroRuleGuiPanel() {
        setStyle(InterfaceStyles.createInvisibleStyle());
        setSize(1000, InterfaceStyles.LABEL_HEIGHT_ZERO_RULE*2);
        Label label = new Label("GameDev can be just a way to have fun", 0, 0, InterfaceStyles.LABEL_LENGTH_ZERO_RULE, InterfaceStyles.LABEL_HEIGHT_ZERO_RULE);
        label.getStyle().setFont(FontRegistry.ROBOTO_BOLD);
        label.getStyle().setFontSize(InterfaceStyles.SLIGHTLY_BIG_LABEL_FONT_SIZE);
        label.getStyle().setTextColor(InterfaceStyles.BLACK_COLOR);
        add(label);
    }
}
