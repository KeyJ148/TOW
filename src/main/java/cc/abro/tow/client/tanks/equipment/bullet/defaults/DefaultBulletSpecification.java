package cc.abro.tow.client.tanks.equipment.bullet.defaults;

import cc.abro.tow.client.tanks.equipment.bullet.BulletSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DefaultBulletSpecification implements BulletSpecification {
    private final double speed;
    private final double damage;
    private final double range;
    private final double explosionPower;
    private final String title;
    private final String type;
}
