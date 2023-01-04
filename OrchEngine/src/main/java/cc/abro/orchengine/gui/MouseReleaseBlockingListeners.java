package cc.abro.orchengine.gui;

import cc.abro.orchengine.services.BlockingGuiService;
import org.liquidengine.legui.listener.MouseClickEventListener;

public interface MouseReleaseBlockingListeners extends MouseReleaseListeners {

    default MouseClickEventListener getUnblockAndParentDestroyReleaseListener(BlockingGuiService.GuiBlock guiBlock) {
        return getMouseReleaseListener(event -> {
            guiBlock.unblock();
            event.getTargetComponent().getFrame().getContainer().remove(
                    event.getTargetComponent().getParent());
        });
    }
}
