package cc.abro.tow.client.menu.panels.controllers;

import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.components.gui.EventableGuiPanelElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.settings.ClickConfirmController;
import cc.abro.tow.client.menu.panels.events.ClickChangePanelGuiEvent;
import cc.abro.tow.client.menu.panels.gui.*;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ClickChangePanelController extends GuiElementController<ClickChangePanelGuiEvent> {

    //TODO move to cache
    public static final Map<Class<? extends EventableGuiPanel>, Supplier<Set<GuiElementController>>> CONTROLLERS = Map.ofEntries(
            Map.entry(SettingsMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickConfirmController())),
            Map.entry(CreateGameMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickCreateController())),
            Map.entry(ConnectMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController())),
            Map.entry(MainMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickExitController())),
            Map.entry(ListOfServersMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController())),
            Map.entry(ConnectByIPMenuGuiPanel.class, () -> Set.of(new ClickChangePanelController(), new ClickConnectController()))
    );

    @Override
    protected Class<ClickChangePanelGuiEvent> getProcessedEventClass() {
        return ClickChangePanelGuiEvent.class;
    }

    @Override
    public void processEvent(ClickChangePanelGuiEvent event) {
        EventableGuiPanel guiPanel = Global.guiPanelStorage.getPanel(event.getNextPanelClass());
        EventableGuiPanelElement<EventableGuiPanel> guiElement =
                new EventableGuiPanelElement<>(guiPanel, CONTROLLERS.get(event.getNextPanelClass()).get());
        getGuiElement().destroyAndCreateGuiElement(guiElement);
    }
}
