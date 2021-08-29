package cc.abro.tow.client;

import cc.abro.orchengine.gui.EventableGuiPanel;
import org.joml.Vector4f;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.shadow.Shadow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class GameTabGuiPanel extends EventableGuiPanel {

    private static final float A = (float) 230/255;
    private static final float DARK_GRAY = (float) 48/255;
    private static final float GRAY = (float) 99/255;

    private static final float R_GREEN = (float) 87/255;
    private static final float G_GREEN = (float) 96/255;
    private static final float B_GREEN = (float) 87/255;

    private static final float R_RED = (float) 94/255;
    private static final float G_RED = (float) 85/255;
    private static final float B_RED = (float) 85/255;

    private static final float R_BLUE = (float) 85/255;
    private static final float G_BLUE = (float) 86/255;
    private static final float B_BLUE = (float) 94/255;

    private static final Vector4f BLACK_TAB_COLOR = new Vector4f(0.0f, 0.0f, 0.0f, A);
    private static final Vector4f DARK_GRAY_TAB_COLOR = new Vector4f(DARK_GRAY, DARK_GRAY, DARK_GRAY, A);
    private static final Vector4f GRAY_TAB_COLOR = new Vector4f(GRAY, GRAY, GRAY, A);
    private static final Vector4f GREEN_TAB_COLOR = new Vector4f(R_GREEN, G_GREEN, B_GREEN, A);
    private static final Vector4f RED_TAB_COLOR = new Vector4f(R_RED, G_RED, B_RED, A);
    private static final Vector4f BLUE_TAB_COLOR = new Vector4f(R_BLUE, G_BLUE, B_BLUE, A);

    private final List<List<Panel>> panels = new ArrayList<>();

    public GameTabGuiPanel(int countOfPlayers) {
        setFocusable(false);
        setStyle(createTabMenuStyle());
        getStyle().getBackground().setColor(INVISIBLE_COLOR);
        getStyle().setBorder(new SimpleLineBorder(BLACK_TAB_COLOR, 2));
        for(int i = 0; i <= countOfPlayers; i++) {
            List<Panel> listOfPanels = new ArrayList<>();
            panels.add(listOfPanels);
            listOfPanels.add(new Panel(0, (TAB_LINE_SIZE_Y + 2)*i,
                    TAB_SIZE_NICKNAME_X, TAB_LINE_SIZE_Y));
            for(int j = 0; j < 4; j++) {
                listOfPanels.add(new Panel(TAB_SIZE_NICKNAME_X + 2*(j+1) + TAB_SIZE_ICO_X*j, (TAB_LINE_SIZE_Y + 2) * i,
                        TAB_SIZE_ICO_X, TAB_LINE_SIZE_Y));
            }
        }
        for(List<Panel> listOfPanels: panels) {
            for(Panel panel: listOfPanels) {
                panel.setStyle(createTabMenuStyle());
                add(panel);
            }
        }
        for(int i = 1; i <= countOfPlayers; i++) {
            panels.get(i).get(1).getStyle().getBackground().setColor(GREEN_TAB_COLOR);
            panels.get(i).get(2).getStyle().getBackground().setColor(RED_TAB_COLOR);
            panels.get(i).get(3).getStyle().getBackground().setColor(BLUE_TAB_COLOR);

        }
        setSize(0, 0);
    }

    public static Style createTabMenuStyle() {
        Style style = new Style();
        style.setBorderRadius(0);
        style.setShadow(new Shadow(0, 0, 0, 0, INVISIBLE_COLOR));
        style.setBorder(new SimpleLineBorder(BLACK_TAB_COLOR, 1));
        style.getBackground().setColor(GRAY_TAB_COLOR);
        return style;
    }

    public void fillInTable() {

    }
}
