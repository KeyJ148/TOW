package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.controllers.ClickChangePanelController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;

import java.util.Set;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init(() -> Set.of(new ClickChangePanelController()));
        addMenuButtons(
                new ButtonConfiguration("Connect via IP", new ClickChangePanelGuiEvent(ConnectByIPMenuGuiPanel.class)),
                new ButtonConfiguration("List of servers", new ClickChangePanelGuiEvent(ListOfServersMenuGuiPanel.class)),
                new ButtonConfiguration("Back to menu", new ClickChangePanelGuiEvent(MainMenuGuiPanel.class))
        );

    }
}
