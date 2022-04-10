package cc.abro.tow.services;

import cc.abro.orchengine.context.TestService;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;

import java.awt.image.BufferedImage;
import java.util.Random;

import static cc.abro.tow.services.ServiceUtils.Profiles.TEST_DISABLE_RENDER;

@TestService({TEST_DISABLE_RENDER})
public class TestTextureService extends TextureService {

    @Override
    public Texture createTexture(BufferedImage image) {
        return new Texture(image);
    }

    @Override
    public int genTextureId() {
        return new Random().nextInt();
    }

    @Override
    public void bindTexture(int textureId) {}

    @Override
    public void unbindTexture() {}

    @Override
    public void deleteTexture(int textureId) {}
}
