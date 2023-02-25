package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.opengl.GL11;

import java.util.List;

@Log4j2
public class AnimationRender extends Rendering implements Updatable {

    private final List<Texture> textures;

    @Getter
    private int currentFrame;
    @Getter
    private double frameSpeed = 0; //Кол-во кадров в секунду
    private long update = 0; //Сколько прошло наносекунд с последней смены кадра

    public AnimationRender(List<Texture> textures, int z) {
        this.textures = textures;
        setZ(z);
    }

    @Override
    public void update(long delta) {
        update += delta;
        if ((frameSpeed != 0) && (update > 1000000000 / frameSpeed)) {
            update = 0; //TODO высчитывать сколько прошло времени от следующего кадра + учитывать ситуацию, что прошло времени больше чем требуется на 1 кадр
            currentFrame = normalizeFrame(currentFrame+1);
        }
    }

    @Override
    public void draw() {
        Vector2<Double> relativePosition = getGameObject().getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getDirection();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef((float) xView, (float) yView, 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getTextureService().bindTexture(textures.get(currentFrame));

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(-width / 2, -height / 2);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(width / 2, -height / 2);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(width / 2, height / 2);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(-width / 2, height / 2);
        GL11.glEnd();
        getTextureService().unbindTexture();
    }

    public void setCurrentFrame(int frame) {
        currentFrame = normalizeFrame(frame);
    }

    public void setFrameSpeed(double frameSpeed) {
        if (frameSpeed < 0) {
            log.error("Frame speed must be >= 0");
            return;
        }

        this.update = 0;
        this.frameSpeed = frameSpeed;
    }

    public int getFrameCount() {
        return textures.size();
    }

    public int getWidth(int frame) {
        return getTexture(frame).getWidth();
    }

    public int getHeight(int frame) {
        return getTexture(frame).getHeight();
    }

    public Texture getTexture(int frame) {
        return textures.get(normalizeFrame(frame));
    }

    @Override
    public Texture getTexture() {
        return getTexture(getCurrentFrame());
    }

    private int normalizeFrame(int frame) {
        return frame >= 0 ? frame % getFrameCount() : getFrameCount() - Math.abs(frame % getFrameCount());
    }
}
