package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;

@EngineService
public class SpriteLoader {

    private final TextureService textureService;

    public SpriteLoader(TextureService textureService) {
        this.textureService = textureService;
    }

    public Sprite getSprite(String texturePath, String maskPath) {
        Texture texture = textureService.getTexture(texturePath);
        Mask mask = (maskPath != null) ?
                MaskLoader.getMask(maskPath, texture.getWidth(), texture.getHeight()) :
                MaskLoader.createDefaultMask(texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
