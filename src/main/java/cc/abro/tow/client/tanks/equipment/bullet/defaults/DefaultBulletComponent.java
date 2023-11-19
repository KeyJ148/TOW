package cc.abro.tow.client.tanks.equipment.bullet.defaults;

import cc.abro.tow.client.tanks.equipment.bullet.BulletComponent;
import cc.abro.tow.client.tanks.stats.Effect;

public class DefaultBulletComponent extends BulletComponent {

    public DefaultBulletComponent(String name,
                                  String title,
                                  Effect effect,
                                  int techLevel) {
        super(name, title, effect, techLevel);
    }
}
