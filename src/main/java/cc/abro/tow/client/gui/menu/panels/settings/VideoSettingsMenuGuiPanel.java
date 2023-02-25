package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.gui.MouseReleaseBlockingListeners;
import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.gui.menu.panels.MenuGuiPanel;
import org.liquidengine.legui.component.Panel;
import org.liquidengine.legui.event.MouseClickEvent;

public class VideoSettingsMenuGuiPanel extends MenuGuiPanel implements MouseReleaseBlockingListeners {

    private final MenuGuiPanel parent;
    //private final SelectBox.SelectBoxElement<boolean> VSync;

    public VideoSettingsMenuGuiPanel(MenuGuiPanel parent) {
        this.parent = parent;
        setSize(InterfaceStyles.SETTINGS_PANEL_WIDTH, InterfaceStyles.SETTINGS_PANEL_HEIGHT);
        setPosition(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_PANEL_BORDER);
        add(MenuGuiComponents.createLabel("Video there", InterfaceStyles.INDENT_X + 10, InterfaceStyles.INDENT_Y + 15, 30, InterfaceStyles.MENU_TEXT_FIELD_HEIGHT));

        final int BUTTON_INDENT_X = (InterfaceStyles.SETTINGS_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH*3)/5;

        add(MenuGuiComponents.createButton("Back to menu", BUTTON_INDENT_X,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(event -> {
                    if(false) {
                        addDialogGuiPanelWithUnblockAndBlockFrame("You have unsaved changes. Are you sure you want to go back?", "Yes", "No");
                    } else {
                        parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                    }
                })));
        add(MenuGuiComponents.createButton("Save & back", InterfaceStyles.SETTINGS_PANEL_WIDTH - (InterfaceStyles.BUTTON_WIDTH + BUTTON_INDENT_X) * 2,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
                getMouseReleaseListener(event -> saveChanges(event, true))));

        add(MenuGuiComponents.createButton("Save", InterfaceStyles.SETTINGS_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH - BUTTON_INDENT_X,
                InterfaceStyles.SETTINGS_PANEL_HEIGHT - InterfaceStyles.BUTTON_HEIGHT - InterfaceStyles.INDENT_Y,
                InterfaceStyles.BUTTON_WIDTH, InterfaceStyles.BUTTON_HEIGHT,
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
        Panel panel = MenuGuiComponents.createButtonPanel(text, "OK", getUnblockAndParentDestroyReleaseListener(guiBlock)).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }

    private void addDialogGuiPanelWithUnblockAndBlockFrame(String labelText, String leftButton, String rightButton) {
        BlockingGuiService.GuiBlock guiBlock = getBlockingGuiService().createGuiBlock(getFrame().getContainer());
        Panel panel = MenuGuiComponents.createDialogPanel(labelText,
                new MenuGuiComponents.ButtonConfiguration(leftButton, getMouseReleaseListener(event -> {
                    getUnblockAndParentDestroyReleaseListener(guiBlock).process(event);
                    parent.getChangeToCachedPanelReleaseListener(MainMenuGuiPanel.class).process(event);
                })),
                new MenuGuiComponents.ButtonConfiguration(rightButton, getUnblockAndParentDestroyReleaseListener(guiBlock))).panel();
        getGuiService().moveComponentToWindowCenter(panel);
        getFrame().getContainer().add(panel);
    }
}
