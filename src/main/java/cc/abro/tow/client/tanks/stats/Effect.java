package cc.abro.tow.client.tanks.stats;

import lombok.Data;

@Data
public class Effect {
    private Stats addition = Stats.builder().build(); //Увеличение характеристик (+)
    private Stats multi = Stats.builder().setAllValue(1).build();//Увеличение характеристик (*)
}
