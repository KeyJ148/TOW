package cc.abro.tow.client.map.objects.destroyed;

import cc.abro.orchengine.location.Location;
import cc.abro.orchengine.resources.masks.Mask;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;
import lombok.Getter;

public class DestroyedMapObject extends CollisedMapObject {

    @Getter
    private final double stability;

    public DestroyedMapObject(Location location, int id, int x, int y, int z, String type, Texture texture, double direction, Mask mask, double stability) {
        super(location, id, x, y, z, type, texture, direction, mask);
        this.stability = stability;
    }
}
