package tow.engine.input.keyboard;

import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.KeyEvent;

import java.util.LinkedList;
import java.util.List;

public class KeyboardEventHistory {

    private List<KeyEvent> eventHistory = new LinkedList<>();

    public KeyboardEventHistory(Frame frame){
        //Создание обратного вызова для фиксирования всех событий клавиатуры (кроме GUI)
        frame.getContainer().getListenerMap().addListener(KeyEvent.class, event -> {
            eventHistory.add(event);
        });
    }

    public List<KeyEvent> getList(){
        return List.copyOf(eventHistory);
    }

    public void update(){
        eventHistory.clear();
    }
}
