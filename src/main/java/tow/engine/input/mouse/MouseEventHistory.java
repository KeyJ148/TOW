package tow.engine.input.mouse;

import org.liquidengine.legui.event.MouseClickEvent;
import tow.engine.Global;

import java.util.LinkedList;
import java.util.List;

public class MouseEventHistory {

    private List<MouseClickEvent> eventHistory = new LinkedList<>();

    public MouseEventHistory(){
        //Создание обратного вызова для фиксирования всех событий мыши (кроме GUI)
        Global.engine.render.getFrameContainer().getListenerMap().addListener(MouseClickEvent.class, event -> {
            eventHistory.add(event);
        });
    }

    public List<MouseClickEvent> getList(){
        return List.copyOf(eventHistory);
    }

    public void update(){
        eventHistory.clear();
    }
}
