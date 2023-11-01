package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.context.GameService;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class BulletCreationService {

    private final BulletSpecificationStorage bulletSpecificationStorage;
    private final BulletCreatorsStorage bulletCreatorsStorage;

    public BulletComponent createBullet(String name) {
        BulletSpecification bulletSpecification = bulletSpecificationStorage.getBulletSpecification(name);
        BulletCreator bulletCreator = bulletCreatorsStorage.getBulletCreator(bulletSpecification.getType());
        return bulletCreator.createBullet(bulletSpecification, name);
    }
}
