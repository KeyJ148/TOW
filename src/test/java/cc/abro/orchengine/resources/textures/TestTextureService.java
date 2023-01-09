package cc.abro.orchengine.resources.textures;

import cc.abro.orchengine.context.TestService;

import java.awt.image.BufferedImage;

import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_DISABLE_RENDER;

@TestService({TEST_DISABLE_RENDER})
public class TestTextureService extends TextureService {

    private int textureIdCounter = 1;

    @Override
    public Texture createTexture(BufferedImage image) {
        return new Texture(textureIdCounter++, image);
    }

    @Override
    public void bindTexture(int textureId) {}

    @Override
    public void unbindTexture() {}

    @Override
    public void deleteTexture(int textureId) {}
}
