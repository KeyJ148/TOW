package cc.abro.orchengine.gui;

import cc.abro.orchengine.services.BlockingGuiService;
import com.spinyowl.legui.listener.MouseClickEventListener;

public interface MouseReleaseBlockingListeners extends MouseReleaseListeners {

    default MouseClickEventListener getUnblockAndParentDestroyReleaseListener(BlockingGuiService.GuiBlock guiBlock) {
        return getMouseReleaseListener(event -> {
            guiBlock.unblock();
            event.getTargetComponent().getFrame().getContainer().remove(
                    event.getTargetComponent().getParent());
        });
    }
}
