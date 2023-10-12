package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.events.UpdateLocationEvent;
import cc.abro.orchengine.events.input.KeyEvent;
import cc.abro.orchengine.events.input.KeyPressEvent;
import cc.abro.orchengine.events.input.KeyReleaseEvent;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.util.Vector2;
import com.google.common.eventbus.Subscribe;
import com.spinyowl.legui.event.MouseClickEvent;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

@EngineService
public class InputEventService {

    @Subscribe
    public void onLocationUpdate(UpdateLocationEvent updateLocationEvent) {
        postKeyboardEvents(updateLocationEvent.getLocation());
        postMouseClickEvents(updateLocationEvent.getLocation());
    }

    private void postKeyboardEvents(Location location) {
        location.getGuiLocationFrame().getKeyboard().getEventHistory().getList().forEach(keyEvent -> {
            location.postEvent(new KeyEvent(keyEvent));
            if (keyEvent.getAction() == GLFW_PRESS) {
                location.postEvent(new KeyPressEvent(keyEvent));
            }
            if (keyEvent.getAction() == GLFW_RELEASE) {
                location.postEvent(new KeyReleaseEvent(keyEvent));
            }
        });
    }

    private void postMouseClickEvents(Location location) {
        Vector2<Integer> cursorRelativePosition = location.getGuiLocationFrame().getMouse().getCursor().getPosition();
        Vector2<Double> cursorRelativePositionDouble =
                new Vector2<>((double) cursorRelativePosition.x, (double) cursorRelativePosition.y);
        Vector2<Double> cursorAbsolutePositionDouble = location.getCamera().toAbsolutePosition(cursorRelativePositionDouble);
        Vector2<Integer> cursorAbsolutePosition =
                new Vector2<>(cursorAbsolutePositionDouble.x.intValue(), cursorAbsolutePositionDouble.y.intValue());

        location.getGuiLocationFrame().getMouse().getEventHistory().getList().forEach(mouseClickEvent -> {
            location.postEvent(new cc.abro.orchengine.events.input.MouseClickEvent(mouseClickEvent,
                    cursorRelativePosition, cursorAbsolutePosition));
            if (mouseClickEvent.getAction() == MouseClickEvent.MouseClickAction.PRESS) {
                location.postEvent(new cc.abro.orchengine.events.input.MousePressEvent(mouseClickEvent,
                        cursorRelativePosition, cursorAbsolutePosition));
            }
            if (mouseClickEvent.getAction() == MouseClickEvent.MouseClickAction.RELEASE) {
                location.postEvent(new cc.abro.orchengine.events.input.MouseReleaseEvent(mouseClickEvent,
                        cursorRelativePosition, cursorAbsolutePosition));
            }
        });
    }
}
