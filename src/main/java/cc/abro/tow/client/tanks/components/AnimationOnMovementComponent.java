package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.resources.textures.Texture;

import java.util.List;
import java.util.Optional;

public class AnimationOnMovementComponent extends AnimationRender<GameObject> {

    private final double frameSpeedCoefficient;

    public AnimationOnMovementComponent(List<Texture> textures, int z, double frameSpeedCoefficient) {
        super(textures, z);
        this.frameSpeedCoefficient = frameSpeedCoefficient;
    }

    @Override
    public void update(long delta) {
        super.update(delta);

        Optional<Movement> movementComponent = getGameObject().getOptionalComponent(Movement.class);
        if (movementComponent.isPresent()) {
            if (movementComponent.get().speed != 0 && getFrameSpeed() == 0) {
                setFrameSpeed(movementComponent.get().speed * frameSpeedCoefficient);
            }
            if (movementComponent.get().speed == 0 && getFrameSpeed() != 0) {
                setFrameSpeed(0);
            }
        }
    }
}
