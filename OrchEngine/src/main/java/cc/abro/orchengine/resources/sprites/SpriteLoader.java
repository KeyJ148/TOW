package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;

public class SpriteLoader {

    public static Sprite getSprite(String texturePath, String maskPath) {
        Texture texture = Context.getService(TextureService.class).getTexture(texturePath);
        Mask mask = (maskPath != null) ?
                MaskLoader.getMask(maskPath, texture.getWidth(), texture.getHeight()) :
                MaskLoader.createDefaultMask(texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
