package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
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

public class FirstEntryGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    protected static final int FIRST_ENTRY_PANEL_WIDTH = (4 * InterfaceStyles.MENU_ELEMENT_WIDTH / 3) + 1;
    protected static final int FIRST_ENTRY_PANEL_HEIGHT = (5 * InterfaceStyles.MENU_ELEMENT_HEIGHT / 2) + 1;
    protected static final int LENGTH_TEXT_AREA_NICK = 100;
    protected static final int BUTTON_COLOR_SIZE = 15;
    protected static final int PANEL_COLOR_WIDTH = 45;
    protected static final int PANEL_COLOR_HEIGHT = 20;
    protected static final int LABEL_LENGTH_CHAPTER = 310;
    protected static final int INDENT_PLUS_Y = 15*2 + InterfaceStyles.LABEL_HEIGHT_CHAPTER;
    protected static final int INDENT_PLUS_TANK_Y = 20 + InterfaceStyles.LABEL_HEIGHT_CHAPTER;
    protected static final int INDENT_PLUS_X = InterfaceStyles.INDENT_X + 15;
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
    private Texture tankTexture;

    public FirstEntryGuiPanel() {
        guiService = getGuiService();
        settingsService = Context.getService(SettingsService.class);
        setSize(FIRST_ENTRY_PANEL_WIDTH, FIRST_ENTRY_PANEL_HEIGHT);

        add(MenuGuiComponents.createLargerLabel("Choose your nickname and color",
                (FIRST_ENTRY_PANEL_WIDTH - LABEL_LENGTH_CHAPTER)/2, 15, LABEL_LENGTH_CHAPTER, InterfaceStyles.LABEL_HEIGHT_CHAPTER));

        add(MenuGuiComponents.createLabel("Nickname:", INDENT_PLUS_X, INDENT_PLUS_Y, 30, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldNickname =
                MenuGuiComponents.createTextAreaField(INDENT_PLUS_X + InterfaceStyles.LABEL_LENGTH_NICKNAME, INDENT_PLUS_Y, LENGTH_TEXT_AREA_NICK, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT,
                        settingsService.getSettings().getProfile().getNickname());
        add(textAreaFieldNickname);

        int[] colorFromSettings = settingsService.getSettings().getProfile().getColor();
        tankColor = new Color(colorFromSettings);
        BufferedImage defaultTankImage = getSpriteStorage().getSprite("sys_tank").texture().getImage();
        tankTexture = getTextureService().createTexture(colorizeImage(defaultTankImage, tankColor));
        FBOImage tankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
        ImageView imageView = new ImageView(tankFBOImage);
        imageView.setStyle(InterfaceStyles.createInvisibleStyle());
        imageView.setFocusable(false);
        addComponent(imageView, INDENT_PLUS_TANK_X, INDENT_PLUS_TANK_Y, tankTexture.getWidth(), tankTexture.getHeight());

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            addColorButton(INDENT_PLUS_X + (BUTTON_COLOR_SIZE + 2) * i, INDENT_PLUS_Y + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(() -> {
                        tankColor = COLORS[fi];
                        BufferedImage tankImage = getSpriteStorage().getSprite("sys_tank").texture().getImage();
                        getTextureService().deleteTexture(tankTexture.getId());
                        tankTexture = getTextureService().createTexture(colorizeImage(tankImage, tankColor));
                        FBOImage newTankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
                        imageView.setImage(newTankFBOImage);
                    }));
        }

        add(MenuGuiComponents.createButton("Confirm", (FIRST_ENTRY_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH)/2,
                FIRST_ENTRY_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y, InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
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
        BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
        Panel panel = MenuGuiComponents.createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(panel);
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
        button.getStyle().setBorder(InterfaceStyles.createButtonBorder());
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
