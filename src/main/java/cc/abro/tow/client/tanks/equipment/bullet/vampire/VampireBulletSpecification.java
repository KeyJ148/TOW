package cc.abro.tow.client.tanks.equipment.bullet.vampire;

import cc.abro.tow.client.tanks.equipment.bullet.defaults.DefaultBulletSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class VampireBulletSpecification extends DefaultBulletSpecification {
    private final double minDamage;
    private final double maxDamage;
}
