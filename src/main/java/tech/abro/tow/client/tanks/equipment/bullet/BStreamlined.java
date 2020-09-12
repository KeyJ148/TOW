package tech.abro.tow.client.tanks.equipment.bullet;

import tech.abro.orchengine.gameobject.components.Movement;
import tech.abro.orchengine.gameobject.components.Position;
import tech.abro.tow.client.tanks.player.Bullet;
import tech.abro.tow.client.tanks.player.Player;

public class BStreamlined extends Bullet {

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name){
        super.init(player, x, y, dir, damage, range, name);

        getComponent(Movement.class).directionDrawEquals = true;
        getComponent(Position.class).setDirectionDraw(getComponent(Movement.class).getDirection());
    }

}

