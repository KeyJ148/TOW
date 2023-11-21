package cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DefaultBulletModifierSpecification implements BulletModifierSpecification {
    private final double speed;
    private final double damage;
    private final double range;
    private final double explosionPower;
    private final String title;
    private final String type;
    private final int techLevel;
}
