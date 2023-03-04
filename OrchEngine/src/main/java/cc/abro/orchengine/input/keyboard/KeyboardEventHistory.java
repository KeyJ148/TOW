package cc.abro.orchengine.input.keyboard;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.event.KeyEvent;

import java.util.LinkedList;
import java.util.List;

public class KeyboardEventHistory {

    private final List<KeyEvent<?>> eventHistory = new LinkedList<>();

    public KeyboardEventHistory(Frame frame) {
        //Создание обратного вызова для фиксирования всех событий клавиатуры (кроме GUI)
        frame.getContainer().getListenerMap().addListener(KeyEvent.class, eventHistory::add);
    }

    public List<KeyEvent<?>> getList() {
        return List.copyOf(eventHistory);
    }

    public void update() {
        eventHistory.clear();
    }
}
