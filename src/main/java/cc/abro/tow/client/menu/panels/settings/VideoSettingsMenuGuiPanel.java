package cc.abro.tow.client.menu.panels.settings;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.orchengine.services.GuiService;
import cc.abro.tow.client.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.component.SelectBox;
import org.liquidengine.legui.component.TextAreaField;
import org.liquidengine.legui.event.MouseClickEvent;

import static cc.abro.tow.client.menu.InterfaceStyles.*;
import static cc.abro.tow.client.menu.MenuGuiComponents.*;

public class VideoSettingsMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    private final MenuGuiPanel parent;
    //private final SelectBox.SelectBoxElement<boolean> VSync;

    public VideoSettingsMenuGuiPanel(MenuGuiPanel parent) {
        this.parent = parent;
        setSize(SETTINGS_PANEL_WIDTH, SETTINGS_PANEL_HEIGHT);
        setPosition(THICKNESS_OF_PANEL_BORDER, THICKNESS_OF_PANEL_BORDER);
        add(createLabel("Video there", INDENT_X + 10, INDENT_Y + 15, 30, MENU_TEXT_FIELD_HEIGHT));

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

    private boolean isChanged() {
        return false;
    }

    private void addButtonGuiPanelWithUnblockAndBlockFrame(String text) {
        BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
        Panel panel = createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, String leftButton, String rightButton) {
        BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
        Panel panel = createDialogPanel(labelText,
                new ButtonConfiguration(leftButton, getMouseReleaseListener(event -> {
                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                })),
                new ButtonConfiguration(rightButton, getUnblockAndParentDestroyReleaseListener(guiBlock))).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }
}
