package cc.abro.tow.client.menu.panels.controllers.main;

import cc.abro.tow.client.menu.panels.controllers.MenuClickController;
import cc.abro.tow.client.menu.panels.events.main.ClickCloseChildPanelGuiEvent;

public class CloseChildPanelController extends MenuClickController<ClickCloseChildPanelGuiEvent> {

    @Override
    protected Class<ClickCloseChildPanelGuiEvent> getProcessedEventClass() {
        return ClickCloseChildPanelGuiEvent.class;
    }

    @Override
    public void processEvent(ClickCloseChildPanelGuiEvent event) {
        event.getComponentsToFocus().iterator().forEachRemaining(c -> c.setFocusable(true));
        getGuiElement().destroy();
    }
}