package tow.engine2.image;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {

    private int id;
    private int width, height;

    public Texture(ByteBuffer image, int width, int height) throws IOException {
        this.width = width;
        this.height = height;

        id = glGenTextures();
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        unbind();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void bind(){
        glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public static void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
