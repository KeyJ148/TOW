package tow.game.client.map.objects.road;

import tow.engine.resources.textures.Texture;
import tow.game.client.map.objects.scaled.RepeatedMapObject;

public class RoadMapObject extends RepeatedMapObject {

    public RoadMapObject(int id, int x, int y, int z, String type, Texture texture, double direction, int width, int height) {
        super(id, x, y, z, type, texture, direction, width, height);
        //TODO: пока что полная копия scaled, потом добавить влияние на скорость
    }
}
