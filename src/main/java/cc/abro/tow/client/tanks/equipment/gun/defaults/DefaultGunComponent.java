package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.List;
import java.util.Map;

public class DefaultGunComponent extends GunComponent {

    public DefaultGunComponent(String name,
                               String title,
                               Effect effect,
                               Sprite sprite,
                               String soundShot,
                               List<GunComponent.TrunkInfo> trunksInfo,
                               Map<String, BulletInfo> bulletMapping,
                               int size) {
        super(name, title, effect, sprite, soundShot, trunksInfo, bulletMapping, size);
    }
}
