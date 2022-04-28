package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
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
import org.liquidengine.legui.component.ImageView;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.image.FBOImage;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.createDialogPanel;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.CANT_SAVE_SETTINGS;
import static cc.abro.tow.client.menu.panels.FirstEntryGuiPanel.Error.NICKNAME_IS_EMPTY;

public class SoundSettingsMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    private final MenuGuiPanel parent;

    public Function<Panel, Boolean> canOut;

    public SoundSettingsMenuGuiPanel(MenuGuiPanel parent) {
        this.parent = parent;
        setSize(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        setPosition(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_PANEL_BORDER);
        add(createLabel("Sounds here", INDENT_X + 10, INDENT_Y + 15, 30, MENU_TEXT_FIELD_HEIGHT));

        final int BUTTON_INDENT_X = (SETTINGS_PANEL_WIDTH - BUTTON_WIDTH*3)/5;

        add(createButton("Back to menu", BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    if(false) {
                        addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes. Are you sure you want to go back?", "Yes", "No");
                    } else {
                        parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    }
                })));
        add(createButton("Save & back", SETTINGS_PANEL_WIDTH - (BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> saveChanges(event, true))));

        add(createButton("Save", SETTINGS_PANEL_WIDTH - BUTTON_WIDTH - BUTTON_INDENT_X,
                SETTINGS_PANEL_HEIGHT - BUTTON_HEIGHT - INDENT_Y,
                BUTTON_WIDTH, BUTTON_HEIGHT,
                getMouseReleaseListener(event -> saveChanges(event, false))));
    }

    private void saveChanges(MouseClickEvent<?> event, boolean getBack) {
        if(getBack)
                parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
    }

    private void addButtonGuiPanelWithUnblockAndBlockFrame(String text) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, String leftButton, String rightButton) {
        BlockingGuiService.GuiBlock guiBlock = Context.getService(BlockingGuiService.class).createGuiBlock(getFrame().getContainer());
        Panel panel = createDialogPanel(labelText,
                new ButtonConfiguration(leftButton, getMouseReleaseListener(event -> {
                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                })),
                new ButtonConfiguration(rightButton, getUnblockAndParentDestroyReleaseListener(guiBlock))).panel();
        Context.getService(GuiService.class).moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }
}
