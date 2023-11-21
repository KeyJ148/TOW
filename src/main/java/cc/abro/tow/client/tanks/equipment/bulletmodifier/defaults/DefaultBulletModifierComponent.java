package cc.abro.tow.client.tanks.equipment.bulletmodifier.defaults;

import cc.abro.tow.client.tanks.equipment.bulletmodifier.BulletModifierComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class DefaultBulletModifierComponent extends BulletModifierComponent {

    public DefaultBulletModifierComponent(String name,
                                          String title,
                                          Effect effect,
                                          int techLevel) {
        super(name, title, effect, techLevel);
    }
}
