package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;
import org.liquidengine.legui.component.Panel;

import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;
import static cc.abro.tow.client.menu.MenuGuiService.ButtonConfiguration;
import static cc.abro.tow.client.menu.MenuGuiService.createMenuPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        super();
        Panel panel = createMenuPanel(
                new ButtonConfiguration("Create a game",
                        getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(CreateGameMenuGuiPanel.class))),
                new ButtonConfiguration("List of servers",
                        getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(ListOfServersMenuGuiPanel.class))),
                new ButtonConfiguration("Connect via IP",
                        getMouseReleaseListenerToNotify(() -> new ClickChangeToPanelFromCacheGuiEvent(ConnectByIPMenuGuiPanel.class))),
                new ButtonConfiguration("Settings",
                        getMouseReleaseListenerToNotify(() -> new ClickChangePanelGuiEvent(SettingsMenuGuiPanel::new))),
                new ButtonConfiguration("Exit",
                        getMouseReleaseListenerToNotify(ClickExitGuiEvent::new)));
        setSize(panel.getSize());
        setStyle(createInvisibleStyle());
        add(panel);
    }

}
