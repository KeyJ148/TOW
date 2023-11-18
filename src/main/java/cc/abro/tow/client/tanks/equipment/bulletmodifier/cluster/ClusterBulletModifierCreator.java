package cc.abro.tow.client.tanks.equipment.bulletmodifier.cluster;

import cc.abro.orchengine.context.Context;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreator;
import cc.abro.tow.client.tanks.equipment.bullet.BulletCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.StoredBulletModifierCreator;
import cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults.DefaultBulletModifierCreator;

import java.util.Map;
import java.util.stream.Collectors;

@StoredBulletModifierCreator
public class ClusterBulletModifierCreator<T extends ClusterBulletModifierSpecification> extends DefaultBulletModifierCreator<T> {

    @Override
    public ClusterBulletModifierComponent createBulletModifier(T bulletModifierSpecification, String name) {
        return new ClusterBulletModifierComponent(name,
                bulletModifierSpecification.getTitle(),
                createEffect(bulletModifierSpecification),
                bulletModifierSpecification.getTechLevel(),
                createSecondaryBulletByGunName(bulletModifierSpecification.getSecondaryBulletByGunName()));
    }

    @Override
    public String getType() {
        return "cluster";
    }

    @Override
    public Class<T> getBulletModifierSpecificationClass() {
        return (Class<T>) ClusterBulletModifierSpecification.class;
    }

    protected Map<String, ClusterBulletModifierComponent.BulletInfo> createSecondaryBulletByGunName(
            Map<String, ClusterBulletModifierSpecification.BulletInfo> secondaryBulletByGunName) {
        return secondaryBulletByGunName.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new ClusterBulletModifierComponent.BulletInfo(
                                getBulletCreator(e.getValue().bulletName()),
                                e.getValue().spriteName(),
                                e.getValue().soundHit()
                        )
                ));
    }
    protected BulletCreator getBulletCreator(String name) {
        return Context.getService(BulletCreatorsStorage.class).getBulletCreator(name);
    }
}
