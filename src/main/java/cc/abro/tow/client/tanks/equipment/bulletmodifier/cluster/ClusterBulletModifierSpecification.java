package cc.abro.tow.client.tanks.equipment.bulletmodifier.cluster;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierSpecification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EqualsAndHashCode(callSuper = true)
public class ClusterBulletModifierSpecification extends DefaultBulletModifierSpecification {
    public record BulletInfo(String bulletName, String spriteName, String soundHit) {}
    private final Map<String, BulletInfo> secondaryBulletByGunName;
}
