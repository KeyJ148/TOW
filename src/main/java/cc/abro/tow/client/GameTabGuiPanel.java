package cc.abro.tow.client;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class GameTabGuiPanel extends Panel {

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
    private static final String LIGHT = FontRegistry.ROBOTO_LIGHT;

    private final List<TabLine> tabLines = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    public static class TabLine {
        public List<Panel> panels;
        public ImageView deadIcon;
        public Label nickname;
        public Background nicknameBackground;
        public Label kills;
        public Label deaths;
        public Label wins;
        public Label ping;

        public void createNicknameLabel(String text, String font) {
            nickname = new Label(text, 12, 0, (int)panels.get(0).getSize().x, (int)panels.get(0).getSize().y);
            nickname.getStyle().setFont(font);
            nickname.getStyle().setFontSize(LABEL_FONT_SIZE);
            nickname.getStyle().setTextColor(BLACK_COLOR);
            nickname.setFocusable(false);
            panels.get(0).add(nickname);
        }

        public void createNicknameBackground(Vector4f color) {
            nicknameBackground = new Background();
            nicknameBackground.setColor(color);
            panels.get(0).getStyle().setBackground(nicknameBackground);
        }

        public void changeNicknameBackground(Vector4f color) {
            float maxColor = Math.max(color.x, Math.max(color.y,color.z));
            color.x = GRAY + (color.x - GRAY)/(maxColor*2);
            color.y = GRAY + (color.y - GRAY)/(maxColor*2);
            color.z = GRAY + (color.z - GRAY)/(maxColor*2);
            color.w = A;
            nicknameBackground.setColor(color);
            panels.get(0).getStyle().setBackground(nicknameBackground);
        }

        public void createImageView(String name) {
            Texture texture = Manager.getService(SpriteStorage.class).getSprite(name).getTexture();
            FBOImage logoFBOImage = new FBOImage(texture.getId(), texture.getWidth(), texture.getHeight());
            deadIcon = new ImageView(logoFBOImage);
            deadIcon.setStyle(createInvisibleStyle());
            disableImageView();
            deadIcon.setFocusable(false);
            deadIcon.setPosition(-10, (panels.get(0).getSize().y - texture.getHeight())/2);
            panels.get(0).add(deadIcon);
        }

        public void enableImageView() {
            deadIcon.setSize(deadIcon.getImage().getWidth(), deadIcon.getImage().getHeight());
        }

        public void disableImageView() {
            deadIcon.setSize(0, 0);
        }

        public void createKillsLabelAndColor() {
            kills = new Label("0", panels.get(1).getSize().x/2 - 5, 0,
                    (int)panels.get(1).getSize().x, (int)panels.get(1).getSize().y);
            kills.getStyle().setFont(REGULAR);
            kills.getStyle().setFontSize(LABEL_FONT_SIZE);
            kills.getStyle().setTextColor(BLACK_COLOR);
            kills.setFocusable(false);
            panels.get(1).add(kills);
            panels.get(1).getStyle().getBackground().setColor(GREEN_TAB_COLOR);
        }

        public void createDeathsLabelAndColor() {
            deaths = new Label("0", panels.get(2).getSize().x/2 - 5, 0,
                    (int)panels.get(2).getSize().x, (int)panels.get(2).getSize().y);
            deaths.getStyle().setFont(REGULAR);
            deaths.getStyle().setFontSize(LABEL_FONT_SIZE);
            deaths.getStyle().setTextColor(BLACK_COLOR);
            deaths.setFocusable(false);
            panels.get(2).add(deaths);
            panels.get(2).getStyle().getBackground().setColor(RED_TAB_COLOR);
        }

        public void createWinsLabelAndColor() {
            wins = new Label("0", panels.get(3).getSize().x/2 - 5, 0,
                    (int)panels.get(3).getSize().x, (int)panels.get(3).getSize().y);
            wins.getStyle().setFont(REGULAR);
            wins.getStyle().setFontSize(LABEL_FONT_SIZE);
            wins.getStyle().setTextColor(BLACK_COLOR);
            wins.setFocusable(false);
            panels.get(3).add(wins);
            panels.get(3).getStyle().getBackground().setColor(BLUE_TAB_COLOR);
        }

        public void createPingsLabelAndColor() {
            ping = new Label("0", panels.get(4).getSize().x/2 - 5, 0,
                    (int)panels.get(4).getSize().x, (int)panels.get(4).getSize().y);
            ping.getStyle().setFont(REGULAR);
            ping.getStyle().setFontSize(LABEL_FONT_SIZE);
            ping.getStyle().setTextColor(BLACK_COLOR);
            ping.setFocusable(false);
            panels.get(4).add(ping);
        }

    }

    public static class TabDataLine {
        public Boolean dead;
        public String nickname;
        public Color color;
        public Integer kills;
        public Integer deaths;
        public Integer wins;
        public Integer ping;

        public TabDataLine(boolean dead, String nickname, Color color, Integer kills, Integer deaths, Integer wins, Integer ping) {
            this.dead = dead;
            this.nickname = nickname;
            this.color = color;
            this.kills = kills;
            this.deaths = deaths;
            this.wins = wins;
            this.ping = ping;
        }
    }

    public GameTabGuiPanel(int countOfPlayers) {
        setFocusable(false);
        setSize(0, 0);
        setStyle(createTabMenuStyle());
        getStyle().getBackground().setColor(INVISIBLE_COLOR);
        getStyle().setBorder(new SimpleLineBorder(BLACK_TAB_COLOR, 2));
        for(int i = 0; i <= countOfPlayers; i++) {
            tabLines.add(new TabLine());
            tabLines.get(i).panels = new ArrayList<>();
            tabLines.get(i).panels.add(new Panel(0, (TAB_LINE_SIZE_Y + 2)*i,
                    TAB_SIZE_NICKNAME_X, TAB_LINE_SIZE_Y));
            for(int j = 0; j < 4; j++) {
                tabLines.get(i).panels.add(new Panel(TAB_SIZE_NICKNAME_X + 2*(j+1) + TAB_SIZE_ICO_X*j, (TAB_LINE_SIZE_Y + 2) * i,
                        TAB_SIZE_ICO_X, TAB_LINE_SIZE_Y));
            }
        }
        for(TabLine tabLine: tabLines) {
            for(Panel panel: tabLine.panels) {
                panel.setStyle(createTabMenuStyle());
                panel.setFocusable(false);
                add(panel);
            }
        }
        TabLine startTabLine = tabLines.get(0);
        /*panels.get(0).get(0).add(createLabel("Nickname", (int)panels.get(0).get(0).getSize().x/2 - 30, 0,
                (int)panels.get(0).get(0).getSize().x, (int)panels.get(0).get(1).getSize().y, BOLD));*/
        startTabLine.createNicknameLabel("Nickname", BOLD);
        addImageViewInCenterOfPanel("kills_icon", startTabLine.panels.get(1));
        addImageViewInCenterOfPanel("deaths_icon", startTabLine.panels.get(2));
        addImageViewInCenterOfPanel("wins_icon", startTabLine.panels.get(3));
        addImageViewInCenterOfPanel("ping_icon", startTabLine.panels.get(4));
        for(int i = 1; i <= countOfPlayers; i++) {
            TabLine currentTabLines = tabLines.get(i);
            currentTabLines.createNicknameLabel("", REGULAR);
            currentTabLines.createNicknameBackground(RED_COLOR);
            currentTabLines.createImageView("dead_icon");
            currentTabLines.createKillsLabelAndColor();
            currentTabLines.createDeathsLabelAndColor();
            currentTabLines.createWinsLabelAndColor();
            currentTabLines.createPingsLabelAndColor();
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

    private void addImageViewToPanel(String name, Panel panel) {
        Texture texture = Manager.getService(SpriteStorage.class).getSprite(name).getTexture();
        FBOImage logoFBOImage = new FBOImage(texture.getId(), texture.getWidth(), texture.getHeight());
        ImageView imageView = new ImageView(logoFBOImage);
        imageView.setStyle(createInvisibleStyle());
        imageView.setSize(texture.getWidth(), texture.getHeight());
        imageView.setPosition(-10, (panel.getSize().y - texture.getHeight())/2);
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
        label.getStyle().setFont(font);
        label.getStyle().setFontSize(LABEL_FONT_SIZE);
        label.getStyle().setTextColor(BLACK_COLOR);
        return label;
    }

    public void fillInTable(List<TabDataLine> data) {
        for(int i = 0; i < data.size(); i++) {
            TabLine currentTabLines = tabLines.get(i+1);
            if(data.get(i).dead)
                currentTabLines.enableImageView();
            else
                currentTabLines.disableImageView();

            currentTabLines.nickname.getTextState().setText(data.get(i).nickname);
            currentTabLines.changeNicknameBackground(data.get(i).color.getVector4f());
            currentTabLines.kills.getTextState().setText(data.get(i).kills.toString());
            currentTabLines.deaths.getTextState().setText(data.get(i).deaths.toString());
            currentTabLines.wins.getTextState().setText(data.get(i).wins.toString());
            currentTabLines.ping.getTextState().setText(data.get(i).ping.toString());
        }
    }
}
