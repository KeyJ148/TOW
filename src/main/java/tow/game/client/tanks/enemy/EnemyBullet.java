package tow.game.client.tanks.enemy;

import tow.engine.Global;
import tow.engine.gameobject.GameObject;
import tow.engine.gameobject.GameObjectFactory;
import tow.engine.gameobject.components.Movement;
import tow.engine.gameobject.components.Position;
import tow.engine.gameobject.components.particles.Particles;
import tow.engine.gameobject.components.render.SpriteRender;
import tow.engine.resources.textures.Texture;
import tow.game.client.particles.Explosion;

import java.util.Arrays;

public class EnemyBullet extends GameObject {

	public int idEnemy;
	public long idNet;
	
	public EnemyBullet(double x, double y, double speed, double direction, Texture texture, int idEnemy, long idNet){
		super(Arrays.asList(
				new Position(x, y, 1600, (int) direction), /*TODO: depth (z) передавать с сервера*/
				new Movement(speed, direction),
				new SpriteRender(texture)));

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		if (texture.equals(Global.spriteStorage.getSprite("b_streamlined").getTexture())){
			getComponent(Movement.class).directionDrawEquals = true;
			getComponent(Position.class).setDirectionDraw(0);
		} else {
			getComponent(Movement.class).directionDrawEquals = false;
		}
	}

	public void destroy(int explosionSize){
		destroy();

		if (explosionSize > 0) {
			GameObject explosion = GameObjectFactory.create(getComponent(Position.class).x, getComponent(Position.class).y, -100);
			explosion.setComponent(new Explosion(explosionSize));
			explosion.getComponent(Particles.class).destroyObject = true;
			Global.location.objAdd(explosion);
		}
	}
}
