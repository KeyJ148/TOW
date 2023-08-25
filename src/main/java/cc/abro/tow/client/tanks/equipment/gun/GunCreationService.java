package cc.abro.tow.client.tanks.equipment.gun;

import cc.abro.orchengine.context.GameService;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class GunCreationService {

    private final GunSpecificationStorage gunSpecificationStorage;
    private final GunCreatorsStorage gunCreatorsStorage;

    public GunComponent createGun(String name) {
        GunSpecification gunSpecification = gunSpecificationStorage.getGunSpecification(name);
        GunCreator gunCreator = gunCreatorsStorage.getGunCreator(gunSpecification.getType());
        return gunCreator.createGun(gunSpecification, name);
    }
}
