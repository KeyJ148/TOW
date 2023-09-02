package cc.abro.tow.client.tanks.stats;

public class Stats {

    public double hpMax; //Максимальное здоровье
    public double hpRegen; //Скорость восстановления здоровья (кол-во реген хп в секунду)
    public double speedUp; //Скорость танка при движение вперед (кол-во пикселей в секунду)
    public double speedDown; //Скорость танка при движение назад (кол-во пикселей в секунду)
    public int stability; //На сколько прочные объекты может сбивать танк

    public double speedRotateGun; //Скорость поворота пушки (кол-во градусов в секунду)
    public double speedRotateTank; //Скорость поворота корпуса такна (кол-во градусов в секунду)

    public double attackSpeed;//скорость атаки (кол-во выстрелов в секунду)
    public int range;//Дальность выстрела (в пикселях)
    public double damage;//Урон

    public Stats() {
        this(0);
    }

    public Stats(int value) {
        hpMax = value;
        hpRegen = value;
        speedUp = value;
        speedDown = value;
        stability = value;

        speedRotateGun = value;
        speedRotateTank = value;

        attackSpeed = value;
        range = value;
        damage = value;
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
                + "\nAttack range (except bullet): " + range
                + "\nAttack damage (except bullet): " + damage;
    }
}
