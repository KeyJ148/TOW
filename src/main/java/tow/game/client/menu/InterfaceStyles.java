package tow.game.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;

public final class InterfaceStyles {

    public final static int MENU_ELEMENT_WIDTH = 250;
    public final static int MENU_ELEMENT_HEIGHT = 70;
    public final static int BUTTON_WIDTH = 5*MENU_ELEMENT_WIDTH/13;
    public final static int BUTTON_HEIGHT = MENU_ELEMENT_HEIGHT/3;
    public final static int MENU_TEXT_FIELD_HEIGHT = BUTTON_HEIGHT;

    public final static int INDENT_X = 4*MENU_ELEMENT_WIDTH/36;

    public final static int BUTTON_RADIUS = 0;

    public static Background createTextAreaFieldBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        return background;
    }

    public static Background createPanelBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.6, (float) 0.6, (float) 0.6, 1));
        return background;
    }

    public static Background createButtonBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.4, (float) 0.4, (float) 0.4, 1));
        return background;
    }

    public static SimpleLineBorder createButtonBorder() {
        return new SimpleLineBorder(ColorConstants.black(), 1.5f);
    }

    public static SimpleLineBorder createPanelBorder() {
        return new SimpleLineBorder(ColorConstants.black(), 1);
    }

    public static Style createButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createButtonBackground());
        style.setBorderRadius(BUTTON_RADIUS);
        return style;
    }

    public static Style createMenuButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createPanelBackground());
        return style;
    }

    public static Style createPanelStyle() {
        Style style = new Style();
        style.setBorder(createPanelBorder());
        style.setBackground(createPanelBackground());
        return style;
    }

    public static Style createTextAreaFieldStyle() {
        Style style = new Style();
        style.setBackground(createTextAreaFieldBackground());
        return style;
    }
}
