package tow.engine.resources.sprites;

import tow.engine.resources.masks.Mask;
import tow.engine.resources.masks.MaskLoader;
import tow.engine.resources.textures.Texture;
import tow.engine.resources.textures.TextureLoader;

public class SpriteLoader {

    public static Sprite getSprite(String texturePath, String maskPath){
        Texture texture = TextureLoader.getTexture(texturePath);
        Mask mask = (maskPath != null)?
                MaskLoader.getMask(maskPath) :
                MaskLoader.createDefaultMask(texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
