package cc.abro.tow.client.menu.panels.events.main;

import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import org.liquidengine.legui.component.Component;

import java.util.Set;

public class ClickCloseChildPanelGuiEvent implements GuiElementEvent {

    private final Set<Component> componentsToFocus;

    public ClickCloseChildPanelGuiEvent(Set<Component> componentsToFocus) {
        this.componentsToFocus = componentsToFocus;
    }

    public Set<Component> getComponentsToFocus() {
        return componentsToFocus;
    }
}
