package cc.abro.tow.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.font.FontRegistry;

public final class InterfaceStyles {

    public final static int MENU_ELEMENT_WIDTH = 250;
    public final static int MENU_ELEMENT_HEIGHT = 70;
    public final static int SETTINGS_PANEL_WIDTH = 5 * MENU_ELEMENT_WIDTH/3 + 1;
    public final static int SETTINGS_PANEL_HEIGHT = 3 * MENU_ELEMENT_HEIGHT;
    public final static int BLOCKING_BUTTON_ELEMENT_WIDTH = 360;
    public final static int BLOCKING_BUTTON_ELEMENT_HEIGHT = 90;
    public final static int CONNECTING_ELEMENT_WIDTH = 160;
    public final static int CONNECTED_PLAYERS_ELEMENT_WIDTH = 230;
    public final static int WAITING_ELEMENT_WIDTH = 180;
    public final static int CONNECTING_ELEMENT_HEIGHT = 70;

    public final static int MENU_BUTTON_WIDTH = MENU_ELEMENT_WIDTH;
    public final static int MENU_BUTTON_HEIGHT = 3*MENU_ELEMENT_HEIGHT/4;
    public final static int BUTTON_WIDTH = 106;
    public final static int SMALL_BUTTON_WIDTH = BUTTON_WIDTH/2;
    public final static int BUTTON_HEIGHT = 23;
    public final static int TAB_BUTTON_HEIGHT = 26;
    public final static int MENU_TEXT_FIELD_HEIGHT = BUTTON_HEIGHT;

    public final static int TEXT_AREA_LENGTH_MAX_PEOPLE = 20;
    public final static int TEXT_AREA_LENGTH_PORT = 50;
    public final static int TEXT_AREA_LENGTH_IP = 125;
    public final static int TEXT_AREA_LENGTH_SERVER_NAME = 200;

    public final static int INDENT_X = 18;
    public final static int INDENT_Y = INDENT_X + 4;
    public final static int BLOCKING_BUTTON_INDENT_Y = 12;

    public final static int TAB_SIZE_X = 420;               //without borders
    public final static int TAB_SIZE_NICKNAME_X = 280;      //without borders

    public final static int TAB_SIZE_ICO_X = 33;            //without borders
    public final static int TAB_LINE_SIZE_Y = 26;           //without borders

    public final static int BUTTON_RADIUS = 0;

    public final static int LABEL_LENGTH_ZERO_RULE = 360;
    public final static int LABEL_LENGTH_PORT = 37;
    public final static int LABEL_LENGTH_NICKNAME = 78;
    public final static int LABEL_LENGTH_ID = 20;
    public final static int LABEL_LENGTH_MAX_PEOPLE = 130;
    public final static int LABEL_LENGTH_SERVER_NAME = 96;
    public final static int LABEL_LENGTH_LIST_OF_SERVERS = 180;

    public final static int LABEL_HEIGHT_DEBUG = 14;
    public final static int LABEL_HEIGHT_ZERO_RULE = 18;
    public final static int LABEL_HEIGHT_CHAPTER = 24;

    public final static float THICKNESS_OF_TEXT_AREA_BORDER = 2f;
    public final static float THICKNESS_OF_PANEL_BORDER = 2f;
    public final static float THICKNESS_OF_BUTTON_BORDER = 2f;

    public final static float MENU_BUTTON_FONT_SIZE = 30;
    public final static float BUTTON_FONT_SIZE = 20;
    public final static float LABEL_FONT_SIZE = BUTTON_FONT_SIZE;
    public final static float SLIGHTLY_BIG_LABEL_FONT_SIZE = 27;
    public final static float BIG_LABEL_FONT_SIZE = 35;
    public final static float ANALYZER_INFO_FONT_SIZE = 16;
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

    public static Background createScrollBarBackground() {
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

    public static Background createScrollablePanelContainerBackground() {
        Background background = new Background();
        background.setColor(LIGHT_GRAY_COLOR);
        return background;
    }

    public static SimpleLineBorder createButtonBorder() {
        return new SimpleLineBorder(BLACK_COLOR, THICKNESS_OF_BUTTON_BORDER);
    }

    public static SimpleLineBorder createPanelBorder() {
        return new SimpleLineBorder(BLACK_COLOR, THICKNESS_OF_PANEL_BORDER);
    }

    public static SimpleLineBorder createTextAreaFieldBorder() {
        return new SimpleLineBorder(BLACK_COLOR, THICKNESS_OF_TEXT_AREA_BORDER);
    }

    public static SimpleLineBorder createScrollBarBorder() {
        return new SimpleLineBorder(BLACK_COLOR, 1.5f);
    }


    public static Style createInvisibleStyle() {
        Style style = new Style();
        style.getBackground().setColor(INVISIBLE_COLOR);
        style.setBorder(new SimpleLineBorder(INVISIBLE_COLOR, 0));
        return style;
    }

    public static Style createLabelStyle() {
        Style style = new Style();
        style.setFont(FontRegistry.ROBOTO_REGULAR);
        style.setFontSize(LABEL_FONT_SIZE);
        style.setTextColor(BLACK_COLOR);
        style.getBackground().setColor(INVISIBLE_COLOR);
        return style;
    }

    public static Style createLargerLabelStyle() {
        Style style = new Style();
        style.setFont(FontRegistry.ROBOTO_BOLD);
        style.setFontSize(SLIGHTLY_BIG_LABEL_FONT_SIZE);
        style.setTextColor(BLACK_COLOR);
        style.getBackground().setColor(INVISIBLE_COLOR);
        return style;
    }

    public static Style createBigLabelStyle() {
        Style style = new Style();
        style.setFont(FontRegistry.ROBOTO_BOLD);
        style.setFontSize(BIG_LABEL_FONT_SIZE);
        style.setTextColor(BLACK_COLOR);
        style.getBackground().setColor(INVISIBLE_COLOR);
        return style;
    }


    public static Style createScrollBarStyle() {
        Style style = new Style();
        style.setWidth(8.0F);
        style.setTop(0.0F);
        style.setRight(0.0F);
        style.setBottom(8.0F);
        style.setBorder(createScrollBarBorder());
        style.setBackground(createScrollBarBackground());
        style.setFocusedStrokeColor(LIGHT_GRAY_COLOR);
        return style;
    }

    public static Style createButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createButtonBackground());
        style.setTextColor(BLACK_COLOR);
        style.setBorderRadius(BUTTON_RADIUS);
        style.setFont(FontRegistry.ROBOTO_REGULAR);
        style.setFontSize(BUTTON_FONT_SIZE);
        style.setHorizontalAlign(HorizontalAlign.CENTER);
        return style;
    }

    public static Style createActiveButtonStyle() {
        Style style = new Style();
        style.setBorder(new SimpleLineBorder(INVISIBLE_COLOR, 0));
        style.setBackground(createHoveredButtonBackground());
        style.setTextColor(BLACK_COLOR);
        style.setBorderRadius(BUTTON_RADIUS);
        style.setFont(FontRegistry.ROBOTO_REGULAR);
        style.setFontSize(BUTTON_FONT_SIZE);
        style.setHorizontalAlign(HorizontalAlign.CENTER);
        return style;
    }

    public static Style createMenuButtonStyle() {
        Style style = new Style();
        style.setBorder(createButtonBorder());
        style.setBackground(createMenuButtonBackground());
        style.setFont(FontRegistry.ROBOTO_BOLD);
        style.setFontSize(MENU_BUTTON_FONT_SIZE);
        style.setTextColor(BLACK_COLOR);
        style.setHorizontalAlign(HorizontalAlign.CENTER);
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

    public static Style createScrollablePanelContainerStyle() {
        Style style = new Style();
        style.setBackground(createScrollablePanelContainerBackground());
        return style;
    }

    public static Style createTextAreaFieldStyle() {
        Style style = new Style();
        style.setBackground(createTextAreaFieldBackground());
        style.setBorder(createTextAreaFieldBorder());
        style.setFocusedStrokeColor(INVISIBLE_COLOR);
        style.setHighlightColor(LIGHT_GRAY_COLOR);
        return style;
    }
}
