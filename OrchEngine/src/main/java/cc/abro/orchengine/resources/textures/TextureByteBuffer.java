package cc.abro.orchengine.resources.textures;

import java.nio.ByteBuffer;

public record TextureByteBuffer(int width, int height, ByteBuffer textureBuffer) { }
