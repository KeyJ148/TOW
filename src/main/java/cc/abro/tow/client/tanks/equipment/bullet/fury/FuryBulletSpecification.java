package cc.abro.tow.client.tanks.equipment.bullet.fury;

import cc.abro.tow.client.tanks.equipment.bullet.BulletSpecification;
import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FuryBulletSpecification extends DefaultBulletSpecification {
    private final double minSpeed;
    private final double maxSpeed;
    private final double minDamage;
    private final double maxDamage;
}
