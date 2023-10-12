package cc.abro.orchengine.events.input;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyPressEvent  {

    private final int key;
    private final int mods;
    private final int scancode;

    public KeyPressEvent(com.spinyowl.legui.event.KeyEvent<?> keyEvent) {
        key = keyEvent.getKey();
        mods = keyEvent.getKey();
        scancode = keyEvent.getScancode();
    }
}
