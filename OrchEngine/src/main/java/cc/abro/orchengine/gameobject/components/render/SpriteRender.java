package cc.abro.orchengine.gameobject.components.render;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.services.LightSystemService;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import org.lwjgl.opengl.GL11;

public class SpriteRender<T extends GameObject> extends Rendering<T> {

    @Getter
    private final Texture texture;
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
        double directionDraw = getDirection();
        directionDraw -= 90; //смещена начального угла с Востока на Север

        GL11.glLoadIdentity();
        GL11.glTranslatef(Math.round(relativePosition.x), Math.round(relativePosition.y), 0);
        GL11.glRotatef(Math.round(-directionDraw), 0f, 0f, 1f);

        getColor().bind();
        getOpenGlService().renderTextureGlQuadsFromCenter(getWidth(), getHeight(), texture);

        if (getGameObject().hasComponent(Collision.class)) {
            Context.getService(LightSystemService.class).render(relativePosition.x.intValue(), relativePosition.y.intValue(), getGameObject().getComponent(Collision.class));
        }
    }

    @Override
    public boolean isUnsuitableDrawableObject() {
        return unsuitableObject;
    }
}