package tow.engine.resources;

import org.lwjgl.BufferUtils;
import tow.engine.Global;
import tow.engine.image.Texture;
import tow.engine.io.logger.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureLoader {

    public static Texture getTexture(String path){
        try (InputStream in = ResourceLoader.getResourceAsStream(path)){
            BufferedImage image = ImageIO.read(in);
            int width = image.getWidth();
            int height = image.getHeight();

            int[] pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);

            ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];

                    buffer.put((byte) ((pixel >> 16) & 0xFF));
                    buffer.put((byte) ((pixel >> 8) & 0xFF));
                    buffer.put((byte) (pixel & 0xFF));
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
                }
            }
            buffer.flip();

            Texture texture = new Texture(width, height);
            texture.bind();
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
            Texture.unbind();

            Global.logger.println("Load image \"" + path + "\" complited", Logger.Type.DEBUG_IMAGE);
            return texture;
        } catch (IOException e){
            Global.logger.println("Image \"" + path + "\" not loading", Logger.Type.ERROR);
            return null;
        }
    }
}
