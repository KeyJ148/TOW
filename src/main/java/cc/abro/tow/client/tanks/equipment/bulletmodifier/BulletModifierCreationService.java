package cc.abro.tow.client.tanks.equipment.bulletmodifier;

import cc.abro.orchengine.context.GameService;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class BulletModifierCreationService {

    private final BulletModifierSpecificationStorage bulletModifierSpecificationStorage;
    private final BulletModifierCreatorsStorage bulletModifierCreatorsStorage;

    public BulletModifierComponent createBulletModifier(String name) {
        BulletModifierSpecification bulletModifierSpecification =
                bulletModifierSpecificationStorage.getBulletModifierSpecification(name);
        BulletModifierCreator bulletModifierCreator =
                bulletModifierCreatorsStorage.getBulletModifierCreator(bulletModifierSpecification.getType());
        return bulletModifierCreator.createBulletModifier(bulletModifierSpecification, name);
    }
}
