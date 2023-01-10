package cc.abro.orchengine.services;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.orchengine.util.Vector2;
import org.lwjgl.opengl.GL11;

@EngineService
public class OpenGlService {

    private final TextureService textureService;

    public OpenGlService(TextureService textureService) {
        this.textureService = textureService;
    }

    public void renderTextureGLQuads(float width, float height, Texture texture) {
        textureService.bindTexture(texture);
        renderGlQuads(width, height);
        textureService.unbindTexture();
    }

    public void renderTextureGlQuadsFromCenter(float width, float height, Texture texture) {
        textureService.bindTexture(texture);
        renderGlQuadsFromCenter(width, height);
        textureService.unbindTexture();
    }

    public void renderGlQuads(float width, float height) {
        renderGlQuads(
                new Vector2<>(0f, 0f),
                new Vector2<>(width, 0f),
                new Vector2<>(width, height),
                new Vector2<>(0f, height));
    }

    public void renderGlQuadsFromCenter(float width, float height) {
        renderGlQuads(
                new Vector2<>(-width / 2, -height / 2),
                new Vector2<>(width / 2, -height / 2),
                new Vector2<>(width / 2, height / 2),
                new Vector2<>(-width / 2, height / 2));
    }

    private void renderGlQuads(Vector2<Float> leftUp,
                              Vector2<Float> rightUp,
                              Vector2<Float> rightDown,
                              Vector2<Float> leftDown) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(leftUp.x, leftUp.y);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(rightUp.x, rightUp.y);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(rightDown.x, rightDown.y);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(leftDown.x, leftDown.y);
        GL11.glEnd();
    }
}
