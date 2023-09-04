package cc.abro.tow.client.tanks.stats;

public class Effect {

    public Stats addition = new Stats();//Увеличение характеристик (+)
    public Stats multi = new Stats(1);//Увеличение характеристик (*)

    public void calcAddStats(Stats stats) {
        stats.hpMax += addition.hpMax;
        stats.hpRegen += addition.hpRegen;
        stats.speedUp += addition.speedUp;
        stats.speedDown += addition.speedDown;
        stats.stability += addition.stability;

        stats.speedRotateGun += addition.speedRotateGun;
        stats.speedRotateTank += addition.speedRotateTank;

        stats.attackSpeed += addition.attackSpeed;
        stats.range += addition.range;
        stats.damage += addition.damage;
    }

    public void calcMultiStats(Stats stats) { //TODO в начале суммировать множители, а потом умножать на них? Т.к. сейчас +30% и -30% = 1*1.3*0.7 = 0.91
        stats.hpMax *= multi.hpMax;
        stats.hpRegen *= multi.hpRegen;
        stats.speedUp *= multi.speedUp;
        stats.speedDown *= multi.speedDown;
        stats.stability *= multi.stability;

        stats.speedRotateGun *= multi.speedRotateGun;
        stats.speedRotateTank *= multi.speedRotateTank;

        stats.attackSpeed *= multi.attackSpeed;
        stats.range *= multi.range;
        stats.damage *= multi.damage;
    }

    //TODO equals, hashcode @Data и конструктор, и для stats
}
