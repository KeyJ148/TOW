package cc.abro.tow.client.tanks.equipment.armor.defaults;

import cc.abro.orchengine.resources.animations.Animation;
import cc.abro.tow.client.tanks.equipment.armor.ArmorComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class DefaultArmorComponent extends ArmorComponent {

    public DefaultArmorComponent(String name,
                                 String title,
                                 Effect effect,
                                 double animationSpeedCoefficient,
                                 Animation animation,
                                 int size) {
        super(name, title, effect, animationSpeedCoefficient, animation, size);
    }
}
