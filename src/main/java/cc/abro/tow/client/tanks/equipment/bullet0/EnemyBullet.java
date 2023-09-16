package cc.abro.tow.client.tanks.equipment.bullet0;

import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.resources.textures.Texture;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.tow.client.particles.Explosion;

import java.util.Arrays;

public class EnemyBullet extends GameObject {

	public int idEnemy;
	public long idNet;

	public EnemyBullet(Location location, double x, double y, double speed, double direction, Texture texture, int idEnemy, long idNet) {
		super(location, Arrays.asList(
				new Movement(speed, direction),
				new SpriteRender(texture, 1600)
		));
		setPosition(x, y);
		setDirection(direction);

		this.idEnemy = idEnemy;
		this.idNet = idNet;

		if (texture.equals(getSpriteStorage().getSprite("b_streamlined").texture())) {
			getComponent(Movement.class).directionDrawEquals = true;
			setDirection(0);
		} else {
			getComponent(Movement.class).directionDrawEquals = false;
		}
	}

	public void destroy(int explosionSize) {
		destroy();

		if (explosionSize > 0) {
			GameObject explosion = GameObjectFactory.create(getLocation(), getX(), getY(), 3000);
			Explosion explosionParticles = new Explosion(explosionSize);
			explosion.addComponent(explosionParticles);
			explosionParticles.activate();
			explosion.getComponent(Particles.class).destroyObject = true;
		}
	}
}
