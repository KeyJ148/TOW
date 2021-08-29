package cc.abro.tow.client.tanks;

public class Stats {

    public double hpMax; //Максимальное здоровье
    public double hpRegen; //Скорость восстановления здоровья (кол-во реген хп в секунду)
    public double speedTankUp; //Скорость танка при движение вперед (кол-во пикселей в секунду)
    public double speedTankDown; //Скорость танка при движение назад (кол-во пикселей в секунду)
    public int stability; //На сколько прочные объекты может сбивать танк

    public double directionGunUp; //Скорость поворота пушки (кол-во градусов в секунду)
    public double directionTankUp; //Скорость поворота корпуса такна (кол-во градусов в секунду)

    public double attackSpeed;//скорость атаки (кол-во выстрелов в секунду)
    public int range;//Дальность выстрела (в пикселях)
    public double damage;//Урон

    public Stats() {
        this(0);
    }

    public Stats(int value) {
        hpMax = value;
        hpRegen = value;
        speedTankUp = value;
        speedTankDown = value;
        stability = value;

        directionGunUp = value;
        directionTankUp = value;

        attackSpeed = value;
        range = value;
        damage = value;
    }

    @Override
    public String toString() {
        return "Max hp: " + hpMax
                + "\nRegen hp: " + hpRegen
                + "\nTank speed (up): " + speedTankUp
                + "\nTank speed (down): " + speedTankDown
                + "\nStability: " + stability
                + "\nSpeed rotate gun: " + directionGunUp
                + "\nSpeed rotate tank: " + directionTankUp
                + "\nAttack speed: " + attackSpeed
                + "\nAttack range (except bullet): " + range
                + "\nAttack damage (except bullet): " + damage;
    }
}
