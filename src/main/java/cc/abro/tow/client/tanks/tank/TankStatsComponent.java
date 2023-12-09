package cc.abro.tow.client.tanks.tank;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TankStatsComponent extends Component<Tank> implements Updatable {

    private Stats stats = Stats.builder().build();
    private final List<Effect> effects = new ArrayList<>();

    @Getter
    private double currentHp;

    @Override
    public void update(long delta) {
        if (currentHp <= 0) {
            getGameObject().exploded();
            return;
        }

        updateStats();
        setCurrentHp(getCurrentHp() + delta / Math.pow(10, 9) * stats.getHpRegen());
    }

    public Stats getStats() {
        return new Stats.StatsBuilder(stats).build();
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
        updateStats();
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
        updateStats();
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = Math.min(currentHp, stats.getHpMax());
    }

    private void updateStats() {
        Stats.StatsBuilder statsBuilder = Stats.builder();
        for (Effect effect : effects) {
            statsBuilder.addStats(effect.getAddition());
        }
        for (Effect effect : effects) {
            statsBuilder.multiStats(effect.getMulti());
        }
        stats = statsBuilder.build();
    }
}
