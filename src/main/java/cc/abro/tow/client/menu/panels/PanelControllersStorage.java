package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gui.EventableGuiPanel;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Класс кеширующий готовые панели меню, чтобы сохранять все вносимые изменения в интерфейс
 */
@Log4j2
public class PanelControllersStorage {

    private final Map<Class<? extends EventableGuiPanel>, Supplier<Set<GuiElementController>>> controllersByPanel = new HashMap<>();

    public void registry(Class<? extends EventableGuiPanel> panelClass,
                         Supplier<Set<GuiElementController>> controllersSupplier) {
        if (controllersByPanel.containsKey(panelClass)) {
            log.error("Panel class \"" + panelClass + "\" already exists");
            throw new IllegalStateException("Panel class \"" + panelClass + "\" already exists");
        }
        controllersByPanel.put(panelClass, controllersSupplier);
    }

    public Set<GuiElementController> getControllers(Class<? extends EventableGuiPanel> panelClass) {
        if (!controllersByPanel.containsKey(panelClass)) {
            log.error("Panel class \"" + panelClass + "\" not found");
            throw new IllegalStateException("Controllers for panel \"" + panelClass + "\" not found");
        }

        return controllersByPanel.get(panelClass).get();
    }
}
/*
    public static final Map<Class<? extends EventableGuiPanel>, Supplier<Set<GuiElementController>>> CONTROLLERS_BY_PANEL = Map.ofEntries(
            Map.entry(SettingsMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickConfirmController())),
            Map.entry(CreateGameMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickCreateController())),
            Map.entry(ConnectMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController())),
            Map.entry(MainMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickExitController())),
            Map.entry(ListOfServersMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController())),
            Map.entry(ConnectByIPMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickConnectController()))
    );
*/