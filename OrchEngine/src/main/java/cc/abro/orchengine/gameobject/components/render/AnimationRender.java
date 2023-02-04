package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.opengl.GL11;

import java.util.List;

@Log4j2
public class AnimationRender extends Rendering implements Updatable {

    private List<Texture> textures;

    @Getter
    private int frameSpeed = 0; //Кол-во кадров в секнду
    @Getter
    @Setter
    private int frameNow; //Номер текущего кадра [0;inf)

    private long update = 0; //Сколько прошло наносекунд с последней смены кадра

    public AnimationRender(List<Texture> textures) {
        this.textures = textures;
    }

    @Override
    public void update(long delta) {
        update += delta;
        if ((frameSpeed != 0) && (update > 1000000000 / frameSpeed)) {
            update = 0;
            if (frameNow == textures.size() - 1) {
                frameNow = 0;
            } else {
                frameNow++;
            }
        }
    }

    @Override
    public void draw() {
        Vector2<Integer> relativePosition = getGameObject().getRelativePosition();
        double xView = relativePosition.x;
        double yView = relativePosition.y;
        double directionDraw = getGameObject().getComponent(Position.class).getDirectionDraw();

        directionDraw -= 90; //смещена начального угла с Востока на Север

        int width = getWidth();
        int height = getHeight();

        GL11.glLoadIdentity();
        GL11.glTranslatef((float) xView, (float) yView, 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        color.bind();
        Context.getService(TextureService.class).bindTexture(textures.get(frameNow));

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
        Context.getService(TextureService.class).unbindTexture();
    }

    public void setFrameSpeed(int frameSpeed) {
        if (frameSpeed < 0) {
            log.error("Frame speed must be >= 0");
            return;
        }

        this.update = 0;
        this.frameSpeed = frameSpeed;
    }

    public int getFrameNumber() {
        return textures.size();
    }

    public int getWidth(int frame) {
        return textures.get(frame).getWidth();
    }

    public int getHeight(int frame) {
        return textures.get(frame).getHeight();
    }

    @Override
    public int getWidthTexture() {
        return getWidth(getFrameNow());
    }

    @Override
    public int getHeightTexture() {
        return getHeight(getFrameNow());
    }

    @Override
    public int getWidth() {
        return (int) scale_x * getWidth(getFrameNow());
    }

    @Override
    public int getHeight() {
        return (int) scale_y * getHeight(getFrameNow());
    }

    @Override
    public void setWidth(int width) {
        scale_x = (double) width / getWidthTexture();
    }

    @Override
    public void setHeight(int height) {
        scale_y = (double) height / getHeightTexture();
    }

    @Override
    public void setDefaultSize() {
        scale_x = 1;
        scale_y = 1;
    }
}
