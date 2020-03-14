package tow.engine.resources.sprites;

import tow.engine.resources.masks.Mask;
import tow.engine.resources.textures.Texture;

public class Sprite {

    private Texture texture;
    private Mask mask;

    public Sprite(Texture texture, Mask mask){
        this.texture = texture;
        this.mask = mask;
    }

    public Texture getTexture() {
        return texture;
    }

    public Mask getMask() {
        return mask;
    }
}
