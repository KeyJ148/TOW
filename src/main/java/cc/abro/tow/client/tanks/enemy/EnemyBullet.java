package cc.abro.tow.client.tanks.enemy;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.location.LocationManager;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.tow.client.particles.Explosion;

import java.util.Arrays;

public class EnemyBullet extends GameObject {

	public int idEnemy;
	public long idNet;

	public EnemyBullet(double x, double y, double speed, double direction, Texture texture, int idEnemy, long idNet) {
		super(Arrays.asList(
				new Position(x, y, 1600, (int) direction),
				new Movement(speed, direction),
				new SpriteRender(texture)));

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		if (texture.equals(Context.getService(SpriteStorage.class).getSprite("b_streamlined").getTexture())) {
			getComponent(Movement.class).directionDrawEquals = true;
			getComponent(Position.class).setDirectionDraw(0);
		} else {
			getComponent(Movement.class).directionDrawEquals = false;
		}
	}

	public void destroy(int explosionSize) {
		destroy();

		if (explosionSize > 0) {
			GameObject explosion = GameObjectFactory.create(getComponent(Position.class).x, getComponent(Position.class).y, 3000);
			explosion.setComponent(new Explosion(explosionSize));
			explosion.getComponent(Particles.class).destroyObject = true;
			Context.getService(LocationManager.class).getActiveLocation().getMap().add(explosion);
		}
	}
}
