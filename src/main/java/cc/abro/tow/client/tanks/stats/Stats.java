package cc.abro.tow.client.tanks.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stats {

    private double hpMax; //Максимальное здоровье
    private double hpRegen; //Скорость восстановления здоровья (кол-во реген хп в секунду)
    private double speedUp; //Скорость танка при движении вперед (кол-во пикселей в секунду)
    private double speedDown; //Скорость танка при движении назад (кол-во пикселей в секунду)
    private int stability; //На сколько прочные объекты может сбивать танк
    private double speedRotateGun; //Скорость поворота пушки (кол-во градусов в секунду)
    private double speedRotateTank; //Скорость поворота корпуса танка (кол-во градусов в секунду)
    private double attackSpeed; //скорость атаки (кол-во выстрелов в секунду)
    private int range; //Дальность выстрела (в пикселях)
    private double damage; //Урон

    public static StatsBuilder builder() {
        return new StatsBuilder();
    }

    @Override
    public String toString() {
        return "Max hp: " + hpMax
                + "\nRegen hp: " + hpRegen
                + "\nTank speed (up): " + speedUp
                + "\nTank speed (down): " + speedDown
                + "\nStability: " + stability
                + "\nSpeed rotate gun: " + speedRotateGun
                + "\nSpeed rotate tank: " + speedRotateTank
                + "\nAttack speed: " + attackSpeed
                + "\nAttack range (except bullet): " + range //TODO Это используется для вывода статистики. Делать вывод статистики через другое место. Там учитывать пули. Здесь убрать toString() совсем, он сгенерирован через lombok.
                + "\nAttack damage (except bullet): " + damage;
    }

    public static class StatsBuilder {

        private double hpMax;
        private double hpRegen;
        private double speedUp;
        private double speedDown;
        private int stability;
        private double speedRotateGun;
        private double speedRotateTank;
        private double attackSpeed;
        private int range;
        private double damage;

        public StatsBuilder() {}

        public StatsBuilder(Stats stats) {
            this.hpMax = stats.hpMax;
            this.hpRegen = stats.hpRegen;
            this.speedUp = stats.speedUp;
            this.speedDown = stats.speedDown;
            this.stability = stats.stability;
            this.speedRotateGun = stats.speedRotateGun;
            this.speedRotateTank = stats.speedRotateTank;
            this.attackSpeed = stats.attackSpeed;
            this.range = stats.range;
            this.damage = stats.damage;
        }

        public StatsBuilder setAllValue(double value) {
            hpMax = value;
            hpRegen = value;
            speedUp = value;
            speedDown = value;
            stability = (int) value;
            speedRotateGun = value;
            speedRotateTank = value;
            attackSpeed = value;
            range = (int) value;
            damage = value;

            return this;
        }

        public StatsBuilder addStats(Stats stats) {
            hpMax += stats.hpMax;
            hpRegen += stats.hpRegen;
            speedUp += stats.speedUp;
            speedDown += stats.speedDown;
            stability += stats.stability;
            speedRotateGun += stats.speedRotateGun;
            speedRotateTank += stats.speedRotateTank;
            attackSpeed += stats.attackSpeed;
            range += stats.range;
            damage += stats.damage;

            return this;
        }

        public StatsBuilder multiStats(Stats stats) {
            hpMax *= stats.hpMax;
            hpRegen *= stats.hpRegen;
            speedUp *= stats.speedUp;
            speedDown *= stats.speedDown;
            stability *= stats.stability;
            speedRotateGun *= stats.speedRotateGun;
            speedRotateTank *= stats.speedRotateTank;
            attackSpeed *= stats.attackSpeed;
            range *= stats.range;
            damage *= stats.damage;

            return this;
        }

        public StatsBuilder setHpMax(double hpMax) {
            this.hpMax = hpMax;
            return this;
        }

        public StatsBuilder setHpRegen(double hpRegen) {
            this.hpRegen = hpRegen;
            return this;
        }

        public StatsBuilder setSpeedUp(double speedUp) {
            this.speedUp = speedUp;
            return this;
        }

        public StatsBuilder setSpeedDown(double speedDown) {
            this.speedDown = speedDown;
            return this;
        }

        public StatsBuilder setStability(int stability) {
            this.stability = stability;
            return this;
        }

        public StatsBuilder setSpeedRotateGun(double speedRotateGun) {
            this.speedRotateGun = speedRotateGun;
            return this;
        }

        public StatsBuilder setSpeedRotateTank(double speedRotateTank) {
            this.speedRotateTank = speedRotateTank;
            return this;
        }

        public StatsBuilder setAttackSpeed(double attackSpeed) {
            this.attackSpeed = attackSpeed;
            return this;
        }

        public StatsBuilder setRange(int range) {
            this.range = range;
            return this;
        }

        public StatsBuilder setDamage(double damage) {
            this.damage = damage;
            return this;
        }

        public Stats build() {
            return new Stats(hpMax, hpRegen, speedUp, speedDown, stability, speedRotateGun, speedRotateTank,
                    attackSpeed, range, damage);
        }
    }
}
