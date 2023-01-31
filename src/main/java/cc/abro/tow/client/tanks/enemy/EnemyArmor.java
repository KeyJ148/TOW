package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.resources.animations.Animation;

import java.util.Arrays;

public class EnemyArmor extends GameObject {

    public Enemy enemy;

    public EnemyArmor(int x, int y, double direction, int z, Animation animation, Enemy enemy) {
        super(enemy.getLocation(), Arrays.asList(new Position(x, y, z, (int) direction), new AnimationRender(animation.textures())));
        this.enemy = enemy;

        addComponent(new Movement());
        getComponent(Movement.class).directionDrawEquals = false;
        addComponent(new Collision(animation.mask()));
    }
}