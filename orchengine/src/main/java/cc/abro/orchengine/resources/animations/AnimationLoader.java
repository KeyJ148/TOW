package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureLoader;

import java.util.ArrayList;
import java.util.List;

public class AnimationLoader {

    public static Animation getAnimation(String[] texturePaths, String maskPath) {
        List<Texture> textures = new ArrayList<>();
        for (String texturePath : texturePaths) {
            textures.add(TextureLoader.getTexture(texturePath));
        }

        Mask mask = (maskPath != null) ?
                MaskLoader.getMask(maskPath) :
                MaskLoader.createDefaultMask(textures.get(0).getWidth(), textures.get(0).getHeight());

        return new Animation(textures, mask);
    }
}
