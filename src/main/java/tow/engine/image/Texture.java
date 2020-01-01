package tow.engine.image;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int id;
    private int width, height;

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;

        id = glGenTextures();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
