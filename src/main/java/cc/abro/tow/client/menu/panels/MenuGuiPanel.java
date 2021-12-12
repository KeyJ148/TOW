package cc.abro.tow.client.menu.panels;

import cc.abro.orchengine.gui.MouseReleaseChangePanelListeners;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Panel;

import static cc.abro.tow.client.menu.InterfaceStyles.createPanelStyle;

public class MenuGuiPanel extends Panel implements MouseReleaseChangePanelListeners {

    public MenuGuiPanel() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }

    public void addComponent(Component component, int x, int y, int width, int height) {
        component.setPosition(x, y);
        component.setSize(width, height);
        add(component);
    }

    @Override
    public Component getRemovableComponentToChangePanel() {
        return this;
    }
}
