package cc.abro.orchengine.input.mouse;

import cc.abro.orchengine.context.EngineBean;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

@EngineBean
public class MouseCursor {

    private final GameObject cursor;
    private boolean captureCursor = false;

    private final Render render;

    public MouseCursor(Render render) {
        this.render = render;

        //Создание объекта курсора (используется компонент Position и Sprite)
        cursor = GameObjectFactory.create(new Location(), 0, 0, -1000); //TODO fake location is ok?
        //cursor.getComponent(Position.class).absolute = false; //TODO
    }

    public void update() {
        Vector2<Integer> mousePos = getPositionFromGLFW();
        cursor.getComponent(Position.class).x = mousePos.x;
        cursor.getComponent(Position.class).y = mousePos.y;
    }

    public void draw() {
        if (!cursor.hasComponent(Rendering.class)) return;

        //Необходимо убрать флаг drawInThisStep, т.к. курсор отрисовывается и во время общей отрисовки локации
        //Потом отрисовывает интерфейс, и потом снова необходимо отрисовать курсор
        //TODO cursor.getComponent(Rendering.class).startNewStep();
        cursor.draw();
    }

    public Vector2<Integer> getPosition() {
        return new Vector2<>((int) cursor.getComponent(Position.class).x, (int) cursor.getComponent(Position.class).y);
    }

    public void setTexture(Texture texture) {
        //Отключение стнадартного курсора
        glfwSetInputMode(render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        //Присвоение текстуры объекту курсора
        cursor.addComponent(new SpriteRender(texture));
    }

    public void setDefaultTexture() {
        //Включение стнадартного курсора
        glfwSetInputMode(render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        //Отключение текстуры у объекта курсора
        cursor.removeComponents(Rendering.class);
    }

    public void setCapture(boolean captureCursor) {
        this.captureCursor = captureCursor;
    }

    //Обновление позиции объекта cursor напрямую из позиции мыши в OpenGL
    private Vector2<Integer> getPositionFromGLFW() {
        DoubleBuffer bufX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer bufY = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(render.getWindowID(), bufX, bufY);
        Vector2<Integer> mousePos = new Vector2<>((int) bufX.get(), (int) bufY.get());

        if (captureCursor) {
            captureInWindow(mousePos);
            glfwSetCursorPos(render.getWindowID(), mousePos.x, mousePos.y);
        }
        return mousePos;
    }

    //Если позиция мыши выходит за пределы окна, то функция нормализует значения в mousePos
    private void captureInWindow(Vector2<Integer> mousePos) {
        int width = render.getWidth();
        int height = render.getHeight();

        if (mousePos.x < 0) mousePos.x = 0;
        if (mousePos.x > width) mousePos.x = width;
        if (mousePos.y < 0) mousePos.y = 0;
        if (mousePos.y > height) mousePos.y = height;
    }
}
