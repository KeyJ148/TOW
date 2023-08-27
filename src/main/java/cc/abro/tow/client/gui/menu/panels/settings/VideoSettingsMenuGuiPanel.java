package cc.abro.tow.client.gui.menu.panels.settings;

import cc.abro.orchengine.services.BlockingGuiService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.MainMenuGuiPanel;
import cc.abro.tow.client.gui.menu.panels.MenuGuiPanel;
import com.spinyowl.legui.component.Button;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.component.SelectBox;
import org.lwjgl.glfw.GLFWVidMode;

import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoModes;

public class VideoSettingsMenuGuiPanel extends MenuGuiPanel implements SaveBackLogicInterface {

    private final MenuGuiPanel parent;
    private final SelectBox<String> resolution;

    public Function<Panel, Boolean> canOut;
    private final Button saveButton;
    private final Button saveAndBackButton;

    public VideoSettingsMenuGuiPanel(SettingsMenuGuiPanel parent) {

        final int WIDTH_OF_RESOLUTION_FIELD = 200;
        final int HEIGHT_OF_RESOLUTION_FIELD = InterfaceStyles.BUTTON_HEIGHT;
        final int BUTTON_INDENT_X = (InterfaceStyles.SETTINGS_PANEL_WIDTH - InterfaceStyles.BUTTON_WIDTH*3)/5;

        this.parent = parent;
        setSize(InterfaceStyles.SETTINGS_PANEL_WIDTH, InterfaceStyles.SETTINGS_PANEL_HEIGHT);
        setPosition(InterfaceStyles.THICKNESS_OF_PANEL_BORDER, InterfaceStyles.THICKNESS_OF_PANEL_BORDER);

        GLFWVidMode.Buffer vidModes = glfwGetVideoModes(glfwGetPrimaryMonitor());
        resolution = new SelectBox<>(InterfaceStyles.INDENT_X + 10, InterfaceStyles.INDENT_Y + 15, WIDTH_OF_RESOLUTION_FIELD, HEIGHT_OF_RESOLUTION_FIELD);

        if(vidModes != null) {
            for (GLFWVidMode vidMode : vidModes) {
                resolution.addElement(vidMode.width() + "x" + vidMode.height());
            }
        }

        resolution.setVisibleCount(5);
        resolution.setElementHeight(InterfaceStyles.MENU_TEXT_FIELD_HEIGHT);
        resolution.getSelectionListPanel().setStyle(InterfaceStyles.createScrollablePanelStyle());
        resolution.getSelectionListPanel().getVerticalScrollBar().setStyle(InterfaceStyles.createScrollBarStyle());
        //resolution.getExpandButton().setStyle(InterfaceStyles.createButtonStyle());
        //resolution.getSelectionButton().setStyle(InterfaceStyles.createButtonStyle());
        //for (SelectBox<String>.SelectBoxElement<String> boxElement :resolution.getSelectBoxElements()) {
            //boxElement.setStyle(InterfaceStyles.createButtonStyle());
        //}
        //TODO return after release add(resolution);

        add(parent.createBackToMenuButton(this));
        canOut = parent.createCanOut(this);

        saveAndBackButton = parent.createSaveAndBackButton(this);
        add(saveAndBackButton);

        saveButton = parent.createSaveButton(this);
        add(saveButton);
        changeSaveButtons();
    }

    public void saveChanges() {
        changeSaveButtons();
    }
    public void clearChanges() {
    }

    public boolean isChanged() {
        return false;
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
}
