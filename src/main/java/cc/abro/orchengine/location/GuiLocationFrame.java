package cc.abro.orchengine.location;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.LeguiRender;
import cc.abro.orchengine.input.keyboard.KeyboardHandler;
import cc.abro.orchengine.input.mouse.MouseHandler;
import org.liquidengine.legui.component.Frame;

public class GuiLocationFrame {

    private final LeguiRender leguiRender;
    private final Frame guiFrame; //Объект хранящий все элементы gui в данной локации
    private KeyboardHandler keyboard; //Объект хранящий события клавиатуры
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

    public Frame getGuiFrame() {
        return guiFrame;
    }

    public KeyboardHandler getKeyboard() {
        return keyboard;
    }

    public MouseHandler getMouse() {
        return mouse;
    }

    protected void setKeyboard(KeyboardHandler keyboard) {
        this.keyboard = keyboard;
    }

    protected void setMouse(MouseHandler mouse) {
        this.mouse = mouse;
    }
}
