package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.Manager;
import cc.abro.orchengine.gameobject.components.gui.GuiElementController;
import cc.abro.orchengine.gameobject.components.gui.GuiElementEvent;
import cc.abro.orchengine.gui.EventableGuiPanel;
import cc.abro.orchengine.gui.PanelControllersStorage;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.event.MouseClickEvent;
import org.liquidengine.legui.style.font.FontRegistry;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static cc.abro.tow.client.menu.InterfaceStyles.*;

public class MenuGuiPanel extends EventableGuiPanel {

    public MenuGuiPanel() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }

    /**
     * Used only for cached panels
     * @param controllersSupplier List of controllers, which will cached
     */
    public MenuGuiPanel(Supplier<Set<GuiElementController>> controllersSupplier) {
        this();
        Manager.getService(PanelControllersStorage.class).registry(getClass(), controllersSupplier);
    }
}
