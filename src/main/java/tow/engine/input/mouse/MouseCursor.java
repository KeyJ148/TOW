package tow.engine.input.mouse;

import org.lwjgl.BufferUtils;
import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.render.Rendering;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseCursor {

    private GameObject cursor;
    private boolean captureCursor = false;

    public MouseCursor(){
        //Создание объекта курсора (используется компонент Position и Sprite)
        cursor = new GameObject();
        cursor.setComponent(new Position(0, 0, -1000));
        cursor.getComponent(Position.class).absolute = false;
    }

    public void update(){
        Vector2<Integer> mousePos = getPositionFromGLFW();
        cursor.getComponent(Position.class).x = mousePos.x;
        cursor.getComponent(Position.class).y = mousePos.y;
    }

    public void draw(){
        if (!cursor.hasComponent(Rendering.class)) return;

        //Необходимо убрать флаг drawInThisStep, т.к. курсор отрисовывается и во время общей отрисовки комнаты
        //Потом отрисовывает интерфейс, и потом снова необходимо отрисовать курсор
        cursor.getComponent(Rendering.class).startNewStep();
        cursor.draw();
    }

    public Vector2<Integer> getPosition(){
        return new Vector2<>((int) cursor.getComponent(Position.class).x, (int) cursor.getComponent(Position.class).y);
    }

    public void setTexture(Texture texture){
        //Отключение стнадартного курсора
        glfwSetInputMode(Global.engine.render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        //Присвоение текстуры объекту курсора
        cursor.setComponent(new SpriteRender(texture));
    }

    public void setDefaultTexture(){
        //Включение стнадартного курсора
        glfwSetInputMode(Global.engine.render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        //Отключение текстуры у объекта курсора
        cursor.removeComponent(Rendering.class);
    }

    public void setCapture(boolean captureCursor){
        this.captureCursor = captureCursor;
    }

    //Обновление позиции объекта cursor напрямую из позиции мыши в OpenGL
    private Vector2<Integer> getPositionFromGLFW(){
        DoubleBuffer bufX = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer bufY = BufferUtils.createDoubleBuffer(1);

        glfwGetCursorPos(Global.engine.render.getWindowID(), bufX, bufY);
        Vector2<Integer> mousePos = new Vector2<>((int) bufX.get(), (int) bufY.get());

        if (captureCursor) captureInWindow(mousePos);
        glfwSetCursorPos(Global.engine.render.getWindowID(), mousePos.x, mousePos.y);
        return mousePos;
    }

    //Если позиция мыши выходит за пределы окна, то функция нормализует значения в mousePos
    private void captureInWindow(Vector2<Integer> mousePos){
        int width = Global.engine.render.getWidth();
        int height = Global.engine.render.getHeight();

        if (mousePos.x < 0) mousePos.x = 0;
        if (mousePos.x > width) mousePos.x = width;
        if (mousePos.y < 0) mousePos.y = 0;
        if (mousePos.y > height) mousePos.y = height;
    }
}
