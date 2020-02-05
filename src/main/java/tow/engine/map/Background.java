package tow.engine.map;

import org.lwjgl.opengl.GL11;
import tow.engine.Global;
import tow.engine.Vector2;
import tow.engine.image.Color;
import tow.engine.resources.textures.Texture;
import tow.engine.image.Camera;
import tow.engine.image.TextureHandler;

public class Background {

    private TextureHandler backgroundTexture;
    private Color backgroundColor;
    private Color outsideMapColor;

    public Background(){
        this(Color.WHITE, Color.GRAY);
    }

    public Background(TextureHandler backgroundTexture){
        this(backgroundTexture, Color.WHITE, Color.GRAY);
    }

    public Background(Color backgroundColor, Color outsideMapColor) {
        this(null, backgroundColor, outsideMapColor);
    }

    public Background(TextureHandler backgroundTexture, Color backgroundColor, Color outsideMapColor) {
        this.backgroundTexture = backgroundTexture;
        this.backgroundColor = backgroundColor;
        this.outsideMapColor = outsideMapColor;
    }

    public void render(int x, int y, int width, int height, Camera camera){
        //Заливка фона карты на весь экран
        if (backgroundTexture != null){
            Vector2<Integer> startAbsolutePosition = new Vector2<>();
            startAbsolutePosition.x = ((x - width/2)  - (x - width/2)  % backgroundTexture.getWidth());
            startAbsolutePosition.y = ((y - height/2) - (y - height/2) % backgroundTexture.getHeight());
            Vector2<Integer> startRelativePosition = camera.toRelativePosition(startAbsolutePosition);

            int countTexturesInWidth = width / backgroundTexture.getWidth() + 2;
            int countTexturesInHeight = height / backgroundTexture.getHeight() + 2;

            int allTexturesWidth = countTexturesInWidth*backgroundTexture.getWidth();
            int allTexturesHeight = countTexturesInHeight*backgroundTexture.getHeight();

            Color.WHITE.bind();
            backgroundTexture.texture.bind();

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

            GL11.glLoadIdentity();
            GL11.glTranslatef(startRelativePosition.x, startRelativePosition.y, 0);

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(countTexturesInWidth,0);
            GL11.glVertex2f(allTexturesWidth, 0);
            GL11.glTexCoord2f(countTexturesInWidth,countTexturesInHeight);
            GL11.glVertex2f(allTexturesWidth, allTexturesHeight);
            GL11.glTexCoord2f(0,countTexturesInHeight);
            GL11.glVertex2f(0, allTexturesHeight);
            GL11.glEnd();

            Texture.unbind();
        } else {
            GL11.glLoadIdentity();
            backgroundColor.bind();

            //TODO: OpenGL Utils / Render func для вывода прямоугольников и текстур
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(width, 0);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(0, height);
            GL11.glEnd();
        }

        //Заливка фона за границами карты
        GL11.glLoadIdentity();
        outsideMapColor.bind();

        int fillW = (width - Global.room.width)/2;
        int fillH = (height - Global.room.height)/2;

        if (Global.engine.render.getWidth() > Global.room.width){
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(fillW, 0);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(fillW, height);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(0, height);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(width-fillW, 0);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(width, 0);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(width-fillW, height);
            GL11.glEnd();
        }
        if (Global.engine.render.getHeight() > Global.room.height){
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(0, 0);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(width, 0);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(width, fillH);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(0, fillH);
            GL11.glEnd();
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0,0);
            GL11.glVertex2f(0, height-fillH);
            GL11.glTexCoord2f(1,0);
            GL11.glVertex2f(width, height-fillH);
            GL11.glTexCoord2f(1,1);
            GL11.glVertex2f(width, height);
            GL11.glTexCoord2f(0,1);
            GL11.glVertex2f(0, height);
            GL11.glEnd();
        }
    }
}
