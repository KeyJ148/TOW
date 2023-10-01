package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.opengl.GL11;

import java.util.List;

@Log4j2
public class AnimationRender<T extends GameObject> extends Rendering<T> implements Updatable {

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
        int nanosForOneFrame = (int) (1000000000 / frameSpeed);
        if ((frameSpeed != 0) && (update > nanosForOneFrame)) {
            int skipFrames = (int) (update / nanosForOneFrame);
            update = update % nanosForOneFrame;
            currentFrame = normalizeFrame(currentFrame + skipFrames);
        }
    }

    @Override
    public void draw() {
        Vector2<Double> relativePosition = getGameObject().getLocation().getCamera().toRelativePosition(getPosition());
        double directionDraw = getDirection();
        directionDraw -= 90; //смещена начального угла с Востока на Север

        GL11.glLoadIdentity();
        GL11.glTranslatef(Math.round(relativePosition.x), Math.round(relativePosition.y), 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getOpenGlService().renderTextureGlQuadsFromCenter(getWidth(), getHeight(), textures.get(currentFrame));
    }

    public void setCurrentFrame(int frame) {
        currentFrame = normalizeFrame(frame);
    }

    public void setFrameSpeed(double frameSpeed) {
        if (frameSpeed < 0) { //TODO сделать поддержку обратного порядка: если frameSpeed < 0, то анимация проигрывается в обратную сторону с конца (В AnimationOnMovementComponent:27 убрать ABS)
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
