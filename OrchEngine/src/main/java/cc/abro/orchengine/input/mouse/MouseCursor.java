package cc.abro.orchengine.input.mouse;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.context.EngineBean;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.gameobject.location.Camera;
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
        cursor = GameObjectFactory.create(new Location(render.getWidth(), render.getHeight()), 0, 0); //TODO fake location is ok?
    }

    public void update() {
        Camera camera = Context.getService(LocationManager.class).getActiveLocation().getCamera();
        Vector2<Double> relativeMousePosition = getPositionFromGLFW();
        Vector2<Double> absolutePosition = camera.toAbsolutePosition(relativeMousePosition);
        cursor.setX(absolutePosition.x);
        cursor.setY(absolutePosition.y);
    }

    public void draw() {
        if (!cursor.hasComponent(Rendering.class)) return;

        //Необходимо убрать флаг drawInThisStep, т.к. курсор отрисовывается и во время общей отрисовки локации
        //Потом отрисовывает интерфейс, и потом снова необходимо отрисовать курсор
        //TODO cursor.getComponent(Rendering.class).startNewStep();
        cursor.draw();
    }

    public Vector2<Integer> getPosition() {
        return new Vector2<>((int) cursor.getX(), (int) cursor.getY());
    }

    public void setTexture(Texture texture) {
        //Отключение стнадартного курсора
        glfwSetInputMode(render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        //Присвоение текстуры объекту курсора
        cursor.addComponent(new SpriteRender(texture, 10000));
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
    private Vector2<Double> getPositionFromGLFW() {
        DoubleBuffer bufX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer bufY = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(render.getWindowID(), bufX, bufY);
        Vector2<Double> mousePos = new Vector2<>(bufX.get(), bufY.get());

        if (captureCursor) {
            captureInWindow(mousePos);
            glfwSetCursorPos(render.getWindowID(), mousePos.x, mousePos.y);
        }
        return mousePos;
    }

    //Если позиция мыши выходит за пределы окна, то функция нормализует значения в mousePos
    private void captureInWindow(Vector2<Double> mousePos) {
        int width = render.getWidth();
        int height = render.getHeight();

        if (mousePos.x < 0) mousePos.x = 0.0;
        if (mousePos.x > width) mousePos.x = (double) width;
        if (mousePos.y < 0) mousePos.y = 0.0;
        if (mousePos.y > height) mousePos.y = (double) height;
    }
}
