package cc.abro.tow.client.tanks.equipment.gun.vampire;

import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.List;

public class VampireGunComponent extends DefaultGunComponent {

    private final double attackSpeedMin;
    private final double attackSpeedMax;

    public VampireGunComponent(String name,
                               String title,
                               Effect effect,
                               Sprite sprite,
                               List<TrunkInfo> trunksInfo,
                               double attackSpeedMin,
                               double attackSpeedMax) {
        super(name, title, effect, sprite, trunksInfo);
        this.attackSpeedMin = attackSpeedMin;
        this.attackSpeedMax = attackSpeedMax;
    }

    @Override
    public void update(long delta) {
        double vampire = getGameObject().getTankVampireComponent().getVampire();
        double newAttackSpeed = attackSpeedMax - ((1 - vampire) * (attackSpeedMax - attackSpeedMin));
        getEffect().addition.attackSpeed = newAttackSpeed;
    }
}
