package tow.engine.resources.animations;

import tow.engine.resources.masks.Mask;
import tow.engine.resources.textures.Texture;

import java.util.List;

public class Animation {

    private List<Texture> textures;
    private Mask mask;

    public Animation(List<Texture> textures, Mask mask){
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
