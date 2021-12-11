package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import cc.abro.orchengine.gui.BlockingGuiPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureLoader;
import cc.abro.tow.client.SettingsStorage;
import cc.abro.tow.client.menu.panels.events.settings.ClickConfirmGuiEvent;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;

import java.awt.image.BufferedImage;
import java.util.List;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.MenuGuiService.createButton;
import static cc.abro.tow.client.menu.panels.gui.SettingsMenuGuiPanel.SETTINGS_PANEL_HEIGHT;
import static cc.abro.tow.client.menu.panels.gui.SettingsMenuGuiPanel.SETTINGS_PANEL_WIDTH;

public class FirstEntryGuiPanel extends BlockingGuiPanel {

    protected final static int FIRST_ENTRY_PANEL_WIDTH = MENU_ELEMENT_WIDTH;
    protected final static int FIRST_ENTRY_PANEL_HEIGHT = 3 * MENU_ELEMENT_HEIGHT;
    protected final static int LENGTH_TEXT_AREA_NICK = 100;
    protected final static int BUTTON_COLOR_SIZE = 15;
    protected final static int PANEL_COLOR_WIDTH = 45;
    protected final static int PANEL_COLOR_HEIGHT = 20;

    private final static Color[] COLORS = {
            new Color(255, 255, 255),
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(255, 255, 0),
            new Color(255, 0, 255),
            new Color(0, 255, 255),
            new Color(0, 125, 255),
            new Color(255, 125, 0),
            new Color(125, 0, 255),
            new Color(125, 125, 0),
    };

    private Color tankColor;

    public FirstEntryGuiPanel(Component parent) {
        super(FIRST_ENTRY_PANEL_WIDTH, FIRST_ENTRY_PANEL_HEIGHT, parent);

        add(createLabel("Nickname:", INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldNickname =
                createTextAreaField(INDENT_X + LABEL_LENGTH_NICKNAME, INDENT_Y, LENGTH_TEXT_AREA_NICK, MENU_TEXT_FIELD_HEIGHT,
                        SettingsStorage.PROFILE.NICKNAME);
        add(textAreaFieldNickname);

        int[] colorFromSettings = SettingsStorage.PROFILE.COLOR;
        tankColor = new Color(colorFromSettings);
        BufferedImage defaultTankImage = Manager.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
        Texture defaultTankTexture = TextureLoader.createTexture(colorizeImage(defaultTankImage, tankColor));
        FBOImage tankFBOImage = new FBOImage(defaultTankTexture.getId(), defaultTankTexture.getWidth(), defaultTankTexture.getHeight());
        ImageView imageView = new ImageView(tankFBOImage);
        imageView.setStyle(createInvisibleStyle());
        addComponent(imageView, 230, 15, defaultTankTexture.getWidth(), defaultTankTexture.getHeight());

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            addColorButton(INDENT_X + (BUTTON_COLOR_SIZE + 2) * i, INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(event -> {
                        tankColor = COLORS[fi];

                        BufferedImage tankImage = Manager.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
                        Texture tankTexture = TextureLoader.createTexture(colorizeImage(tankImage, tankColor));
                        //TODO здесь надо вызывать delete у defaultTankTexture и переопределять defaultTankTexture = tankTexture
                        //TODO при закрытие панели не забыть очистить и tankTexture
                        FBOImage newTankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
                        imageView.setImage(newTankFBOImage);
                    }));
        }

        add(createButton("Back to menu", INDENT_X, SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT, getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class))));
        add(createButton("Confirm", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListenerToNotifyEvents(
                () -> List.of(
                        new ClickConfirmGuiEvent(textAreaFieldNickname.getTextState().getText(), tankColor),
                        new ClickChangeToPanelFromCacheGuiEvent(MainMenuGuiPanel.class)))));
    }

    private Button addColorButton(int x, int y, Color color, MouseClickEventListener event) {
        Button button = new Button("");
        Background background = new Background();
        background.setColor(color.getVector4f());
        button.getStyle().setBackground(background);
        button.getStyle().setBorder(createButtonBorder());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        button.setSize(BUTTON_COLOR_SIZE, BUTTON_COLOR_SIZE);
        button.setPosition(x, y);
        add(button);
        return button;
    }

    //TODO в отдельный сервис по покраске или работе с текстурами
    private BufferedImage colorizeImage(BufferedImage image, Color newColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        Color oldColor = new Color(255, 255, 255, 255);
        for (int p=0; p<pixels.length; p++) {
            if (oldColor.getRGB() == pixels[p]) {
                pixels[p] = newColor.getRGB();
            }
        }
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

}
