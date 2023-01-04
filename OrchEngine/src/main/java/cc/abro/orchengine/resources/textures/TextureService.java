package cc.abro.orchengine.resources.textures;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.resources.ResourceLoader;
import lombok.extern.log4j.Log4j2;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

@Log4j2
@EngineService
public class TextureService {

    public ByteBuffer getByteBufferFromBufferedImage(BufferedImage image) {
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
        return buffer;
    }

    public Texture createTexture(BufferedImage image) {
        ByteBuffer buffer = getByteBufferFromBufferedImage(image);
        Texture texture = new Texture(image);
        texture.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        texture.unbind();
        return texture;
    }

    public Texture getTexture(String path) {
        try (InputStream in = ResourceLoader.getResourceAsStream(path)) {
            Texture texture = createTexture(ImageIO.read(in));
            log.debug("Load image \"" + path + "\" completed");
            return texture;
        } catch (Exception e) {
            log.error("Image \"" + path + "\" not loading");
            return null;
        }
    }

    public int genTextureId() {
        return glGenTextures();
    }

    public void bindTexture(int textureId) {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void unbindTexture() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void deleteTexture(int textureId) {
        glDeleteTextures(textureId);
    }
}
