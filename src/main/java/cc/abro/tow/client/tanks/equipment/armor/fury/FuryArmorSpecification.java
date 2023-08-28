package cc.abro.tow.client.tanks.equipment.armor.fury;

import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorSpecification;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode(callSuper = true)
public class FuryArmorSpecification extends DefaultArmorSpecification {
    private final double speedUpMax;
    private final double speedUpMin;
    private final double speedDownMax;
    private final double speedDownMin;
}
