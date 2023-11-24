package cc.abro.orchengine.input.mouse;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.OpenGlService;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseCursor {

    private final Render render;
    private final OpenGlService openGlService;

    @Getter
    private double x, y;
    private boolean captureCursor = false;
    private Texture cursorTexture;

    public MouseCursor() {
        this.render = Context.getService(Render.class);
        this.openGlService = Context.getService(OpenGlService.class);
    }

    public void update() {
        Vector2<Double> relativeMousePosition = getPositionFromGLFW();
        x = relativeMousePosition.x;
        y = relativeMousePosition.y;
    }

    public void draw() {
        GL11.glLoadIdentity();
        GL11.glTranslatef(Math.round(x), Math.round(y), 0);
        Color.WHITE.bind();
        openGlService.renderTextureGlQuadsFromCenter(cursorTexture.getWidth(), cursorTexture.getHeight(), cursorTexture);
    }

    public Vector2<Integer> getPosition() {
        return new Vector2<>((int) getX(), (int) getY());
    }

    public void setTexture(Texture texture) {
        //Отключение стнадартного курсора
        glfwSetInputMode(render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        //Присвоение текстуры объекту курсора
        cursorTexture = texture;
    }

    public void setDefaultTexture() {
        //Включение стнадартного курсора
        glfwSetInputMode(render.getWindowID(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        //Отключение текстуры у объекта курсора
        cursorTexture = null;
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
