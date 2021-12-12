package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.cycle.Engine;
import org.liquidengine.legui.component.Panel;

import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;
import static cc.abro.tow.client.menu.MenuGuiComponents.ButtonConfiguration;
import static cc.abro.tow.client.menu.MenuGuiComponents.createMenuPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        Panel panel = createMenuPanel(
                new ButtonConfiguration("Create a game",
                        getChangeToCachedPanelReleaseListener(CreateGameMenuGuiPanel.class)),
                new ButtonConfiguration("List of servers",
                        getChangeToCachedPanelReleaseListener(ListOfServersMenuGuiPanel.class)),
                new ButtonConfiguration("Connect via IP",
                        getChangeToCachedPanelReleaseListener(ConnectByIPMenuGuiPanel.class)),
                new ButtonConfiguration("Settings",
                        getChangeToPanelReleaseListener(SettingsMenuGuiPanel::new)),
                new ButtonConfiguration("Exit",
                        getMouseReleaseListener(() -> Manager.getService(Engine.class).stop())));
        setSize(panel.getSize());
        setStyle(createInvisibleStyle());
        add(panel);
    }

}
