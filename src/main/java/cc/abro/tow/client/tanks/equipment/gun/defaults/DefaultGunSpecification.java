package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.tow.client.tanks.equipment.gun.GunSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DefaultGunSpecification implements GunSpecification {
    public record TrunkInfo(int bulletStartX, int bulletStartY, double bulletStartDir) {}
    public record BulletInfo(String behavior, String spriteName) {}

    private final double attackSpeed;
    private final double bulletSpeed;
    private final double speedRotate;
    private final double damage;
    private final double range;
    private final List<TrunkInfo> gunTrunksInfo;
    private final double bulletExplosionPower;
    private final String spriteName;
    private final String title;
    private final String type;
    private final int size;
    private final Map<String, BulletInfo> bulletMapping;
}
