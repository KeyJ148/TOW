package cc.abro.tow.client.tanks.equipment.armor.defaults;

import cc.abro.tow.client.tanks.equipment.armor.ArmorSpecification;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class DefaultArmorSpecification implements ArmorSpecification {
    private final double hpMax;
    private final double hpRegen;
    private final double speedUp;
    private final double speedDown;
    private final double speedRotateGun;
    private final double speedRotateTank;
    private final int stability;
    private final double animationSpeedCoefficient;
    private final String animationName;
    private final String title;
    private final String type;
}
