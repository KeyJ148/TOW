package cc.abro.tow.client.menu.panels.gui;

import cc.abro.orchengine.gui.EventableGuiPanel;

import static cc.abro.tow.client.menu.InterfaceStyles.createPanelStyle;

public class MenuGuiPanel extends EventableGuiPanel {

    public MenuGuiPanel() {
        setStyle(createPanelStyle());
        setFocusable(false);
    }//TODO пернести в движок, как пример кешиуремой панели

    /**
     * Used only for cached panels
     * @param controllersSupplier List of controllers, which will cached
     */
    /* TODO public MenuGuiPanel(Supplier<Set<GuiElementController>> controllersSupplier) {
        this();
        Manager.getService(PanelControllersStorage.class).registry(getClass(), controllersSupplier);
    }*/
}
