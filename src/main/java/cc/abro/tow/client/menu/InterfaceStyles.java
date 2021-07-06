package cc.abro.tow.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;

public final class InterfaceStyles {

    public final static int MENU_ELEMENT_WIDTH = 250;
    public final static int MENU_ELEMENT_HEIGHT = 70;
    public final static int BUTTON_WIDTH = 106;
    public final static int SMALL_BUTTON_WIDTH = BUTTON_WIDTH/2;
    public final static int BUTTON_HEIGHT = 23;
    public final static int MENU_TEXT_FIELD_HEIGHT = BUTTON_HEIGHT;
    public final static int ERROR_ELEMENT_WIDTH = 400;
    public final static int ERROR_ELEMENT_HEIGHT = 120;

    public final static int TEXT_AREA_LENGTH_MAX_PEOPLE = 20;
    public final static int TEXT_AREA_LENGTH_PORT = 50;
    public final static int TEXT_AREA_LENGTH_IP = 125;
    public final static int TEXT_AREA_LENGTH_SERVER_NAME = 200;

    public final static int INDENT_X = 18;
    public final static int INDENT_Y = INDENT_X + 4;

    public final static int BUTTON_RADIUS = 0;

    public final static int LABEL_LENGTH_PORT = 37;
    public final static int LABEL_LENGTH_NICKNAME = 78;
    public final static int LABEL_LENGTH_ID = 20;
    public final static int LABEL_LENGTH_MAX_PEOPLE = 130;
    public final static int LABEL_LENGTH_SERVER_NAME = 96;


    public final static int MENU_BUTTON_FONT_SIZE = 30;
    public final static int BUTTON_FONT_SIZE = 20;
    public final static int LABEL_FONT_SIZE = BUTTON_FONT_SIZE;
    public final static int LABEL_ERROR_FONT_SIZE = 18;

    public final static Vector4f WHITE_COLOR = new Vector4f(1.0f, 1.0f, 1.0f, 1);
    public final static Vector4f DARK_WHITE_COLOR = new Vector4f(0.9f, 0.9f, 0.9f, 1);
    public final static Vector4f VERY_LIGHT_GRAY_COLOR = new Vector4f(0.8f, 0.8f, 0.8f, 1);
    public final static Vector4f LIGHT_GRAY_COLOR = new Vector4f(0.7f, 0.7f, 0.7f, 1);
    public final static Vector4f SLIGHTLY_LIGHT_GRAY_COLOR = new Vector4f(0.6f, 0.6f, 0.6f, 1);
    public final static Vector4f GRAY_COLOR = new Vector4f(0.5f, 0.5f, 0.5f, 1);
    public final static Vector4f SLIGHTLY_DARK_GRAY_COLOR = new Vector4f(0.4f, 0.4f, 0.4f, 1);
    public final static Vector4f DARK_GRAY_COLOR = new Vector4f(0.3f, 0.3f, 0.3f, 1);
    public final static Vector4f VERY_DARK_GRAY_COLOR = new Vector4f(0.2f, 0.2f, 0.2f, 1);
    public final static Vector4f LIGHT_BLACK_COLOR = new Vector4f(0.1f, 0.1f, 0.1f, 1);
    public final static Vector4f BLACK_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 1);

    public final static Vector4f INVISIBLE_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, 0.0f);

    public final static Vector4f RED_COLOR = new Vector4f(1.0f, 0.0f, 0.0f, 1);

    public final static Vector4f GREEN_COLOR = new Vector4f(0.0f, 1.0f, 0.0f, 1);
    public final static Vector4f BLIND_GREEN_COLOR = new Vector4f(0.4f, 0.9f, 0.4f, 1);

    public final static Vector4f BLUE_COLOR = new Vector4f(0.0f, 0.0f, 1.0f, 1);

    public final static Vector4f YELLOW_COLOR = new Vector4f(1.0f, 1.0f, 0.0f, 1);


    public static Background createTextAreaFieldBackground() {
        Background background = new Background();
        background.setColor(DARK_WHITE_COLOR);
        return background;
    }

    public static Background createFocusedTextAreaFieldBackground() {
        Background background = new Background();
        background.setColor(BLIND_GREEN_COLOR);
        return background;
    }

    public static Background createPanelBackground() {
        Background background = new Background();
        background.setColor(GRAY_COLOR);
        return background;
    }

    public static Background createScrollablePanelBackground() {
        Background background = new Background();
        background.setColor(LIGHT_GRAY_COLOR);
        return background;
    }

    public static Background createButtonBackground() {
        Background background = new Background();
        background.setColor(SLIGHTLY_DARK_GRAY_COLOR);
        return background;
    }

    public static Background createHoveredButtonBackground() {
        Background background = new Background();
        background.setColor(GRAY_COLOR);
        return background;
    }

    public static Background createPressedButtonBackground() {
        Background background = new Background();
        background.setColor(SLIGHTLY_LIGHT_GRAY_COLOR);
        return background;
    }

    public static Background createMenuButtonBackground() {
        Background background = new Background();
        background.setColor(SLIGHTLY_DARK_GRAY_COLOR);
        return background;
    }

    public static Background createHoveredMenuButtonBackground() {
        Background background = new Background();
        background.setColor(GRAY_COLOR);
        return background;
    }

    public static Background createPressedMenuButtonBackground() {
        Background background = new Background();
        background.setColor(SLIGHTLY_LIGHT_GRAY_COLOR);
        return background;
    }

    public static SimpleLineBorder createButtonBorder() {
        return new SimpleLineBorder(BLACK_COLOR, 2f);
    }

    public static SimpleLineBorder createPanelBorder() {
        return new SimpleLineBorder(BLACK_COLOR, 3f);
    }

    public static SimpleLineBorder createTextAreaFieldBorder() {
        return new SimpleLineBorder(BLACK_COLOR, 1.5f);
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
        style.setBackground(createMenuButtonBackground());
        return style;
    }

    public static Style createPanelStyle() {
        Style style = new Style();
        style.setBorder(createPanelBorder());
        style.setBackground(createPanelBackground());
        return style;
    }

    public static Style createScrollablePanelStyle() {
        Style style = new Style();
        style.setDisplay(Style.DisplayType.FLEX);
        style.setBorder(createPanelBorder());
        style.setBackground(createScrollablePanelBackground());
        return style;
    }

    public static Style createTextAreaFieldStyle() {
        Style style = new Style();
        style.setBackground(createTextAreaFieldBackground());
        style.setBorder(createTextAreaFieldBorder());
        style.setFocusedStrokeColor(INVISIBLE_COLOR);
        return style;
    }
}
