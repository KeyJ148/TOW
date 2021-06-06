package cc.abro.tow.client.menu.panels.guielements;

import cc.abro.orchengine.gameobject.components.gui.EventableGuiElement;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.tow.client.menu.panels.gui.SettingsMenuGuiPanel;

public class SettingsMenuGuiElement extends EventableGuiElement<SettingsMenuGuiPanel> {

    public SettingsMenuGuiElement(SettingsMenuGuiPanel component) {
        super(component);
        component.addListener(this);
    }

    @Override
    public void processEvent(GuiElementEvent event) {

    }

}
