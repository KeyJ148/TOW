package cc.abro.tow.client.settings;

import lombok.Data;

@Data
public class GameSettings {
    private int soundRange;
    private int messagePerSecond;
    private double vampireUpFromOneDamage;
    private double vampireDownFromSec;
    private double minBulletSpeedCoefficient;
}
