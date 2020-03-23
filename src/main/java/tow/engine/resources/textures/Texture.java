package tow.engine.resources.textures;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private final int id;
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

    public void delete(){
        glDeleteTextures(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Texture texture = (Texture) o;
        return id == texture.id;
    }
}
