package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.tow.client.tanks.player.PlayerTank;

public class BStreamlined extends Bullet {

    @Override
    public void init(PlayerTank player, double x, double y, double dir, double damage, int range, String name) {
        super.init(player, x, y, dir, damage, range, name);

        //getComponent(Movement.class).directionDrawEquals = true; //Угол обзора объекта равен углу поворота
        setDirection(getComponent(Movement.class).getDirection());
    }

}

