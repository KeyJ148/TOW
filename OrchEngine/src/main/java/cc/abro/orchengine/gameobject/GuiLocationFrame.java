package cc.abro.orchengine.gameobject;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.LeguiRender;
import cc.abro.orchengine.input.keyboard.KeyboardHandler;
import cc.abro.orchengine.input.mouse.MouseHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.liquidengine.legui.component.Frame;

public class GuiLocationFrame {

    private final LeguiRender leguiRender;

    @Getter
    private final Frame guiFrame; //Объект хранящий все элементы gui в данной локации
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private KeyboardHandler keyboard; //Объект хранящий события клавиатуры
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private MouseHandler mouse; //Объект хранящий события мыши и рисующий курсор на экране

    public GuiLocationFrame() {
        leguiRender = Context.getService(LeguiRender.class);
        guiFrame = leguiRender.createFrame();
    }

    public void pollEvents() {
        leguiRender.pollEvents(getGuiFrame());
    }

    public void update() {
    }

    public void render() {
        leguiRender.render(getGuiFrame()); //Отрисовка интерфейса (LeGUI)
        getMouse().draw(); //Отрисовка курсора мыши
    }

    public void destroy() {
    }

    public void setFocus() {
        leguiRender.setFrameFocused(getGuiFrame());
    }
}
