package cc.abro.tow.client.tanks.equipment.bulletmodifier.vampire;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class VampireBulletModifierSpecification extends DefaultBulletModifierSpecification {
    private final double minDamage;
    private final double maxDamage;
}
