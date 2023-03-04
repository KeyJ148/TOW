package cc.abro.orchengine.gui;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.services.GuiService;
import cc.abro.orchengine.services.ServiceConsumer;
import com.spinyowl.legui.component.Component;
import com.spinyowl.legui.component.Panel;
import com.spinyowl.legui.listener.MouseClickEventListener;

import java.util.function.Supplier;

public interface MouseReleaseChangePanelListeners extends MouseReleaseListeners, ServiceConsumer {

    Component getRemovableComponentToChangePanel();

    default MouseClickEventListener getChangeToPanelReleaseListener(Supplier<Panel> newPanelSupplier) {
        GuiService guiService = getGuiService();
        return getMouseReleaseListener(() -> {
            Panel newPanel = newPanelSupplier.get();
            guiService.moveComponentToWindowCenter(newPanel);
            guiService.replaceComponent(getRemovableComponentToChangePanel(), newPanel);
        });
    }

    default MouseClickEventListener getChangeToCachedPanelReleaseListener(Class<? extends Panel> newPanelClass) {
        return getChangeToPanelReleaseListener(() -> Context.getService(GuiPanelStorage.class).getPanel(newPanelClass));
    }

    default MouseClickEventListener getChangeToPanelReleaseListener(Panel newPanel) {
        return getChangeToPanelReleaseListener(() -> newPanel);
    }
}
