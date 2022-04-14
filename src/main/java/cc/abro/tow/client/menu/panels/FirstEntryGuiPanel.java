package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.settings.SettingsService;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;

import java.awt.image.BufferedImage;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class FirstEntryGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected static final int FIRST_ENTRY_PANEL_WIDTH = (4 * MENU_ELEMENT_WIDTH / 3) + 1;
    protected static final int FIRST_ENTRY_PANEL_HEIGHT = (5 * MENU_ELEMENT_HEIGHT / 2) + 1;
    protected static final int LENGTH_TEXT_AREA_NICK = 100;
    protected static final int BUTTON_COLOR_SIZE = 15;
    protected static final int PANEL_COLOR_WIDTH = 45;
    protected static final int PANEL_COLOR_HEIGHT = 20;
    protected static final int LABEL_LENGTH_CHAPTER = 310;
    protected static final int INDENT_PLUS_Y = 15*2 + LABEL_HEIGHT_CHAPTER;
    protected static final int INDENT_PLUS_TANK_Y = 20 + LABEL_HEIGHT_CHAPTER;
    protected static final int INDENT_PLUS_X = INDENT_X + 15;
    protected static final int INDENT_PLUS_TANK_X = 230 + 15;
    
    private final GuiService guiService;
    private final SettingsService settingsService;

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

    public FirstEntryGuiPanel() {
        guiService = Context.getService(GuiService.class);
        settingsService = Context.getService(SettingsService.class);
        setSize(FIRST_ENTRY_PANEL_WIDTH, FIRST_ENTRY_PANEL_HEIGHT);

        add(createLargerLabel("Choose your nickname and color",
                (FIRST_ENTRY_PANEL_WIDTH - LABEL_LENGTH_CHAPTER)/2, 15, LABEL_LENGTH_CHAPTER, LABEL_HEIGHT_CHAPTER));

        add(createLabel("Nickname:", INDENT_PLUS_X, INDENT_PLUS_Y, 30, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldNickname =
                createTextAreaField(INDENT_PLUS_X + LABEL_LENGTH_NICKNAME, INDENT_PLUS_Y, LENGTH_TEXT_AREA_NICK, MENU_TEXT_FIELD_HEIGHT,
                        settingsService.getSettings().getProfile().getNickname());
        add(textAreaFieldNickname);

        int[] colorFromSettings = settingsService.getSettings().getProfile().getColor();
        tankColor = new Color(colorFromSettings);
        BufferedImage defaultTankImage = Context.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
        Texture defaultTankTexture = Context.getService(TextureService.class).createTexture(colorizeImage(defaultTankImage, tankColor));
        FBOImage tankFBOImage = new FBOImage(defaultTankTexture.getId(), defaultTankTexture.getWidth(), defaultTankTexture.getHeight());
        ImageView imageView = new ImageView(tankFBOImage);
        imageView.setStyle(createInvisibleStyle());
        imageView.setFocusable(false);
        addComponent(imageView, INDENT_PLUS_TANK_X, INDENT_PLUS_TANK_Y, defaultTankTexture.getWidth(), defaultTankTexture.getHeight());

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            addColorButton(INDENT_PLUS_X + (BUTTON_COLOR_SIZE + 2) * i, INDENT_PLUS_Y + MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(() -> {
                        tankColor = COLORS[fi];

                        BufferedImage tankImage = Context.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
                        Texture tankTexture = Context.getService(TextureService.class).createTexture(colorizeImage(tankImage, tankColor));
                        //TODO здесь надо вызывать delete у defaultTankTexture и переопределять defaultTankTexture = tankTexture
                        //TODO при закрытие панели не забыть очистить и tankTexture
                        FBOImage newTankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
                        imageView.setImage(newTankFBOImage);
                    }));
        }

        add(createButton("Confirm", (FIRST_ENTRY_PANEL_WIDTH - BUTTON_WIDTH)/2,
                FIRST_ENTRY_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y, BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    try {
                        settingsService.setProfileSettings(textAreaFieldNickname.getTextState().getText(), tankColor);
                        getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    } catch (SettingsService.EmptyNicknameException e) {
                        addButtonGuiPanelWithUnblockAndBlockFrame(Error.NICKNAME_IS_EMPTY.getText());
                    } catch (SettingsService.CantSaveSettingException e) {
                        addButtonGuiPanelWithUnblockAndBlockFrame(Error.CANT_SAVE_SETTINGS.getText());
                    }
                })));
    }

    private void addButtonGuiPanelWithUnblockAndBlockFrame(String text) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    //TODO
    public enum Error {
        NICKNAME_IS_EMPTY("ERROR: Nickname is empty"),
        WRONG_LETTERS_IN_NICKNAME("ERROR: Wrong letters in nickname"),
        CANT_SAVE_SETTINGS("ERROR: Settings can't be saved"),
        UNKNOWN("ERROR: Something went wrong");

        private final String text;

        Error(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
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
