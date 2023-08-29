package cc.abro.tow.client.tanks.equipment.gun.fury;

import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.List;

public class FuryGunComponent extends DefaultGunComponent {

    private final double attackSpeedMin;
    private final double attackSpeedMax;

    public FuryGunComponent(String name,
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

    //TODO
    /*
    @Override
    public void update(long delta) {

        effect.addition.attackSpeed = attackSpeedMax - ((player.hp / player.stats.hpMax) * (attackSpeedMax - attackSpeedMin));
    }
     */
}
