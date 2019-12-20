package tow.game.client.tanks.equipment.bullet;

import tow.game.client.tanks.player.Bullet;
import tow.game.client.tanks.player.Player;

public class BStreamlined extends Bullet {

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name){
        super.init(player, x, y, dir, damage, range, name);

        movement.directionDrawEquals = true;
        position.setDirectionDraw(movement.getDirection());
    }

}

