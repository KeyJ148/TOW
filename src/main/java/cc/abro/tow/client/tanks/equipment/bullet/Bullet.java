package cc.abro.tow.client.tanks.equipment.bullet;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.events.UpdateEvent;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.CollidableComponent;
import cc.abro.orchengine.gameobject.components.collision.Collision;
import cc.abro.orchengine.gameobject.components.collision.CollisionType;
import cc.abro.orchengine.gameobject.components.collision.DefaultCollidableObjectType;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.audios.AudioService;
import cc.abro.orchengine.resources.sprites.SpriteStorage;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.DepthConstants;
import cc.abro.tow.client.events.TankHitEvent;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.settings.GameSettingsService;
import cc.abro.tow.client.tanks.enemy.EnemyTank;
import cc.abro.tow.client.tanks.stats.Stats;
import cc.abro.tow.client.tanks.tank.Tank;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;

public class Bullet extends GameObject {

    private final SpriteStorage spriteStorage;
    private final AudioService audioService;
    private final GameSettingsService gameSettingsService;
    private final BulletSpriteStorage bulletSpriteStorage;

    @Getter
    private final Movement<GameObject> movementComponent;
    @Getter
    private final SpriteRender<GameObject> spriteComponent;
    @Getter
    private final Collision collisionComponent;
    @Getter
    private final BulletSpriteStorage.BulletSpriteSpecification bulletSpriteSpecification;

    @Getter
    private final Tank tankAttacker;
    @Getter
    private final String soundHit;
    @Getter
    private final double explosionPower;
    @Getter
    private final double startX;
    @Getter
    private final double startY;
    @Getter
    private final double range;
    @Getter
    private final double damage;

    public Bullet(Tank tankAttacker, double x, double y, double direction, String spriteName, String soundHit) {
        super(tankAttacker.getLocation());
        setX(x);
        setY(y);

        spriteStorage = getSpriteStorage();
        audioService = getAudioService();
        gameSettingsService = Context.getService(GameSettingsService.class);
        bulletSpriteStorage = Context.getService(BulletSpriteStorage.class);

        Stats stats = tankAttacker.getTankStatsComponent().getStats();
        this.tankAttacker = tankAttacker;
        this.explosionPower = stats.getBulletExplosionPower();
        this.soundHit = soundHit;
        this.startX = x;
        this.startY = y;
        this.range = stats.getRange();
        this.damage = stats.getDamage();

        double speed = Math.max(stats.getBulletSpeed(),
                stats.getSpeedUp() * gameSettingsService.getGameSettings().getMinBulletSpeedCoefficient());
        movementComponent = new Movement<>();
        addComponent(movementComponent);
        movementComponent.setDirection(direction);
        movementComponent.setSpeed(speed);

        spriteComponent = new SpriteRender<>(spriteStorage.getSprite(spriteName).texture(),
                DepthConstants.BULLET_SPRITE_Z);
        addComponent(spriteComponent);

        collisionComponent = new Collision(spriteStorage.getSprite(spriteName).mask(), CollidableObjectType.BULLET);
        collisionComponent
                .addListener(CollidableObjectType.ENEMY_TANK, this::collision)
                .addListener(DefaultCollidableObjectType.BORDER, this::collision)
                .addListener(CollidableObjectType.WALL, this::collision);
        addComponent(collisionComponent);

        bulletSpriteSpecification = bulletSpriteStorage.getBulletSpriteSpecification(spriteName);
        if (bulletSpriteSpecification.rotation()) {
            spriteComponent.setDirection(getComponent(Movement.class).getDirection());
        }

        //TODO Context.getService(TCPControl.class).send(13, getData());
    }

    @Subscribe
    public void onUpdateEventForRangeDestroy(UpdateEvent updateEvent) {
        if (!isDestroyed()) {
            if (Math.sqrt(Math.pow(startX - getX(), 2) + Math.pow(startY - getY(), 2)) >= range) {
                destroyByRange();
            }
        }
    }

    protected void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;

        if (collision.getType().equals(DefaultCollidableObjectType.BORDER)) {
            destroyWithoutExplosion();
        }
        if (collision.getType().equals(CollidableObjectType.WALL)) {
            destroyWithExplosion();
        }
        if (collision.getType().equals(CollidableObjectType.ENEMY_TANK)) {
            destroyWithExplosion();
            postEvent(new TankHitEvent(tankAttacker, (EnemyTank) collision.getGameObject(), damage));

            //TODO Context.getService(TCPControl.class).send(14, damage + " " + ea.enemy.id); (получать по шине ивентов)
        }
    }

    protected void destroyWithExplosion(double explosionSize) {
        if (explosionSize > 0) {
            GameObject explosion = new GameObject(getLocation());
            explosion.setPosition(getX(), getY());
            Explosion explosionParticles = new Explosion(explosionSize, DepthConstants.EXPLOSION_Z);
            explosionParticles.destroyObject = true;
            explosion.addComponent(explosionParticles);
            explosionParticles.activate();

            audioService.playSoundEffect(getAudioStorage().getAudio(soundHit), (int) getX(), (int) getY(),
                    gameSettingsService.getGameSettings().getSoundRange());
            Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + soundHit);
        }
        //TODO Context.getService(TCPControl.class).send(15, idNet + " " + expSize); (посылать и получать по шине ивентов)
        destroy();
    }

    protected void destroyByRange() {
        destroyWithoutExplosion();
    }

    protected void destroyWithExplosion() {
        destroyWithExplosion(getExplosionPower());
    }

    protected void destroyWithoutExplosion() {
        destroyWithExplosion(0);
    }
}
