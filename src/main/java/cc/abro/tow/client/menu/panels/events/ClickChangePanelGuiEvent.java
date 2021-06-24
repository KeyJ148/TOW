package cc.abro.tow.client.menu.panels.events;

import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.tow.client.menu.panels.controllers.ClickChangePanelController;
import cc.abro.tow.client.menu.panels.controllers.connectbyip.ClickConnectController;
import cc.abro.tow.client.menu.panels.controllers.creategame.ClickCreateController;
import cc.abro.tow.client.menu.panels.controllers.main.ClickExitController;
import cc.abro.tow.client.menu.panels.controllers.settings.ClickConfirmController;
import cc.abro.tow.client.menu.panels.gui.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class ClickChangePanelGuiEvent implements GuiElementEvent {

    private final Class<? extends EventableGuiPanel> nextPanelClass;

    public ClickChangePanelGuiEvent(Class<? extends EventableGuiPanel> nextPanelClass) {
        this.nextPanelClass = nextPanelClass;
    }

    public Class<? extends EventableGuiPanel> getNextPanelClass() {
        return nextPanelClass;
    }
}
