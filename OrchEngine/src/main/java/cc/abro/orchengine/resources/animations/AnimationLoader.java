package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.context.EngineService;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.masks.MaskLoader;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.resources.textures.TextureService;

import java.util.ArrayList;
import java.util.List;

@EngineService
public class AnimationLoader {

    private final TextureService textureService;

    public AnimationLoader(TextureService textureService) {
        this.textureService = textureService;
    }

    public Animation getAnimation(String[] texturePaths, String maskPath) {
        List<Texture> textures = new ArrayList<>();
        for (String texturePath : texturePaths) {
            textures.add(textureService.getTexture(texturePath));
        }

        Mask mask = (maskPath != null) ?
                MaskLoader.getMaskFromFile(maskPath, textures.get(0).getWidth(), textures.get(0).getHeight()) :
                MaskLoader.getRectangularMask(textures.get(0).getWidth(), textures.get(0).getHeight());

        return new Animation(textures, mask);
    }
}
