package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureLoader;

public class SpriteLoader {

    public static Sprite getSprite(String texturePath, String maskPath) {
        Texture texture = TextureLoader.getTexture(texturePath);
        Mask mask = (maskPath != null) ?
                MaskLoader.getMask(maskPath) :
                MaskLoader.createDefaultMask(texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
