package cc.abro.tow.client.tanks.equipment.bulletmodifier.fury;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class FuryBulletModifierSpecification extends DefaultBulletModifierSpecification {
    private final double minSpeed;
    private final double maxSpeed;
    private final double minDamage;
    private final double maxDamage;
}
