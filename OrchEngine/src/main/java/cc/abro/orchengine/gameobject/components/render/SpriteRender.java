package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.gameobject.location.Camera;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

public class SpriteRender extends Rendering {

    @Getter
    private final Texture texture;
    @Getter
    private final boolean unsuitableObject;

    public SpriteRender(Texture texture, int z) {
        this(texture, z, false);
    }

    public SpriteRender(Texture texture, int z, boolean unsuitableObject) {
        this.texture = texture;
        setZ(z);
        this.unsuitableObject = unsuitableObject;
    }

    @Override
    public void draw() {
        Vector2<Double> relativePosition = getGameObject().getLocation().getCamera().toRelativePosition(getPosition());
        double directionDraw = getGameObject().getDirection();
        directionDraw -= 90; //смещена начального угла с Востока на Север

        GL11.glLoadIdentity();
        GL11.glTranslatef(Math.round(relativePosition.x), Math.round(relativePosition.y), 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getOpenGlService().renderTextureGlQuadsFromCenter(getWidth(), getHeight(), texture);
    }
}