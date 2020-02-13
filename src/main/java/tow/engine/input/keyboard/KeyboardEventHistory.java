package tow.engine.input.keyboard;

import org.liquidengine.legui.event.KeyEvent;
import tow.engine.Global;

import java.util.LinkedList;
import java.util.List;

public class KeyboardEventHistory {

    private List<KeyEvent> eventHistory = new LinkedList<>();

    public KeyboardEventHistory(){
        //Создание обратного вызова для фиксирования всех событий клавиатуры (кроме GUI)
        Global.engine.render.getFrameContainer().getListenerMap().addListener(KeyEvent.class, event -> {
            eventHistory.add(event);
            System.out.println(event);
        });
    }

    public List<KeyEvent> getList(){
        return List.copyOf(eventHistory);
    }

    public void update(){
        eventHistory.clear();
    }
}
