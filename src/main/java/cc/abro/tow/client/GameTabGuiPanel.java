package cc.abro.tow.client;

import cc.abro.orchengine.gui.EventableGuiPanel;
import org.joml.Vector4f;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.border.SimpleLineBorder;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class GameTabGuiPanel extends EventableGuiPanel {

    private static final float A = (float) 230/255;
    private static final float DARK_GRAY = (float) 48/255;
    private static final float GRAY = (float) 99/255;

    private static final Vector4f BLACK_TAB_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, A);
    private static final Vector4f DARK_GRAY_TAB_COLOR = new Vector4f(DARK_GRAY, DARK_GRAY, DARK_GRAY, A);
    private static final Vector4f GRAY_TAB_COLOR = new Vector4f(GRAY, GRAY, GRAY, A);

    public GameTabGuiPanel() {
        setFocusable(false);
        getStyle().setBorder(new SimpleLineBorder(BLACK_TAB_COLOR, 2));
        getStyle().setBorderRadius(0);
        getStyle().getShadow().setColor(INVISIBLE_COLOR);
        getStyle().getBackground().setColor(GRAY_TAB_COLOR);
        setSize(0, 0);
    }
}
