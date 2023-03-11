package cc.abro.tow.client.tanks;

public class Effect {

    public Stats addition = new Stats();//Увеличение характеристик (+)
    public Stats multi = new Stats(1);//Увеличение характеристик (*)

    public void calcAddStats(Stats stats) {
        stats.hpMax += addition.hpMax;
        stats.hpRegen += addition.hpRegen;
        stats.speedTankUp += addition.speedTankUp;
        stats.speedTankDown += addition.speedTankDown;
        stats.stability += addition.stability;

        stats.directionGunUp += addition.directionGunUp;
        stats.directionTankUp += addition.directionTankUp;

        stats.attackSpeed += addition.attackSpeed;
        stats.range += addition.range;
        stats.damage += addition.damage;
    }

    public void calcMultiStats(Stats stats) { //TODO в начале суммировать множители, а потом умножать на них? Т.к. сейчас +30% и -30% = 1*1.3*0.7 = 0.91
        stats.hpMax *= multi.hpMax;
        stats.hpRegen *= multi.hpRegen;
        stats.speedTankUp *= multi.speedTankUp;
        stats.speedTankDown *= multi.speedTankDown;
        stats.stability *= multi.stability;

        stats.directionGunUp *= multi.directionGunUp;
        stats.directionTankUp *= multi.directionTankUp;

        stats.attackSpeed *= multi.attackSpeed;
        stats.range *= multi.range;
        stats.damage *= multi.damage;
    }
}
