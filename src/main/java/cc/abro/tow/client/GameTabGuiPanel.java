package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import org.joml.Vector4f;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.Style;
import org.liquidengine.legui.style.border.SimpleLineBorder;
import org.liquidengine.legui.style.font.FontRegistry;
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

    private static final String BOLD = FontRegistry.ROBOTO_BOLD;
    private static final String REGULAR = FontRegistry.ROBOTO_REGULAR;

    private final List<List<Panel>> panels = new ArrayList<>();

    public GameTabGuiPanel(int countOfPlayers) {
        setFocusable(false);
        setSize(0, 0);
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
        panels.get(0).get(0).add(createLabel("Nickname", (int)panels.get(0).get(0).getSize().x/2 - 30, 0,
                (int)panels.get(0).get(0).getSize().x, (int)panels.get(0).get(1).getSize().y, BOLD));
        addImageViewInCenterOfPanel("kills_icon", panels.get(0).get(1));
        addImageViewInCenterOfPanel("deaths_icon", panels.get(0).get(2));
        addImageViewInCenterOfPanel("wins_icon", panels.get(0).get(3));
        addImageViewInCenterOfPanel("ping_icon", panels.get(0).get(4));
        //addImageViewInCenterOfPanel("dead_icon", panels.get(0).get(1));
        for(int i = 1; i <= countOfPlayers; i++) {
            panels.get(i).get(1).getStyle().getBackground().setColor(GREEN_TAB_COLOR);
            panels.get(i).get(2).getStyle().getBackground().setColor(RED_TAB_COLOR);
            panels.get(i).get(3).getStyle().getBackground().setColor(BLUE_TAB_COLOR);

        }
    }

    private void addImageViewInCenterOfPanel(String name, Panel panel) {
        Texture texture = Manager.getService(SpriteStorage.class).getSprite(name).getTexture();

        FBOImage logoFBOImage = new FBOImage(texture.getId(), texture.getWidth(), texture.getHeight());

        ImageView imageView = new ImageView(logoFBOImage);
        imageView.setStyle(createInvisibleStyle());
        imageView.setSize(texture.getWidth(), texture.getHeight());
        imageView.setPosition((panel.getSize().x - texture.getWidth())/2, (panel.getSize().y - texture.getHeight())/2);
        panel.add(imageView);
    }

    public static Style createTabMenuStyle() {
        Style style = new Style();
        style.setBorderRadius(0);
        style.setShadow(new Shadow(0, 0, 0, 0, INVISIBLE_COLOR));
        style.setBorder(new SimpleLineBorder(BLACK_TAB_COLOR, 1));
        style.getBackground().setColor(GRAY_TAB_COLOR);
        return style;
    }

    public Label createLabel(String text, int x, int y, int width, int height, String font) {
        Label label = new Label(text, x, y, width, height);
        label.getTextState().setFont(font);
        label.getTextState().setFontSize(LABEL_FONT_SIZE);
        return label;
    }

    public void fillInTable() {


    }
}
