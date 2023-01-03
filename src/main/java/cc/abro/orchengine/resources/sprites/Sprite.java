package cc.abro.orchengine.resources.sprites;

import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;

public class Sprite {

    private final Texture texture;
    private final Mask mask;

    public Sprite(Texture texture, Mask mask) {
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
