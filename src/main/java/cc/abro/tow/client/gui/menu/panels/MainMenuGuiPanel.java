package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gui.StoredGuiPanel;
import cc.abro.orchengine.resources.locales.LocalizationService;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import cc.abro.tow.client.gui.menu.MenuGuiComponents;
import cc.abro.tow.client.gui.menu.panels.settings.SettingsMenuGuiPanel;
import com.spinyowl.legui.component.Panel;

@StoredGuiPanel
public class MainMenuGuiPanel extends MenuGuiPanel {

    public MainMenuGuiPanel() {
        Panel panel = MenuGuiComponents.createMenuPanel(
                new MenuGuiComponents.ButtonConfiguration(getLocalizationService().localize("game.create"),
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
