package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.MenuGuiPanel;
import cc.abro.tow.client.settings.Settings;
import cc.abro.tow.client.settings.SettingsService;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.KeyEvent;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import static cc.abro.tow.client.gui.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.gui.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;

public class PlayerSettingsMenuGuiPanel extends MenuGuiPanel implements SaveBackLogicInterface {

    protected final static int LENGTH_TEXT_AREA_NICK = 100;
    protected final static int BUTTON_COLOR_SIZE = 15;

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
    private SettingsMenuGuiPanel parent;
    private Color tankColor;
    private Texture tankTexture;
    private final TextAreaField textAreaFieldNickname;
    private final Settings settings;
    private final Button saveButton;
    private final Button saveAndBackButton;
    ImageView imageView;

    public Function<Panel, Boolean> canOut;

    public PlayerSettingsMenuGuiPanel(SettingsMenuGuiPanel parent, TabPanel tabPanel) {
        this.parent = parent;
        settings = Context.getService(SettingsService.class).getSettings();
        setSize(InterfaceStyles.SETTINGS_PANEL_WIDTH, InterfaceStyles.SETTINGS_PANEL_HEIGHT);
        setPosition(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_PANEL_BORDER);
        add(MenuGuiComponents.createLabel("Nickname:", InterfaceStyles.INDENT_X, InterfaceStyles.INDENT_Y, 30, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));
        textAreaFieldNickname =
                MenuGuiComponents.createTextAreaField(InterfaceStyles.INDENT_X + InterfaceStyles.LABEL_LENGTH_NICKNAME, InterfaceStyles.INDENT_Y, LENGTH_TEXT_AREA_NICK, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT,
                        settings.getProfile().getNickname());
        add(textAreaFieldNickname);

        int[] colorFromSettings = settings.getProfile().getColor();
        tankColor = new Color(colorFromSettings);
        BufferedImage defaultTankImage = getSpriteStorage().getSprite("sys_tank").texture().getImage();
        BufferedImage colorizedTankImage = getTextureService().colorizeImage(defaultTankImage, tankColor);
        tankTexture = getTextureService().createTexture(colorizedTankImage);
        FBOImage tankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
        imageView = new ImageView(tankFBOImage);
        imageView.setStyle(InterfaceStyles.createInvisibleStyle());
        imageView.setPosition(230, 15);
        imageView.setSize(tankTexture.getWidth(), tankTexture.getHeight());
        add(imageView);

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            addColorButton(InterfaceStyles.INDENT_X + (BUTTON_COLOR_SIZE + 2) * i, InterfaceStyles.INDENT_Y + InterfaceStyles.MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(event -> {
                        changeTankColor(COLORS[fi], imageView);
                    }));
        }

        add(parent.createBackToMenuButton(this));
        saveAndBackButton = parent.createSaveAndBackButton(this);
        add(saveAndBackButton);
        saveButton = parent.createSaveButton(this);
        add(saveButton);

        canOut = parent.createCanOut(this);

        textAreaFieldNickname.getListenerMap().addListener(KeyEvent.class, event -> {
            changeSaveButtons();
        });

        changeSaveButtons();

    }

    public void clearChanges() {
        changeTankColor(new Color(settings.getProfile().getColor()), imageView);
        textAreaFieldNickname.getTextState().setText(settings.getProfile().getNickname());
    }

    public void changeSaveButtons() {
        boolean changed = isChanged();
        saveButton.setFocusable(changed);
        saveAndBackButton.setFocusable(changed);
        if(changed) {
            saveButton.setStyle(InterfaceStyles.createButtonStyle());
            saveAndBackButton.setStyle(InterfaceStyles.createButtonStyle());
        }
        else
        {
            saveButton.setStyle(InterfaceStyles.createBlockedButtonStyle());
            saveAndBackButton.setStyle(InterfaceStyles.createBlockedButtonStyle());
            saveButton.setHovered(false);
            saveAndBackButton.setHovered(false);
        }
    }

    public void saveChanges() {
        try {
            Context.getService(SettingsService.class).setProfileSettings(textAreaFieldNickname.getTextState().getText(), tankColor);
            changeSaveButtons();
        } catch (SettingsService.EmptyNicknameException e) {
            parent.addButtonGuiPanelWithUnblockAndBlockFrame(NICKNAME_IS_EMPTY.getText(), this);
        } catch (SettingsService.CantSaveSettingException e) {
            parent.addButtonGuiPanelWithUnblockAndBlockFrame(CANT_SAVE_SETTINGS.getText(), this);
        }
    }

    public boolean isChanged() {
        return (tankColor.getRGB() != new Color(settings.getProfile().getColor()).getRGB() ||
                !(textAreaFieldNickname.getTextState().getText().equals(settings.getProfile().getNickname())));
    }

    private void addColorButton(int x, int y, Color color, MouseClickEventListener event) {
        Button button = new Button("");
        Background background = new Background();
        background.setColor(color.getVector4f());
        button.getStyle().setBackground(background);
        button.getStyle().setBorder(InterfaceStyles.createButtonBorder());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        button.setSize(BUTTON_COLOR_SIZE, BUTTON_COLOR_SIZE);
        button.setPosition(x, y);
        add(button);
    }

    private void changeTankColor(Color color, ImageView imageView) {
        tankColor = color;
        BufferedImage tankImage = getSpriteStorage().getSprite("sys_tank").texture().getImage();
        getTextureService().deleteTexture(tankTexture.getId());
        BufferedImage colorizedTankImage = getTextureService().colorizeImage(tankImage, tankColor);
        tankTexture = getTextureService().createTexture(colorizedTankImage);
        FBOImage newTankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
        imageView.setImage(newTankFBOImage);
        changeSaveButtons();
    }
}
