package cc.abro.tow.client.tanks.equipment;

import cc.abro.orchengine.context.GameService;
import cc.abro.tow.client.tanks.equipment.armor.ArmorCreatorsStorage;
import cc.abro.tow.client.tanks.equipment.armor.defaults.DefaultArmorCreator;
import lombok.RequiredArgsConstructor;

@GameService
@RequiredArgsConstructor
public class EquipmentCreatorsLoader {

    private final ArmorCreatorsStorage armorCreatorsStorage;

    public void load() { //TODO просто сканить аннотации и добавлять автоматически по аннотациям. Отдельные классы для брони/оружия/патрон. Общий сервис для скана по аннотациям.
        armorCreatorsStorage.addArmorCreator(new DefaultArmorCreator<>());
    }
}
