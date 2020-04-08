package tow.game.client.menu;

import org.joml.Vector4f;
import org.liquidengine.legui.component.optional.TextState;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.color.ColorConstants;
import org.liquidengine.legui.style.font.FontRegistry;

//переписать на функции, которые возвращают уникалные экземпляры объектов

public final class InterfaceStyles {

    protected final static int MENU_ELEMENT_WIDTH = 250;
    protected final static int MENU_ELEMENT_HEIGHT = 70;
    protected final static int BUTTON_WIDTH = 5*MENU_ELEMENT_WIDTH/13;
    protected final static int BUTTON_HEIGHT = MENU_ELEMENT_HEIGHT/3;
    protected final static int MENU_TEXT_FIELD_HEIGHT = BUTTON_HEIGHT;


    protected final static int BUTTON_RADIUS = 0;

    protected final static Background TEXT_AREA_FIELD_BACKGROUND = new Background();
    protected final static Background PANEL_BACKGROUND = new Background();
    protected final static Background BUTTON_BACKGROUND = new Background();

    protected final static SimpleLineBorder BUTTON_BORDER = new SimpleLineBorder(ColorConstants.black(), 1.5f);
    protected final static SimpleLineBorder PANEL_BORDER = new SimpleLineBorder(ColorConstants.black(), 1);

    protected final static Style BUTTON_STYLE = new Style();
    protected final static Style MENU_BUTTON_STYLE = new Style();
    protected final static Style PANEL_STYLE = new Style();
    protected final static Style TEXT_AREA_FIELD_STYLE = new Style();

    static {
        TEXT_AREA_FIELD_BACKGROUND.setColor(new Vector4f((float) 0.8, (float) 0.8, (float) 0.8, 1));
        PANEL_BACKGROUND.setColor(new Vector4f((float) 0.6, (float) 0.6, (float) 0.6, 1));
        BUTTON_BACKGROUND.setColor(new Vector4f((float) 0.4, (float) 0.4, (float) 0.4, 1));

        BUTTON_STYLE.setBorder(BUTTON_BORDER);
        BUTTON_STYLE.setBackground(BUTTON_BACKGROUND);
        BUTTON_STYLE.setBorderRadius(BUTTON_RADIUS);

        MENU_BUTTON_STYLE.setBorder(BUTTON_BORDER);
        MENU_BUTTON_STYLE.setBackground(PANEL_BACKGROUND);

        PANEL_STYLE.setBorder(PANEL_BORDER);
        PANEL_STYLE.setBackground(PANEL_BACKGROUND);

        TEXT_AREA_FIELD_STYLE.setBackground(TEXT_AREA_FIELD_BACKGROUND);
    }
}
