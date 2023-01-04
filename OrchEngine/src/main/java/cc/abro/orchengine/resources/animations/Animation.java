package cc.abro.orchengine.resources.animations;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;

import java.util.List;

public class Animation {

    private List<Texture> textures;
    private Mask mask;

    public Animation(List<Texture> textures, Mask mask) {
        this.textures = textures;
        this.mask = mask;
    }

    public List<Texture> getTextures() {
        return textures;
    }

    public Mask getMask() {
        return mask;
    }
}
