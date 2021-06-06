package cc.abro.tow.client.menu.panels.gui;

import cc.abro.tow.client.menu.panels.controllers.connect.ClickBackController;
import cc.abro.tow.client.menu.panels.controllers.connect.ClickConnectByIpController;
import cc.abro.tow.client.menu.panels.controllers.connect.ClickListOfServersController;

public class ConnectMenuGuiPanel extends MenuGuiPanel {

    public ConnectMenuGuiPanel() {
        init();
        addMenuButtons(
                new ButtonConfiguration("Connect via IP", () -> ClickConnectByIpController.class),
                new ButtonConfiguration("List of servers", () -> ClickListOfServersController.class),
                new ButtonConfiguration("Back to menu", () -> ClickBackController.class)
        );

    }
}
