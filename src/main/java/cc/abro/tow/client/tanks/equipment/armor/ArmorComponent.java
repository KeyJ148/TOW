package cc.abro.tow.client.tanks.equipment.armor;

import cc.abro.orchengine.gameobject.Component;
import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.stats.Effect;
import cc.abro.tow.client.tanks.tank.Tank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ArmorComponent extends Component<Tank> {

    @Getter
    private final String name; //Имя файла и техническое имя брони
    @Getter
    private final String title; //Название, отображаемое в игре
    @Getter
    private final Effect effect;
    @Getter
    private final double animationSpeedCoefficient;
    @Getter
    private final Animation animation;
    @Getter
    private final int size;
    @Getter
    private final int techLevel;
}
