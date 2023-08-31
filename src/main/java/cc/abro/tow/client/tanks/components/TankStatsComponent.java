package cc.abro.tow.client.tanks.components;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.stats.Stats;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class TankStatsComponent extends Component<GameObject> {

    @Getter
    private Stats stats = new Stats(); //TODO unmutable, иначе исправить метод getStats()
    private final List<Effect> effects = new ArrayList<>();

    @Getter @Setter
    private double currentHp;

    public void addEffect(Effect effect) {
        effects.add(effect);
        updateStats();
    }

    public void removeEffect(Effect effect) {
        effects.remove(effect);
        updateStats();
    }

    public void updateStats() {
        stats = new Stats();

        for (Effect effect : effects) {
            effect.calcAddStats(stats);
        }

        for (Effect effect : effects) {
            effect.calcMultiStats(stats);
        }
    }
}
