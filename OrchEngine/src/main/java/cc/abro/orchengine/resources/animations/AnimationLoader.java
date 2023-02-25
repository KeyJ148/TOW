package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass //TODO it is service, not utility class, because use TextureService
public class AnimationLoader {

    public Animation getAnimation(String[] texturePaths, String maskPath) {
        List<Texture> textures = new ArrayList<>();
        for (String texturePath : texturePaths) {
            textures.add(Context.getService(TextureService.class).getTexture(texturePath));
        }

        Mask mask = (maskPath != null) ?
                MaskLoader.getMask(maskPath, textures.get(0).getWidth(), textures.get(0).getHeight()) :
                MaskLoader.createDefaultMask(textures.get(0).getWidth(), textures.get(0).getHeight());

        return new Animation(textures, mask);
    }
}
