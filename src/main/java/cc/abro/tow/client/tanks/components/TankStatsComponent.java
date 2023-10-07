package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.components.interfaces.Updatable;
import cc.abro.tow.client.tanks.Tank;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TankStatsComponent extends Component<Tank> implements Updatable {

    @Getter
    private Stats stats = new Stats(); //TODO unmutable, иначе исправить метод getStats()
    private final List<Effect> effects = new ArrayList<>();

    @Getter
    private double currentHp;

    public void update(long delta) {
        if (currentHp < 0) {
            getGameObject().exploded();
            return;
        }

        updateStats();
        setCurrentHp(getCurrentHp() + delta / Math.pow(10, 9) * stats.hpRegen);
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
        this.currentHp = Math.min(currentHp, stats.hpMax);
    }

    private void updateStats() {
        stats = new Stats();
        for (Effect effect : effects) {
            effect.calcAddStats(stats);
        }
        for (Effect effect : effects) {
            effect.calcMultiStats(stats);
        }
    }
}
