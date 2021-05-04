package tow.engine.resources.animations;

import tow.engine.resources.masks.Mask;
import tow.engine.resources.masks.MaskLoader;
import tow.engine.resources.textures.Texture;
import tow.engine.resources.textures.TextureLoader;

import java.util.ArrayList;
import java.util.List;

public class AnimationLoader {

    public static Animation getAnimation(String[] texturePaths, String maskPath){
        List<Texture> textures = new ArrayList<>();
        for(String texturePath : texturePaths){
            textures.add(TextureLoader.getTexture(texturePath));
        }

        Mask mask = (maskPath != null)?
                MaskLoader.getMask(maskPath) :
                MaskLoader.createDefaultMask(textures.get(0).getWidth(), textures.get(0).getHeight());

        return new Animation(textures, mask);
    }
}
