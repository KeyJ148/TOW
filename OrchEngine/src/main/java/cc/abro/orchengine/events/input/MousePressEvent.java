package cc.abro.orchengine.events.input;

import cc.abro.orchengine.util.Vector2;
import com.spinyowl.legui.input.Mouse;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MousePressEvent {

    private final Mouse.MouseButton button;
    private final int mods;
    private final Vector2<Integer> cursorPosition;
    private final Vector2<Integer> cursorOnLocationPosition;

    public MousePressEvent(com.spinyowl.legui.event.MouseClickEvent<?> mouseClickEvent,
                           Vector2<Integer> cursorPosition, Vector2<Integer> cursorOnLocationPosition) {
        button = mouseClickEvent.getButton();
        mods = mouseClickEvent.getMods();
        this.cursorPosition = cursorPosition;
        this.cursorOnLocationPosition = cursorOnLocationPosition;
    }
}
