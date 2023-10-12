package cc.abro.orchengine.events.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyEvent {

    private final int action;
    private final int key;
    private final int mods;
    private final int scancode;

    public KeyEvent(com.spinyowl.legui.event.KeyEvent<?> keyEvent) {
        action = keyEvent.getAction();
        key = keyEvent.getKey();
        mods = keyEvent.getKey();
        scancode = keyEvent.getScancode();
    }
}
