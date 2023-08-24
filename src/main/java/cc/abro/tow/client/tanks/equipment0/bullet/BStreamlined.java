package cc.abro.tow.client.tanks.equipment0.bullet;

import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.tow.client.tanks.player0.Bullet;
import cc.abro.tow.client.tanks.player0.Player;

public class BStreamlined extends Bullet {

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name) {
        super.init(player, x, y, dir, damage, range, name);

        getComponent(Movement.class).directionDrawEquals = true;
        setDirection(getComponent(Movement.class).getDirection());
    }

}

