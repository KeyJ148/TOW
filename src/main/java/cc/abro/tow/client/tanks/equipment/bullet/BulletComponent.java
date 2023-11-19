package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.tank.Tank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BulletComponent extends Component<Tank> {

    @Getter
    private final String name; //Имя файла и техническое имя брони
    @Getter
    private final String title; //Название, отображаемое в игре
    @Getter
    private final Effect effect;
    @Getter
    private final int techLevel;
}
