package tow.game.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;
import org.lwjgl.system.CallbackI;

//переписать на функции, которые возвращают уникалные экземпляры объектов

public final class InterfaceStyles {

    protected final static int MENU_ELEMENT_WIDTH = 250;
    protected final static int MENU_ELEMENT_HEIGHT = 70;
    protected final static int BUTTON_WIDTH = 5*MENU_ELEMENT_WIDTH/13;
    protected final static int BUTTON_HEIGHT = MENU_ELEMENT_HEIGHT/3;
    protected final static int MENU_TEXT_FIELD_HEIGHT = BUTTON_HEIGHT;

    protected final static int INDENT_X = 4*MENU_ELEMENT_WIDTH/36;

    protected final static int BUTTON_RADIUS = 0;

    protected static Background createTextAreaFieldBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        return background;
    }

    protected static Background createPanelBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.6, (float) 0.6, (float) 0.6, 1));
        return background;
    }

    protected static Background createButtonBackground() {
        Background background = new Background();
        background.setColor(new Vector4f((float) 0.4, (float) 0.4, (float) 0.4, 1));
        return background;
    }

    protected static SimpleLineBorder createButtonBorder() {
        return new SimpleLineBorder(ColorConstants.black(), 1.5f);
    }

    protected static SimpleLineBorder createPanelBorder() {
        return new SimpleLineBorder(ColorConstants.black(), 1);
    }

    protected static Style createButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createButtonBackground());
        style.setBorderRadius(BUTTON_RADIUS);
        return style;
    }

    protected static Style createMenuButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createPanelBackground());
        return style;
    }

    protected static Style createPanelStyle() {
        Style style = new Style();
        style.setBorder(createPanelBorder());
        style.setBackground(createPanelBackground());
        return style;
    }

    protected static Style createTextAreaFieldStyle() {
        Style style = new Style();
        style.setBackground(createTextAreaFieldBackground());
        return style;
    }
}
