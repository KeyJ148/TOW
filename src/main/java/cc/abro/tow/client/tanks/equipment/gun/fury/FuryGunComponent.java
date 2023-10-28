package cc.abro.tow.client.tanks.equipment.gun.fury;

import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.List;
import java.util.Map;

public class FuryGunComponent extends DefaultGunComponent {

    private final double attackSpeedMin;
    private final double attackSpeedMax;

    public FuryGunComponent(String name,
                            String title,
                            Effect effect,
                            Sprite sprite,
                            List<TrunkInfo> trunksInfo,
                            Map<String, BulletInfo> bulletMapping,
                            int size,
                            double attackSpeedMin,
                            double attackSpeedMax) {
        super(name, title, effect, sprite, trunksInfo, bulletMapping, size);
        this.attackSpeedMin = attackSpeedMin;
        this.attackSpeedMax = attackSpeedMax;
    }

    @Override
    public void update(long delta) {
        double percentHp = getGameObject().getTankStatsComponent().getCurrentHp() /
                getGameObject().getTankStatsComponent().getStats().getHpMax();
        getEffect().getAddition().setAttackSpeed(attackSpeedMax - (percentHp * (attackSpeedMax - attackSpeedMin)));
    }
}
