package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@GameService
public class BattleStatisticService {

    private final Map<Integer, BattleStatistic> battleStatisticByEnemyId = new HashMap<>();
    private final BattleStatistic playerBattleStatistic = new BattleStatistic();

    public BattleStatistic getPlayerStatistic() {
        return playerBattleStatistic;
    }

    public BattleStatistic getEnemyStatistic(int id) {
        return battleStatisticByEnemyId.computeIfAbsent(id, (key) -> new BattleStatistic());
    }

    @Data
    public static class BattleStatistic {
        private int kill;
        private int death;
        private int win;

        public void incrementKill() {
            setKill(getKill() + 1);
        }

        public void incrementDeath() {
            setDeath(getDeath() + 1);
        }

        public void incrementWin() {
            setWin(getWin() + 1);
        }
    }
}
