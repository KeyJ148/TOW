package tech.abro.orchengine.resources.sprites;

import tech.abro.orchengine.resources.masks.Mask;
import tech.abro.orchengine.resources.textures.Texture;

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
