package tow.engine.resources.sprites;

import tow.engine.image.Mask;
import tow.engine.resources.textures.Texture;
import tow.engine.resources.textures.TextureLoader;

public class SpriteLoader {

    public static Sprite getSprite(String texturePath, String maskPath){
        Texture texture = TextureLoader.getTexture(texturePath);
        Mask mask = new Mask(maskPath, texture.getWidth(), texture.getHeight());

        return new Sprite(texture, mask);
    }
}
