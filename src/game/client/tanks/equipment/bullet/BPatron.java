package game.client.tanks.equipment.bullet;

import game.client.particles.Pixel;
import game.client.tanks.player.Bullet;
import game.client.tanks.player.Player;

public class BPatron extends Bullet{

    @Override
    public void init(Player player, double x, double y, double dir, double damage, int range, String name){
        super.init(player, x, y, dir, damage, range, name);

        particles = new Pixel(this, x, y, dir, movement.speed);
    }
}
