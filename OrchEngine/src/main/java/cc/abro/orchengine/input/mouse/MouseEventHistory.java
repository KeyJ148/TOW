package cc.abro.orchengine.input.mouse;

import com.spinyowl.legui.component.Frame;
import com.spinyowl.legui.event.MouseClickEvent;

import java.util.LinkedList;
import java.util.List;

public class MouseEventHistory {

    private List<MouseClickEvent<?>> eventHistory = new LinkedList<>();

    public MouseEventHistory(Frame frame) {
        //Создание обратного вызова для фиксирования всех событий мыши (кроме GUI)
        frame.getContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
            eventHistory.add(event);
        });
    }

    public List<MouseClickEvent<?>> getList() {
        return List.copyOf(eventHistory);
    }

    public void update() {
        eventHistory.clear();
    }
}
