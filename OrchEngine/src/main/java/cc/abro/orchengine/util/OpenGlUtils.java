package cc.abro.orchengine.util;

import lombok.experimental.UtilityClass;
import org.lwjgl.opengl.GL11;

@UtilityClass
public class OpenGlUtils {

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

    public void renderGlQuads(Vector2<Float> leftUp,
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
