package cc.abro.tow.client.tanks.equipment.armor.vampire;

import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class VampireArmorSpecification extends DefaultArmorSpecification {
    private final double speedUpMax;
    private final double speedUpMin;
    private final double speedDownMax;
    private final double speedDownMin;
    private final double speedRotateTankMin;
    private final double speedRotateTankMax;
}
