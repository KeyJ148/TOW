package tech.abro.orchengine.resources.sprites;

import tech.abro.orchengine.resources.masks.Mask;
import tech.abro.orchengine.resources.masks.MaskLoader;
import tech.abro.orchengine.resources.textures.Texture;
import tech.abro.orchengine.resources.textures.TextureLoader;

public class SpriteLoader {

    public static Sprite getSprite(String texturePath, String maskPath){
        Texture texture = TextureLoader.getTexture(texturePath);
        Mask mask = (maskPath != null)?
                MaskLoader.getMask(maskPath) :
                MaskLoader.createDefaultMask(texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
