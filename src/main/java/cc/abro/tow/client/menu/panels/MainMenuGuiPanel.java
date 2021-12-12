package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Engine;
import org.liquidengine.legui.component.Panel;

import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;
import static cc.abro.tow.client.menu.MenuGuiComponents.MenuButtonConfiguration;
import static cc.abro.tow.client.menu.MenuGuiComponents.createMenuPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        Panel panel = createMenuPanel(
                new MenuButtonConfiguration("Create a game",
                        getChangeToCachedPanelReleaseListener(CreateGameMenuGuiPanel.class)),
                new MenuButtonConfiguration("List of servers",
                        getChangeToCachedPanelReleaseListener(ListOfServersMenuGuiPanel.class)),
                new MenuButtonConfiguration("Connect via IP",
                        getChangeToCachedPanelReleaseListener(ConnectByIPMenuGuiPanel.class)),
                new MenuButtonConfiguration("Settings",
                        getChangeToPanelReleaseListener(SettingsMenuGuiPanel::new)),
                new MenuButtonConfiguration("Exit",
                        getMouseReleaseListener(() -> Manager.getService(Engine.class).stop())));
        setSize(panel.getSize());
        setStyle(createInvisibleStyle());
        add(panel);
    }

}
