package cc.abro.tow.client.tanks.player;


import cc.abro.orchengine.context.Context;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.LocationManager;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.collision.*;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.Rendering;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.gameobject.location.Border;
import cc.abro.orchengine.net.client.tcp.TCPControl;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.orchengine.util.GameObjectFactory;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.CollidableObjectType;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.map.objects.collised.CollisedMapObject;
import cc.abro.tow.client.map.objects.destroyed.DestroyedMapObject;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.tanks.enemy.EnemyArmor;

import java.io.IOException;
import java.util.Set;

public class Bullet extends GameObject implements CollisionListener{

    public static final String PATH_SETTING = "game/bullet/";
    public String name, title; //name - техническое название, title - игровое

    public double damage; //Дамаг (пушка и в loadData добавляем дамаг пули)
    public int range; //Дальность (пушка и в loadData добавляем дальность пули)
    public int explosionSize;

    public double startX;
    public double startY;
    public long idNet;

    public String sound_shot;
    public String sound_hit;

    public Player player;
    public String imageName;
    public Sprite texture;

    public Bullet() {
        super(Context.getService(LocationManager.class).getActiveLocation());
    }

    public void init(Player player, double x, double y, double dir, double damage, int range, String name) {
        this.player = player;
        this.name = name;
        this.damage = damage; //Дамаг исключительно от выстрелевшей пушки
        this.range = range; //Дальность исключительно от выстрелевшей пушки
        this.idNet = Context.getService(ClientData.class).idNet;
        this.startX = x;
        this.startY = y;

        addComponent(new Movement());
        this.getComponent(Movement.class).setDirection(dir);
        loadData();

        setX(x);
        setY(y);
        setDirection(dir);
        addComponent(new SpriteRender(texture.texture(), 1500));

        addComponent(new Collision(texture.mask(), CollidableObjectType.BULLET));
        getComponent(Collision.class)
                .addListener(CollidableObjectType.ENEMY_ARMOR, this)
                .addListener(DefaultCollidableObjectType.BORDER, this)
                .addListener(CollidableObjectType.WALL, this);

        Context.getService(TCPControl.class).send(13, getData());
        Context.getService(ClientData.class).idNet++;

        playSoundShot();
    }

    @Override
    public void collision(CollidableComponent collision, CollisionType collisionType) {
        if (isDestroyed()) return;
        if (collisionType == CollisionType.LEAVING) return;
        GameObject gameObject = collision.getGameObject();

        if (gameObject.getClass().equals(Border.class)) {
            destroy(0);
        }

        if (Set.of(CollisedMapObject.class, DestroyedMapObject.class).contains(gameObject.getClass())) {
            destroy(explosionSize);

            getAudioService().playSoundEffect(getAudioStorage().getAudio(sound_hit), (int) getX(), (int) getY(), GameSetting.SOUND_RANGE);
            Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + sound_hit);
        }

        if (gameObject.getClass().equals(EnemyArmor.class)) {
            EnemyArmor ea = (EnemyArmor) gameObject;

            Context.getService(TCPControl.class).send(14, damage + " " + ea.enemy.id);
            destroy(explosionSize);

            getAudioService().playSoundEffect(getAudioStorage().getAudio(sound_hit), (int) getX(), (int) getY(), GameSetting.SOUND_RANGE);
            Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + sound_hit);

            //Для вампирского сета
            if (ea.enemy.alive) player.hitting(damage);
        }
    }

    public void destroy(int expSize) {
        destroy();
        Context.getService(TCPControl.class).send(15, idNet + " " + expSize);

        if (explosionSize > 0) {
            GameObject explosion = GameObjectFactory.create(getLocation(), getX(), getY(), 3000);
            Explosion explosionParticles = new Explosion(expSize);
            explosion.addComponent(explosionParticles);
            explosionParticles.activate();
            explosion.getComponent(Particles.class).destroyObject = true;
        }
    }

    @Override
    public void update(long delta) {
        if (!isDestroyed()) {
            if (Math.sqrt(Math.pow(startX - getX(), 2) + Math.pow(startY - getY(), 2)) >= range) {
                destroy(0);
            }
        }

        super.update(delta);
    }

    public void playSoundShot() {
        getAudioService().playSoundEffect(getAudioStorage().getAudio(sound_shot), (int) getX(), (int) getY(), GameSetting.SOUND_RANGE);
        Context.getService(TCPControl.class).send(25, (int) getX() + " " + (int) getY() + " " + sound_shot);
    }

    public String getData() {
        return Math.round(getX())
                + " " + Math.round(getY())
                + " " + getComponent(Movement.class).getDirection()
                + " " + getComponent(Movement.class).speed
                + " " + imageName
                + " " + idNet;
    }

    public String getConfigFileName() {
        return PATH_SETTING + name + ".properties";
    }

    public void loadData() {
        ConfigReader cr = new ConfigReader(getConfigFileName());

        getComponent(Movement.class).speed = cr.findDouble("SPEED") + player.stats.speedTankUp / 2;
        getComponent(Movement.class).speed = Math.max(getComponent(Movement.class).speed, player.stats.speedTankUp * GameSetting.MIN_BULLET_SPEED_KOEF);

        damage += cr.findDouble("DAMAGE");//К дамагу пушки прибавляем дамаг патрона
        range += cr.findInteger("RANGE");//К дальности пушки прибавляем дальность патрона
        imageName = cr.findString("IMAGE_NAME");
        texture = getSpriteStorage().getSprite(imageName);
        title = cr.findString("TITLE");
        sound_shot = cr.findString("SOUND_SHOT");
        sound_hit = cr.findString("SOUND_HIT");
        explosionSize = cr.findInteger("EXPLOSION_SIZE");
    }

}
