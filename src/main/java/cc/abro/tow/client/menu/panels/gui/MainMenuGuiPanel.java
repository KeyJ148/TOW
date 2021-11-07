package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangePanelGuiEvent;
import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheController;
import cc.abro.orchengine.gameobject.components.gui.ClickChangeToPanelFromCacheGuiEvent;
import cc.abro.tow.client.menu.MenuGuiComponents;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.settings.ClickConfirmController;
import cc.abro.tow.client.menu.panels.events.main.ClickExitGuiEvent;
import org.liquidengine.legui.component.Panel;

import java.util.Set;

import static cc.abro.tow.client.menu.InterfaceStyles.createInvisibleStyle;
import static cc.abro.tow.client.menu.MenuGuiComponents.createMenuPanel;

public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        super(() -> Set.of(new ClickChangeToPanelFromCacheController(), new ClickChangePanelController(), new ClickExitController()));
        Panel panel = createMenuPanel(new MenuGuiComponents.ButtonConfiguration("Create a game", new ClickChangeToPanelFromCacheGuiEvent(CreateGameMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("List of servers", new ClickChangeToPanelFromCacheGuiEvent(ListOfServersMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("Connect via IP", new ClickChangeToPanelFromCacheGuiEvent(ConnectByIPMenuGuiPanel.class)),
                new MenuGuiComponents.ButtonConfiguration("Settings", new ClickChangePanelGuiEvent(() -> new SettingsMenuGuiPanel(),
                        () -> Set.of(new ClickChangeToPanelFromCacheController(), new ClickConfirmController()))),
                new MenuGuiComponents.ButtonConfiguration("Exit", new ClickExitGuiEvent()));
        setSize(panel.getSize());
        setStyle(createInvisibleStyle());
        add(panel);
    }

}
