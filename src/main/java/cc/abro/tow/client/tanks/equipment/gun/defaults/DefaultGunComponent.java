package cc.abro.tow.client.tanks.equipment.gun.defaults;

import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.tanks.equipment.gun.GunComponent;
import cc.abro.tow.client.tanks.stats.Effect;

import java.util.List;

public class DefaultGunComponent extends GunComponent{

    public DefaultGunComponent(String name,
                               String title,
                               Effect effect,
                               Sprite sprite,
                               List<GunComponent.TrunkInfo> trunksInfo) {
        super(name, title, effect, sprite, trunksInfo);
    }
}
