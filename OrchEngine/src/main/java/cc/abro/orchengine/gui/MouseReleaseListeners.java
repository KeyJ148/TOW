package cc.abro.orchengine.gui;

import com.spinyowl.legui.event.MouseClickEvent;
import com.spinyowl.legui.listener.MouseClickEventListener;

import java.util.function.Consumer;

public interface MouseReleaseListeners {

    default MouseClickEventListener getMouseReleaseListener(Runnable action) {
        return getMouseReleaseListener(event -> action.run());
    }

    default MouseClickEventListener getMouseReleaseListener(Consumer<MouseClickEvent> consumer) {
        return event -> {
            event.getTargetComponent().setFocused(false); //Чтобы компонент не оставался нажатым после возврата на этот экран
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                consumer.accept(event);
            }
        };
    }


}
