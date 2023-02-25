package cc.abro.tow.client.gui.menu.panels;

import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.settings.SettingsMenuGuiPanel;
import org.liquidengine.legui.component.Panel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        Panel panel = MenuGuiComponents.createMenuPanel(
                new MenuGuiComponents.ButtonConfiguration("Create a game",
                        getChangeToCachedPanelReleaseListener(CreateGameMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("List of servers",
                        getChangeToCachedPanelReleaseListener(ListOfServersMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("Connect via IP",
                        getChangeToCachedPanelReleaseListener(ConnectByIPMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("Settings",
                        getChangeToPanelReleaseListener(SettingsMenuGuiPanel::new)),
                new MenuGuiComponents.ButtonConfiguration("Exit",
                        getMouseReleaseListener(() -> getEngine().stop())));
        setSize(panel.getSize());
        setStyle(InterfaceStyles.createInvisibleStyle());
        add(panel);
    }

}
