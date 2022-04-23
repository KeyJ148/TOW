package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.gui.tabpanel.TabPanel;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.SettingsStorage;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import cc.abro.tow.client.services.SettingsService;
import org.liquidengine.legui.component.Button;
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;
import org.liquidengine.legui.listener.MouseClickEventListener;
import org.liquidengine.legui.style.Background;
import org.liquidengine.legui.style.border.SimpleLineBorder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.InterfaceStyles.BUTTON_HEIGHT;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.createButton;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;

public class PlayerSettingsMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

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

    private Color tankColor;

    public Function<Panel, Boolean> canOut;

    public PlayerSettingsMenuGuiPanel(MenuGuiPanel parent, TabPanel tabPanel) {
        setSize(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        setPosition(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_PANEL_BORDER);
        add(createLabel("Nickname:", INDENT_X, INDENT_Y, 30, MENU_TEXT_FIELD_HEIGHT));
        TextAreaField textAreaFieldNickname =
                createTextAreaField(INDENT_X + LABEL_LENGTH_NICKNAME, INDENT_Y, LENGTH_TEXT_AREA_NICK, MENU_TEXT_FIELD_HEIGHT,
                        SettingsStorage.PROFILE.NICKNAME);
        add(textAreaFieldNickname);

        int[] colorFromSettings = SettingsStorage.PROFILE.COLOR;
        tankColor = new Color(colorFromSettings);
        BufferedImage defaultTankImage = Context.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
        Texture defaultTankTexture = Context.getService(TextureService.class).createTexture(colorizeImage(defaultTankImage, tankColor));
        FBOImage tankFBOImage = new FBOImage(defaultTankTexture.getId(), defaultTankTexture.getWidth(), defaultTankTexture.getHeight());
        ImageView imageView = new ImageView(tankFBOImage);
        imageView.setStyle(createInvisibleStyle());
        imageView.setPosition(230, 15);
        imageView.setSize(defaultTankTexture.getWidth(), defaultTankTexture.getHeight());
        add(imageView);

        for (int i = 0; i < COLORS.length; i++) {
            final int fi = i;
            addColorButton(INDENT_X + (BUTTON_COLOR_SIZE + 2) * i, INDENT_Y + MENU_TEXT_FIELD_HEIGHT + 10, COLORS[i],
                    getMouseReleaseListener(event -> {
                        changeTankColor(COLORS[fi], imageView);
                    }));
        }

        final int BUTTON_INDENT_X = (SETTINGS_PANEL_WIDTH - BUTTON_WIDTH*3)/5;

        add(createButton("Back to menu", BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
                    if((tankColor.getRGB() != new Color(SettingsStorage.PROFILE.COLOR).getRGB()) || !(textAreaFieldNickname.getTextState().getText().equals(SettingsStorage.PROFILE.NICKNAME))) {
                        addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                                new ButtonConfiguration("Back to menu", getMouseReleaseListener(buttonEvent -> {
                                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new ButtonConfiguration("Save & back", getMouseReleaseListener(buttonEvent -> {
                                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(buttonEvent);
                                    saveChanges(textAreaFieldNickname.getTextState().getText());
                                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(buttonEvent);
                                })),
                                new ButtonConfiguration("Return editing", getUnblockAndParentDestroyReleaseListener(guiBlock)));
                    } else {
                        parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    }
                })));
        add(createButton("Save & back", SETTINGS_PANEL_WIDTH - (BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    saveChanges(textAreaFieldNickname.getTextState().getText());
                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                })));

        add(createButton("Save", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> saveChanges(textAreaFieldNickname.getTextState().getText()))));

        canOut = (to -> {
            if((tankColor.getRGB() != new Color(SettingsStorage.PROFILE.COLOR).getRGB()) || !(textAreaFieldNickname.getTextState().getText().equals(SettingsStorage.PROFILE.NICKNAME))) {
                BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
                addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes.",
                        new ButtonConfiguration("Switch without saving", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            changeTankColor(new Color(SettingsStorage.PROFILE.COLOR), imageView);
                            textAreaFieldNickname.getTextState().setText(SettingsStorage.PROFILE.NICKNAME);
                            tabPanel.setActivePanelFromTiedPair(tabPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Save changes & switch", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                            saveChanges(textAreaFieldNickname.getTextState().getText());
                            tabPanel.setActivePanelFromTiedPair(tabPanel.getTideButtonPanel(to));
                        }),
                        new ButtonConfiguration("Don't switch", event -> {
                            getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                        }));
                return false;
            } else {
                return true;
            }
        });

    }

    private void saveChanges(String nickname) {
        try {
            Context.getService(SettingsService.class).setSettings(nickname, tankColor);
        } catch (SettingsService.EmptyNicknameException e) {
            addButtonGuiPanelWithUnblockAndBlockFrame(NICKNAME_IS_EMPTY.getText());
        } catch (SettingsService.CantSaveSettingException e) {
            addButtonGuiPanelWithUnblockAndBlockFrame(CANT_SAVE_SETTINGS.getText());
        }
    }

    private void addButtonGuiPanelWithUnblockAndBlockFrame(String text) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, ButtonConfiguration... buttonConfigurations) {
        Panel panel = createDialogPanel(labelText, buttonConfigurations).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addColorButton(int x, int y, Color color, MouseClickEventListener event) {
        Button button = new Button("");
        Background background = new Background();
        background.setColor(color.getVector4f());
        button.getStyle().setBackground(background);
        button.getStyle().setBorder(createButtonBorder());
        button.getListenerMap().addListener(MouseClickEvent.class, event);
        button.setSize(BUTTON_COLOR_SIZE, BUTTON_COLOR_SIZE);
        button.setPosition(x, y);
        add(button);
    }

    private void changeTankColor(Color color, ImageView imageView) {
        tankColor = color;
        BufferedImage tankImage = Context.getService(SpriteStorage.class).getSprite("sys_tank").getTexture().getImage();
        Texture tankTexture = Context.getService(TextureService.class).createTexture(colorizeImage(tankImage, tankColor));
        //TODO здесь надо вызывать delete у defaultTankTexture и переопределять defaultTankTexture = tankTexture
        //TODO при закрытие панели не забыть очистить и tankTexture
        FBOImage newTankFBOImage = new FBOImage(tankTexture.getId(), tankTexture.getWidth(), tankTexture.getHeight());
        imageView.setImage(newTankFBOImage);
    }

    //TODO в отдельный сервис по покраске или работе с текстурами
    private BufferedImage colorizeImage(BufferedImage image, Color newColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        Color oldColor = new Color(255, 255, 255, 255);
        for (int p=0; p<pixels.length; p++) {
            if (oldColor.getRGB() == pixels[p]){
                pixels[p] = newColor.getRGB();
            }
        }
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }
}
