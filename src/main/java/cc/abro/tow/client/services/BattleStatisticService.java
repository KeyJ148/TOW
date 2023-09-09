package cc.abro.tow.client.services;

import cc.abro.orchengine.context.GameService;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@GameService
public class BattleStatisticService {

    private final Map<Integer, BattleStatistic> battleStatisticByEnemyId = new HashMap<>();
    private final BattleStatistic playerBattleStatistic = new BattleStatistic();

    @Data
    public static class BattleStatistic {
        private int kill;
        private int death;
        private int win;
    }
}
