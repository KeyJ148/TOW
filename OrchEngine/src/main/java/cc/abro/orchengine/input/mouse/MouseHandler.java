package cc.abro.orchengine.input.mouse;

import cc.abro.orchengine.context.Context;
import lombok.Getter;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.event.MouseClickEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MouseHandler {

    private MouseCursor cursor;

    private Set<Integer> buttonPressed = new HashSet<>();
    @Getter
    private final MouseEventHistory eventHistory;

    public MouseHandler(Frame frame) {
        //Создание объекта для обработки курсора
        cursor = Context.createBean(MouseCursor.class);

        //Создание объекта фиксирующего все события мыши
        eventHistory = new MouseEventHistory(frame);
    }

    //Инициализация обработчика мыши с сохранением состояния предыдущего обработчика (нажатых клавиш, курсора)
    public MouseHandler(Frame frame, MouseHandler mouse) {
        this(frame);
        buttonPressed = new HashSet<>(mouse.buttonPressed);
        cursor = mouse.cursor;
    }

    public MouseCursor getCursor() {
        return cursor;
    }

    public boolean isButtonDown(int button) {
        return buttonPressed.contains(button);
    }

    public void update() {
        List<MouseClickEvent<?>> eventHistoryList = getEventHistory().getList();
        for (MouseClickEvent<?> event : eventHistoryList) {
            if (event.getAction() == MouseClickEvent.MouseClickAction.PRESS)
                buttonPressed.add(event.getButton().getCode());
            if (event.getAction() == MouseClickEvent.MouseClickAction.RELEASE)
                buttonPressed.remove(event.getButton().getCode());
        }

        eventHistory.update();
        cursor.update();
    }

    public void draw() {
        cursor.draw();
    }
}
