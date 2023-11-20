package cc.abro.tow.client.tanks.equipment.bullet1;

import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.Location;
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
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.settings.GameSettingsService;

public class Bullet extends GameObject {

    private final SpriteStorage spriteStorage;
    private final AudioService audioService;
    private final GameSettingsService gameSettingsService;

    private final Movement<GameObject> movementComponent;
    private final SpriteRender<GameObject> spriteComponent;
    private final Collision collisionComponent;

    private final String soundHit;
    private final double explosionPower;
    private final double startX;
    private final double startY;
    private final double range;

    public Bullet(Location location, double x, double y, double direction, String spriteName, String soundHit,
                  double speed, double range, double damage, double explosionPower) {
        super(location);
        setX(x);
        setY(y);

        spriteStorage = getSpriteStorage();
        audioService = getAudioService();
        gameSettingsService = Context.getService(GameSettingsService.class);

        this.explosionPower = explosionPower;
        this.soundHit = soundHit;
        this.startX = x;
        this.startY = y;
        this.range = range;

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

        //TODO не забыть реализовать
        //getComponent(Movement.class).speed = cr.findDouble("SPEED") + player.stats.speedTankUp / 2;
        //getComponent(Movement.class).speed = Math.max(getComponent(Movement.class).speed, player.stats.speedTankUp * GameSetting.MIN_BULLET_SPEED_KOEF);
    }

    public void update() {
        if (!isDestroyed()) {
            if (Math.sqrt(Math.pow(startX - getX(), 2) + Math.pow(startY - getY(), 2)) >= range) {
                exploded(0);
            }
        }
    }

    private void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;

        if (collision.getType().equals(DefaultCollidableObjectType.BORDER)) {
            exploded(0);

        }
        if (collision.getType().equals(CollidableObjectType.WALL)) {
            exploded(explosionPower);

        }
        if (collision.getType().equals(CollidableObjectType.ENEMY_TANK)) {
            exploded(explosionPower);

            //TODO Context.getService(TCPControl.class).send(14, damage + " " + ea.enemy.id);
            //if (ea.enemy.alive) player.hitting(damage); //TODO вызвать функцию в соответствующем компоненте танка, eventBus хорошо подойдет
        }
    }

    private void exploded(double explosionSize) {
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
        //TODO Context.getService(TCPControl.class).send(15, idNet + " " + expSize);
        destroy();
    }
}
