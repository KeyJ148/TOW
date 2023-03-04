package cc.abro.tow.client.gui.menu.panels;

import cc.abro.orchengine.gui.MouseReleaseChangePanelListeners;
import cc.abro.orchengine.services.ServiceConsumer;
import cc.abro.tow.client.gui.menu.InterfaceStyles;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Panel;

public class MenuGuiPanel extends Panel implements MouseReleaseChangePanelListeners, ServiceConsumer {

    public MenuGuiPanel() {
        setStyle(InterfaceStyles.createPanelStyle());
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
