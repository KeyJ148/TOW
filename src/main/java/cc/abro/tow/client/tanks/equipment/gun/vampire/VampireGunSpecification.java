package cc.abro.tow.client.tanks.equipment.gun.vampire;

import cc.abro.tow.client.tanks.equipment.gun.defaults.DefaultGunSpecification;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode(callSuper = true)
public class VampireGunSpecification extends DefaultGunSpecification {
    private final double attackSpeedMin;
    private final double attackSpeedMax;
}
