package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.render.AnimationRender;
import cc.abro.orchengine.resources.animations.Animation;

import java.util.List;

public class EnemyArmor extends GameObject {

    public Enemy enemy;

    public EnemyArmor(int x, int y, double direction, int z, Animation animation, Enemy enemy) {
        super(enemy.getLocation(), x, y,
                List.of(new AnimationRender(animation.textures(), z)));
        setDirection(direction);
        this.enemy = enemy;

        addComponent(new Movement());
        getComponent(Movement.class).directionDrawEquals = false;
        addComponent(new Collision(animation.mask()));
    }
}