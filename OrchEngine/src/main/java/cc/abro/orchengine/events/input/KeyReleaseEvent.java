package cc.abro.orchengine.events.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyReleaseEvent {

    private final int key;
    private final int mods;
    private final int scancode;

    public KeyReleaseEvent(com.spinyowl.legui.event.KeyEvent<?> keyEvent) {
        key = keyEvent.getKey();
        mods = keyEvent.getKey();
        scancode = keyEvent.getScancode();
    }
}
