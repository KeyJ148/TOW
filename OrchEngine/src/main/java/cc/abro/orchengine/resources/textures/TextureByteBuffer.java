package cc.abro.orchengine.resources.textures;

import java.nio.ByteBuffer;

public class TextureByteBuffer {

    private final int width;
    private final int height;
    private final ByteBuffer textureBuffer;

    public TextureByteBuffer(int width, int height, ByteBuffer textureBuffer) {
        this.width = width;
        this.height = height;
        this.textureBuffer = textureBuffer;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ByteBuffer getTextureBuffer() {
        return textureBuffer;
    }
}
