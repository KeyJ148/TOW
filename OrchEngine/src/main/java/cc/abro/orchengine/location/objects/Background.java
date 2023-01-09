package cc.abro.orchengine.location.objects;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.cycle.Render;
import cc.abro.orchengine.image.Color;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import cc.abro.orchengine.util.OpenGlUtils;
import cc.abro.orchengine.util.Vector2;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.opengl.GL11;

public class Background {

    @Getter
    @Setter
    private Texture backgroundTexture;
    @Getter
    @Setter
    private Color backgroundColor;
    @Getter
    @Setter
    private Color outsideMapColor;

    public Background() {
        this(Color.WHITE, Color.GRAY);
    }

    public Background(Texture backgroundTexture) {
        this(backgroundTexture, Color.WHITE, Color.GRAY);
    }

    public Background(Color backgroundColor, Color outsideMapColor) {
        this(null, backgroundColor, outsideMapColor);
    }

    public Background(Texture backgroundTexture, Color backgroundColor, Color outsideMapColor) {
        this.backgroundTexture = backgroundTexture;
        this.backgroundColor = backgroundColor;
        this.outsideMapColor = outsideMapColor;
    }

    public void render(int x, int y, int width, int height, Camera camera) {
        //Заливка фона карты на весь экран
        if (backgroundTexture != null) {
            Vector2<Integer> startAbsolutePosition = new Vector2<>();
            startAbsolutePosition.x = ((x - width / 2) - (x - width / 2) % backgroundTexture.getWidth());
            startAbsolutePosition.y = ((y - height / 2) - (y - height / 2) % backgroundTexture.getHeight());
            Vector2<Integer> startRelativePosition = camera.toRelativePosition(startAbsolutePosition);

            //TODO в танках появилась поддержка растягиваемых текстур. Возможно переиспользовать код, или вынести в OpenGl Utils
            int countTexturesInWidth = width / backgroundTexture.getWidth() + 2;
            int countTexturesInHeight = height / backgroundTexture.getHeight() + 2;

            int allTexturesWidth = countTexturesInWidth * backgroundTexture.getWidth();
            int allTexturesHeight = countTexturesInHeight * backgroundTexture.getHeight();

            Color.WHITE.bind();
            Context.getService(TextureService.class).bindTexture(backgroundTexture);

            GL11.glLoadIdentity();
            GL11.glTranslatef(startRelativePosition.x, startRelativePosition.y, 0);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(countTexturesInWidth, 0);
            GL11.glVertex2f(allTexturesWidth, 0);
            GL11.glTexCoord2f(countTexturesInWidth, countTexturesInHeight);
            GL11.glVertex2f(allTexturesWidth, allTexturesHeight);
            GL11.glTexCoord2f(0, countTexturesInHeight);
            GL11.glVertex2f(0, allTexturesHeight);
            GL11.glEnd();

            Context.getService(TextureService.class).unbindTexture();
        } else {
            GL11.glLoadIdentity();
            backgroundColor.bind();

            OpenGlUtils.renderGlQuads(width, height);
        }

        //Заливка фона за границами карты
        GL11.glLoadIdentity();
        outsideMapColor.bind();

        int fillW = (width - Context.getService(LocationManager.class).getActiveLocation().getWidth()) / 2;
        int fillH = (height - Context.getService(LocationManager.class).getActiveLocation().getHeight()) / 2;

        if (Context.getService(Render.class).getWidth() > Context.getService(LocationManager.class).getActiveLocation().getWidth()) {
            OpenGlUtils.renderGlQuads(fillW, height);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(width - fillW, 0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(width, 0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(width - fillW, height);
            GL11.glEnd();
        }
        if (Context.getService(Render.class).getHeight() > Context.getService(LocationManager.class).getActiveLocation().getHeight()) {
            OpenGlUtils.renderGlQuads(width, fillH);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0, height - fillH);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(width, height - fillH);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(0, height);
            GL11.glEnd();
        }
    }
}
