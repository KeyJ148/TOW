package cc.abro.tow.client.tanks.player;


import cc.abro.orchengine.Global;
import cc.abro.orchengine.gameobject.GameObject;
import cc.abro.orchengine.gameobject.GameObjectFactory;
import cc.abro.orchengine.gameobject.components.Collision;
import cc.abro.orchengine.gameobject.components.CollisionDirect;
import cc.abro.orchengine.gameobject.components.Movement;
import cc.abro.orchengine.gameobject.components.Position;
import cc.abro.orchengine.gameobject.components.particles.Particles;
import cc.abro.orchengine.gameobject.components.render.SpriteRender;
import cc.abro.orchengine.map.Border;
import cc.abro.orchengine.resources.sprites.Sprite;
import cc.abro.tow.client.ConfigReader;
import cc.abro.tow.client.ClientData;
import cc.abro.tow.client.GameSetting;
import cc.abro.tow.client.map.objects.textured.TexturedMapObject;
import cc.abro.tow.client.particles.Explosion;
import cc.abro.tow.client.tanks.enemy.EnemyArmor;

public class Bullet extends GameObject implements Collision.CollisionListener {

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
    public Sprite texture;

    public void init(Player player, double x, double y, double dir, double damage, int range, String name) {
        this.player = player;
        this.name = name;
        this.damage = damage; //Дамаг исключительно от выстрелевшей пушки
        this.range = range; //Дальность исключительно от выстрелевшей пушки
        this.idNet = ClientData.idNet;
        this.startX = x;
        this.startY = y;

        setComponent(new Movement());
        this.getComponent(Movement.class).setDirection(dir);
        loadData();

        setComponent(new Position(x, y, 1500, dir));
        setComponent(new SpriteRender(texture.getTexture()));

        setComponent(new CollisionDirect(texture.getMask(), range));
        getComponent(Collision.class).addCollisionObjects(new Class[]{TexturedMapObject.class, EnemyArmor.class, Border.class});
        getComponent(Collision.class).addListener(this);
        ((CollisionDirect) getComponent(Collision.class)).init();

        Global.tcpControl.send(13, getData());
        ClientData.idNet++;

        playSoundShot();
    }

    @Override
    public void collision(GameObject gameObject) {
        if (isDestroy()) return;

        if (gameObject.getClass().equals(Border.class)) {
            destroy(0);
        }

        if (gameObject.getClass().equals(TexturedMapObject.class)) {
            destroy(explosionSize);

            Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_hit), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
            Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_hit);
        }

        if (gameObject.getClass().equals(EnemyArmor.class)) {
            EnemyArmor ea = (EnemyArmor) gameObject;

            Global.tcpControl.send(14, damage + " " + ea.enemy.id);
            destroy(explosionSize);

            Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_hit), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
            Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_hit);

            //Для вампирского сета
            if (ea.enemy.alive) player.hitting(damage);
        }
    }

    public void destroy(int expSize) {
        destroy();
        Global.tcpControl.send(15, idNet + " " + expSize);

        if (explosionSize > 0) {
            GameObject explosion = GameObjectFactory.create(getComponent(Position.class).x, getComponent(Position.class).y, -100);
            explosion.setComponent(new Explosion(expSize));
            explosion.getComponent(Particles.class).destroyObject = true;
            Global.location.objAdd(explosion);
        }
    }

    @Override
    public void update(long delta) {
        if (!isDestroy()) {
            if (Math.sqrt(Math.pow(startX - getComponent(Position.class).x, 2) + Math.pow(startY - getComponent(Position.class).y, 2)) >= range) {
                destroy(0);
            }
        }

        super.update(delta);
    }

    public void playSoundShot() {
        Global.audioPlayer.playSoundEffect(Global.audioStorage.getAudio(sound_shot), (int) getComponent(Position.class).x, (int) getComponent(Position.class).y, GameSetting.SOUND_RANGE);
        Global.tcpControl.send(25, (int) getComponent(Position.class).x + " " + (int) getComponent(Position.class).y + " " + sound_shot);
    }

    public String getData() {
        return Math.round(getComponent(Position.class).x)
                + " " + Math.round(getComponent(Position.class).y)
                + " " + getComponent(Movement.class).getDirection()
                + " " + getComponent(Movement.class).speed
                + " " + "b_default"
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
        texture = Global.spriteStorage.getSprite(cr.findString("IMAGE_NAME"));
        title = cr.findString("TITLE");
        sound_shot = cr.findString("SOUND_SHOT");
        sound_hit = cr.findString("SOUND_HIT");
        explosionSize = cr.findInteger("EXPLOSION_SIZE");
    }

}